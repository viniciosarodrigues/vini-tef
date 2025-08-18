package br.com.viniapp.vinitefapp.infraestructure.web.controller;

import br.com.viniapp.vinitefapp.domain.Transaction;
import br.com.viniapp.vinitefapp.domain.ports.in.ProcessTransactionUseCase;
import br.com.viniapp.vinitefapp.infraestructure.web.dto.AuthorizationRequest;
import br.com.viniapp.vinitefapp.infraestructure.web.dto.AuthorizationResponse;
import br.com.viniapp.vinitefapp.infraestructure.web.mapper.TransactionDtoMapper;
import br.com.viniapp.vinitefapp.infraestructure.web.util.RequestContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.SocketTimeoutException;

@RestController
public class TefController {
    private final ProcessTransactionUseCase processTransactionUseCase;

    public TefController(final ProcessTransactionUseCase processTransactionUseCase) {
        this.processTransactionUseCase = processTransactionUseCase;
    }

    @PostMapping("authorization")
    public ResponseEntity<AuthorizationResponse> createTransaction(@Validated @RequestBody AuthorizationRequest request, HttpServletRequest httpServletRequest) throws SocketTimeoutException {
        Transaction transaction = TransactionDtoMapper.toDomain(request);
        Transaction result = processTransactionUseCase.execute(httpServletRequest.getHeader(RequestContextUtil.MERCHANT_ID_HEADER_KEY), transaction);
        AuthorizationResponse response = TransactionDtoMapper.toResponse(result);
        return ResponseEntity.ok(response);
    }
}