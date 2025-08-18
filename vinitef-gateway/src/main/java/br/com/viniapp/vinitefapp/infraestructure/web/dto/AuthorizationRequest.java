package br.com.viniapp.vinitefapp.infraestructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Requisição de autorização de pagamento")
public class AuthorizationRequest extends AbstractAuthorizationBase {
    @NotNull(message = "Informe o identificador externo da transação")
    @JsonProperty("external_id")
    @Schema(description = "Identificador único, gerenciado pelo requisitante da autorização de pagamento")
    private String externalId;

    @JsonProperty("card_number")
    @Schema(description = "Número do cartão")
    private String cardNumber;

    @NotNull(message = "Informe a quantidade de parcelas")
    @Schema(description = "Número de parcelas")
    private Integer installments;

    @Schema(description = "Código de segurança do cartão")
    private String cvv;

    @NotNull(message = "Informe o mês de vencimento do cartão")
    @JsonProperty("exp_month")
    @Schema(description = "Mês do vencimento do cartão")
    private Integer expMonth;

    @JsonProperty("holder_name")
    @Schema(description = "Nome do portador do cartão")
    private String holderName;

    @NotNull(message = "Informe o ano de vencimento do cartão")
    @JsonProperty("exp_year")
    @Schema(description = "Ano de vencimento do cartão")
    private Integer expYear;
}
