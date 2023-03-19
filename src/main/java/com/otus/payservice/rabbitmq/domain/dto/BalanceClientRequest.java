package com.otus.payservice.rabbitmq.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceClientRequest {

    private String username;
    private BigDecimal amount;

}
