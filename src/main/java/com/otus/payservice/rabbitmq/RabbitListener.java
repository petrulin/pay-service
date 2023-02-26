package com.otus.payservice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.payservice.entity.Message;
import com.otus.payservice.rabbitmq.domain.RMessage;
import com.otus.payservice.rabbitmq.domain.dto.PayDTO;
import com.otus.payservice.service.PaymentService;
import com.otus.payservice.service.MessageService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitListener {

    private final MessageService messageService;
    private final PaymentService storageService;
    private final RabbitTemplate rt;

    @Value("${spring.rabbitmq.queues.order-answer-queue}")
    private String answerQueue;
    @Value("${spring.rabbitmq.exchanges.order-answer-exchange}")
    private String answerExchange;

    @Transactional
    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "${spring.rabbitmq.queues.service-queue}", ackMode = "MANUAL")
    public void orderQueueListener(RMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            var om = new ObjectMapper();
            var msg = messageService.findById(message.getMsgId());
            if (msg == null) {
                messageService.save(new Message(message.getMsgId()));
                switch (message.getCmd()) {
                    case "sale" ->  {
                        om.getTypeFactory().constructCollectionType(ArrayList.class, PayDTO.class);
                        var payDTO = om.convertValue(message.getMessage(), PayDTO.class);
                        var answer = storageService.sale(payDTO);
                        rt.convertAndSend(answerExchange, answerQueue,
                                new RMessage(UUID.randomUUID().toString(), "payAnswer", answer)
                        );
                    }
                    default -> log.warn("::PayService:: rabbitmq listener method. Unknown message type");
                }
            }
            else {
                log.warn("::PayService:: rabbitmq listener method orderQueueListener duplicate message!");
            }
        } catch (Exception ex) {
            log.error("::PayService:: rabbitmq listener method orderQueueListener with error message {}", ex.getLocalizedMessage());
            log.error("::PayService:: rabbitmq listener method orderQueueListener with stackTrace {}", ExceptionUtils.getStackTrace(ex));
        } finally {
            basicAck(channel, tag, true);
        }
    }

    private void basicAck(Channel channel, Long tag, boolean b) {
        try {
            channel.basicAck(tag, b);
        } catch (IOException ex) {
            log.error("::PayService:: rabbitmq listener method basicAck with stackTrace {}", ExceptionUtils.getStackTrace(ex));
        }
    }
}
