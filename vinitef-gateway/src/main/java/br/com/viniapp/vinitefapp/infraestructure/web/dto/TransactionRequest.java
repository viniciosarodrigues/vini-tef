package br.com.viniapp.vinitefapp.infraestructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionRequest extends AbstractTransactionBase {
    @NotNull(message = "Informe o identificador externo da transação")
    @JsonProperty("external_id")
    private UUID externalId;
    @JsonProperty("card_number")
    private String cardNumber;
    @NotNull(message = "Informe a quantidade de parcelas")
    private Integer installments;
    private String cvv;
    @NotNull(message = "Informe o mês de vencimento do cartão")
    @JsonProperty("exp_month")
    private Integer expMonth;
    @JsonProperty("holder_name")
    private String holderName;
    @NotNull(message = "Informe o ano de vencimento do cartão")
    @JsonProperty("exp_year")
    private Integer expYear;
}
