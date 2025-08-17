package br.com.viniapp.vinitefapp.infraestructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionResponse extends AbstractTransactionBase {

    @JsonProperty(value = "response_code")
    private String responseCode;
    @JsonProperty(value = "authorization_code")
    private String authorizationCode;
    @JsonProperty(value = "transaction_date")
    private String transactionDate;
    @JsonProperty(value = "transaction_hour")
    private String transactionHour;

}