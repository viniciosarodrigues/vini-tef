package br.com.viniapp.vinitefapp.infraestructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Resposta da solicitação de autorização de pagamento")
public class AuthorizationResponse extends AbstractAuthorizationBase {

    @JsonProperty(value = "payment_id")
    @Schema(description = "Identificador único gerado pela API do pagamento, que identifica a autorização")
    private String paymentId;

    @JsonProperty(value = "response_code")
    @Schema(description = "Código de resposta do processamento do autorizador")
    private String responseCode;

    @JsonProperty(value = "authorization_code")
    @Schema(description = "Código de autorização, quando transação aprovada")
    private String authorizationCode;

    @JsonProperty(value = "transaction_date")
    @Schema(description = "Data da transação no formmato yy-MM-dd")
    private LocalDate transactionDate;

    @JsonProperty(value = "transaction_hour")
    @Schema(description = "Hora da transação no formato HH:mm:ss")
    private LocalTime transactionHour;

}