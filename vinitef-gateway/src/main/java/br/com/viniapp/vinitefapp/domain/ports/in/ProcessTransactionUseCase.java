package br.com.viniapp.vinitefapp.domain.ports.in;

import br.com.viniapp.vinitefapp.domain.Transaction;

public interface ProcessTransactionUseCase {
    Transaction execute(final String merchantId, final Transaction transaction);
}