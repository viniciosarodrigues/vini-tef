package br.com.viniapp.vinitefapp.infraestructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public abstract class AbstractAuthorizationBase {
    @NotNull(message = "Informe o valor da transação")
    @Schema(description = "Valor do pagamento")
    private BigDecimal value;
}