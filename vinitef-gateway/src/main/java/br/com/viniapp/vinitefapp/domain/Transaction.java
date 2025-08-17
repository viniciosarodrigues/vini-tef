package br.com.viniapp.vinitefapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@EqualsAndHashCode
public class Transaction {
    private UUID externalId;
    private BigDecimal value;
    private String cardNumber;
    private Integer installments;
    private String cvv;
    private Integer expMonth;
    private String holderName;
    private Integer expYear;
    private String responseCode;
    private String authorizationCode;
    private LocalDate transactionDate;
    private LocalTime transactionHour;
    private String merchantId;
}
