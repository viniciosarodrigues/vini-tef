package br.com.viniapp.vinitefapp.infraestructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionResponse extends AbstractTransactionBase {

    @JsonProperty(value = "response_code")
    private String responseCode;
    @JsonProperty(value = "authorization_code")
    private String authorizationCode;
    @JsonProperty(value = "transaction_date")
    private LocalDate transactionDate;
    @JsonProperty(value = "transaction_hour")
    private LocalTime transactionHour;

}