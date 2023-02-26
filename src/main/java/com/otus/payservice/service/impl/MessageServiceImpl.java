package com.otus.payservice.service.impl;

import com.otus.payservice.entity.Message;
import com.otus.payservice.repository.MessageRepository;
import com.otus.payservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("currencyService")
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message findById(String msgId) {
        return messageRepository.findById(msgId).orElse(null);
    }

}
