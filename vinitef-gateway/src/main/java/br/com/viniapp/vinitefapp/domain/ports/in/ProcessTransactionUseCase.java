package br.com.viniapp.vinitefapp.domain.ports.in;

import br.com.viniapp.vinitefapp.domain.Transaction;

import java.net.SocketTimeoutException;

public interface ProcessTransactionUseCase {
    Transaction execute(final String merchantId, final Transaction transaction) throws SocketTimeoutException;
}