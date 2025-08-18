package br.com.viniapp.vinitefapp.infraestructure.web.mapper;

import br.com.viniapp.vinitefapp.domain.Transaction;
import br.com.viniapp.vinitefapp.infraestructure.web.dto.AuthorizationRequest;
import br.com.viniapp.vinitefapp.infraestructure.web.dto.AuthorizationResponse;
import org.springframework.beans.BeanUtils;

public final class TransactionDtoMapper {
    private TransactionDtoMapper() {
    }

    public static Transaction toDomain(final AuthorizationRequest request) {
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(request, transaction);
        return transaction;
    }

    public static AuthorizationResponse toResponse(final Transaction result) {
        AuthorizationResponse response = new AuthorizationResponse();
        BeanUtils.copyProperties(result, response);
        return response;
    }
}
