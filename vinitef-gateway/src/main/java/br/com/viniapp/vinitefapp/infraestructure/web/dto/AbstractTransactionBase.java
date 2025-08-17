package br.com.viniapp.vinitefapp.infraestructure.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public abstract class AbstractTransactionBase {
    @NotNull(message = "Informe o valor da transação")
    private BigDecimal value;
}