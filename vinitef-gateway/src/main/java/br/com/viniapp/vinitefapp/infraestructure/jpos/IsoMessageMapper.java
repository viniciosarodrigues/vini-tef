package br.com.viniapp.vinitefapp.infraestructure.jpos;

import br.com.viniapp.vinitefapp.domain.Transaction;
import br.com.viniapp.vinitefapp.infraestructure.web.dto.enums.BitField;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class IsoMessageMapper {

    public static final String CASH_INSTALLMENT = "003000";
    public static final String IN_INSTALLMENTS = "003001";
    private static int _transactionNsu = 0;


    public IsoMessageMapper() {
    }

    public static ISOMsg toIsoMsg(final String merchantId, final Transaction transaction) throws ISOException {
        ISOMsg requesIsoMsg = new ISOMsg();
        final var transactionDate = LocalDateTime.now();
        requesIsoMsg.setMTI("0200");

        requesIsoMsg.set(BitField.BIT_CARD_NUMBER.getBit(), transaction.getCardNumber());
        requesIsoMsg.set(BitField.BIT_PROCESSING_CODE.getBit(), transaction.getInstallments() == 1 ? CASH_INSTALLMENT : IN_INSTALLMENTS);
        requesIsoMsg.set(BitField.BIT_AMOUNT.getBit(), String.format("%012d", transaction.getValue().multiply(BigDecimal.valueOf(100)).toBigIntegerExact().intValue()));
        requesIsoMsg.set(BitField.BIT_TRANSACTION_DATETIME.getBit(), transactionDate.format(DateTimeFormatter.ofPattern("MMddHHmmss")));
        requesIsoMsg.set(BitField.BIT_TRANSACTION_NSU.getBit(), String.format("%06d", ++_transactionNsu));
        requesIsoMsg.set(BitField.BIT_TRANSACTION_HOUR.getBit(), transactionDate.format(DateTimeFormatter.ofPattern("HHmmss")));
        requesIsoMsg.set(BitField.BIT_TRANSACTION_MONTH_DAY.getBit(), transactionDate.format(DateTimeFormatter.ofPattern("MMdd")));
        requesIsoMsg.set(BitField.BIT_CARD_EXP_DATE.getBit(), String.format("%02d", transaction.getExpYear()) + String.format("%02d", transaction.getExpMonth()));
        requesIsoMsg.set(BitField.BIT_ENTRY_MODE.getBit(), "051");
        requesIsoMsg.set(BitField.BIT_MERCHANT_ID.getBit(), String.format("%015d", Integer.parseInt(merchantId)));
        requesIsoMsg.set(BitField.BIT_CVV.getBit(), transaction.getCvv());
        requesIsoMsg.set(BitField.BIT_HOLD_NAME.getBit(), transaction.getHolderName());
        requesIsoMsg.set(BitField.BIT_INSTALLMENT_NUMBER.getBit(), String.format("%02d", transaction.getInstallments()));

        return requesIsoMsg;
    }

    public static Transaction toTransaction(ISOMsg responseMsg) {
        Transaction transaction = new Transaction();
        transaction.setResponseCode(responseMsg.getString(BitField.BIT_RESPONSE_CODE.getBit()));
        transaction.setValue(new BigDecimal(responseMsg.getString(BitField.BIT_AMOUNT.getBit())).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        transaction.setAuthorizationCode(responseMsg.getString(BitField.BIT_AUTHORIZATION_CODE.getBit()));

        LocalDateTime transactionDate = LocalDateTime.parse(responseMsg.getString(BitField.BIT_TRANSACTION_DATETIME.getBit()), DateTimeFormatter.ofPattern("MMddHHmmss"));
        transaction.setTransactionDate(transactionDate.toLocalDate());
        transaction.setTransactionHour(transactionDate.toLocalTime());
        return transaction;
    }
}
