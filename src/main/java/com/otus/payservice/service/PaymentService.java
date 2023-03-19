package com.otus.payservice.service;

import com.otus.payservice.entity.Payment;
import com.otus.payservice.rabbitmq.domain.dto.CancelDTO;
import com.otus.payservice.rabbitmq.domain.dto.TrxDTO;


public interface PaymentService {

    String sale(TrxDTO trxDTO);
    Payment refund(CancelDTO cancelDTO);
}
