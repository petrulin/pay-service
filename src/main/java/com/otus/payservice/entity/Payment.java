package com.otus.payservice.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "payment", schema = "pay_service", catalog = "postgres")
public class Payment {

    private static final BigDecimal AMOUNT_DEFAULT_VALUE = BigDecimal.ZERO;
    private static final BigDecimal DISCOUNT_DEFAULT_VALUE = BigDecimal.ZERO;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "amount", nullable = false, precision = 2)
    private BigDecimal amount = AMOUNT_DEFAULT_VALUE;

    @Column(name = "discount", nullable = false, precision = 2)
    private BigDecimal discount = DISCOUNT_DEFAULT_VALUE;

    @Column(name = "success", nullable = false)
    private Boolean success;

    @Column(name = "payment_type", nullable = false, length = 10)
    private String paymentType;

    @Column(name = "currency", nullable = false)
    private Integer currency;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "order_id")
    private Long orderId;

    public Payment(BigDecimal amount, BigDecimal discount, Boolean success, Long clientId, Long orderId) {
        this.paymentDate = LocalDateTime.now();
        this.currency = 933;
        this.paymentType = "SALE";
        this.amount = amount;
        this.discount = discount;
        this.success = success;
        this.clientId = clientId;
        this.orderId = orderId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = Objects.requireNonNullElse(amount, AMOUNT_DEFAULT_VALUE);
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = Objects.requireNonNullElse(discount, DISCOUNT_DEFAULT_VALUE);
    }

}
