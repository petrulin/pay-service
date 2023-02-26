package com.otus.payservice.service;

import com.otus.payservice.entity.Message;


public interface MessageService {

    Message save(Message user);
    Message findById(String msgId);

}
