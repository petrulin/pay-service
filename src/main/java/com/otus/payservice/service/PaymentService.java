package com.otus.payservice.service;

import com.otus.payservice.rabbitmq.domain.dto.CancelDTO;
import com.otus.payservice.rabbitmq.domain.dto.TrxDTO;


public interface PaymentService {

    String sale(TrxDTO trxDTO);
    void refund(CancelDTO cancelDTO);
}
