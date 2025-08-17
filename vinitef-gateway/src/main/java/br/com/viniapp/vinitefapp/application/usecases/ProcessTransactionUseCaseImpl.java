package br.com.viniapp.vinitefapp.application.usecases;

import br.com.viniapp.vinitefapp.domain.Transaction;
import br.com.viniapp.vinitefapp.domain.ports.in.ProcessTransactionUseCase;
import br.com.viniapp.vinitefapp.domain.ports.out.TefProviderPort;

import java.net.SocketTimeoutException;

public class ProcessTransactionUseCaseImpl implements ProcessTransactionUseCase {

    private final TefProviderPort tefProviderPort;

    public ProcessTransactionUseCaseImpl(TefProviderPort tefProviderPort) {
        this.tefProviderPort = tefProviderPort;
    }

    @Override
    public Transaction execute(final String merchantId, final Transaction transaction) throws SocketTimeoutException {
        return tefProviderPort.processTransaction(merchantId, transaction);
    }
}