package com.otus.payservice.service.impl;


import com.otus.payservice.entity.Payment;
import com.otus.payservice.rabbitmq.domain.dto.PayDTO;
import com.otus.payservice.rabbitmq.domain.dto.PayResponse;
import com.otus.payservice.repository.PaymentRepository;
import com.otus.payservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("paymentService")
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public PayResponse sale(PayDTO payDTO) {
        try {
            Payment payment = new  Payment(payDTO.getAmount(), payDTO.getDiscount(), Boolean.TRUE, payDTO.getClientId(), payDTO.getOrderId());
            paymentRepository.save(payment);
            return new PayResponse("Ok");
        } catch (Exception e) {
            return new PayResponse("Error");
        }
    }

}
