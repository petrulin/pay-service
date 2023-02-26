
package com.otus.payservice.rabbitmq.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PayDTO {
    private BigDecimal amount;

    private BigDecimal discount;

    private Long orderId;

    private Long clientId;

}
