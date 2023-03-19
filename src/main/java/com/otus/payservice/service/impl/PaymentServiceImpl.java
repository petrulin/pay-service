package com.otus.payservice.service.impl;


import com.otus.payservice.entity.Payment;
import com.otus.payservice.rabbitmq.domain.dto.CancelDTO;
import com.otus.payservice.rabbitmq.domain.dto.TrxDTO;
import com.otus.payservice.repository.PaymentRepository;
import com.otus.payservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("paymentService")
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public String sale(TrxDTO trxDTO) {
        try {
            Payment payment = new  Payment(
                    trxDTO.getOrder().getAmount(),
                    trxDTO.getOrder().getDiscount(),
                    Boolean.TRUE,
                    trxDTO.getOrder().getUserName(),
                    trxDTO.getOrder().getOrderId(),
                    "SALE"
            );
            paymentRepository.save(payment);
            return "Ok";
        } catch (Exception e) {
            log.error("::PayService:: sale method orderQueueListener with error message {}", e.getLocalizedMessage());
            log.error("::PayService:: sale method orderQueueListener with stackTrace {}", ExceptionUtils.getStackTrace(e));
            return "Error";
        }
    }

    @Override
    public Payment refund(CancelDTO cancelDTO) {
        try {
            var originalPayment = paymentRepository.findByOrderId(cancelDTO.getOrderId());
            if (originalPayment != null) {
                Payment payment = new Payment(
                        originalPayment.getAmount(),
                        originalPayment.getDiscount(),
                        Boolean.TRUE,
                        originalPayment.getUserName(),
                        originalPayment.getOrderId(),
                        "REFUND");
                paymentRepository.save(payment);
                return payment;
            }
            return null;
        } catch (Exception e) {
            log.error("::PayService:: refund method orderQueueListener with error message {}", e.getLocalizedMessage());
            log.error("::PayService:: refund method orderQueueListener with stackTrace {}", ExceptionUtils.getStackTrace(e));
            return null;
        }
    }


}
