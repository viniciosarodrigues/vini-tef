package br.com.viniapp.vinitefapp.infraestructure.web.mapper;

import br.com.viniapp.vinitefapp.domain.Transaction;
import br.com.viniapp.vinitefapp.infraestructure.web.dto.TransactionRequest;
import br.com.viniapp.vinitefapp.infraestructure.web.dto.TransactionResponse;
import org.springframework.beans.BeanUtils;

public final class TransactionDtoMapper {
    private TransactionDtoMapper() {
    }

    public static Transaction toDomain(final TransactionRequest request) {
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(request, transaction);
        return transaction;
    }

    public static TransactionResponse toResponse(final Transaction result) {
        TransactionResponse response = new TransactionResponse();
        BeanUtils.copyProperties(result, response);
        return response;
    }
}
