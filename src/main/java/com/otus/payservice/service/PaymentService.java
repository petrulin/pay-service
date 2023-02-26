package com.otus.payservice.service;

import com.otus.payservice.rabbitmq.domain.dto.PayDTO;
import com.otus.payservice.rabbitmq.domain.dto.PayResponse;


public interface PaymentService {

    public PayResponse sale(PayDTO payDTO);
}
