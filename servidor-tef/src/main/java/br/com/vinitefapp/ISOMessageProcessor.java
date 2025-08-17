package br.com.vinitefapp;

import br.com.vinitefapp.enums.BitField;
import br.com.vinitefapp.enums.MandatoryOrOptional;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ISOMessageProcessor implements ISORequestListener {
    private static final Logger logger = LoggerFactory.getLogger(ISOMessageProcessor.class);

    private final static String MIT_SALE_REQUEST_COD = "0200";
    private final static String MIT_SALE_RESPONSE_COD = "0210";
    private final static String MIT_NOT_SUPPORTED_REQUEST_RESPONSE_COD = "0810";

    private final static String SUCCESS_RESPONSE_CODE = "00";
    private final static String REFUSED_RESPONSE_CODE = "51";
    private final static String GENERIC_ERROR_RESPONSE_CODE = "05";
    private static Integer _authorizationCode = 0;
    private static Integer _nsuHost = 0;


    @Override
    public boolean process(ISOSource source, ISOMsg request) {
        try {
            logger.info("Requisição recebida...");
            logMessage(request);

            ISOMsg response = (ISOMsg) request.clone();

            if (MIT_SALE_REQUEST_COD.equals(request.getMTI())) {
                response.setMTI(MIT_SALE_RESPONSE_COD);
                var responseCode = getResponseCode(request);
                if (responseCode != null && responseCode.equals(SUCCESS_RESPONSE_CODE)) {
                    response.set(BitField.BIT_AUTHORIZATION_CODE.getBit(), getAuthorizationCode());
                }
                response.set(BitField.BIT_RESPONSE_CODE.getBit(), responseCode);
            } else {
                response.setMTI(MIT_NOT_SUPPORTED_REQUEST_RESPONSE_COD);
                response.set(BitField.BIT_RESPONSE_CODE.getBit(), GENERIC_ERROR_RESPONSE_CODE);
                response.set(BitField.BIT_TRANSACTION_DATETIME.getBit(), LocalDate.now().format(DateTimeFormatter.ofPattern("MMddHHmmss")));
                logger.warn("MTI {} não suportado. Respondendo com erro.", request.getMTI());
            }
            response.set(BitField.BIT_NSU_HOST.getBit(), getNsuHost());

            logger.info("Devolvendo resposta...");
            logMessage(response);
            source.send(response);

        } catch (ISOException | IOException | InterruptedException e) {
            logger.error("Erro ao processar mensagem.", e);
        }
        return true;
    }

    private static String getAuthorizationCode() {
        _authorizationCode++;
        return String.format("%06d", _authorizationCode);
    }

    private static String getNsuHost() {
        _nsuHost++;
        return String.format("%010d", _nsuHost);
    }

    private String getResponseCode(ISOMsg request) throws InterruptedException {
        String amountStr = request.getString(BitField.BIT_AMOUNT.getBit());
        double amount = Long.parseLong(amountStr) / 100.0;
        if (amount > 1000.00) {
            long delay = 15000;
            Thread.sleep(delay);
            return null;
        } else if (amount % 2 == 0) {
            return SUCCESS_RESPONSE_CODE;
        } else {
            return REFUSED_RESPONSE_CODE;
        }
    }

    private static void logMessage(ISOMsg msg) {
        logger.info("-------------------- MENSAGEM ISO --------------------");
        try {
            logger.info("  MTI: {}", msg.getMTI());
            for (BitField bitField : BitField.values()) {
                String field = msg.getString(bitField.getBit());
                if (bitField.getMandatoryOrOptional() == MandatoryOrOptional.MANDATORY && (field == null || field.isBlank())) {
                    logger.warn("  Campo {}: {} | {}", bitField.name(), bitField.getBit(), "CAMPO OBRIGATÓRIO NULO!");
                } else
                    logger.info("  Campo {}: {} | {}", bitField.name(), bitField.getBit(), field);
            }
        } catch (ISOException e) {
            logger.error("Erro ao fazer o parse da mensagem para log.", e);
        }
        logger.info("----------------------------------------------------");
    }
}

