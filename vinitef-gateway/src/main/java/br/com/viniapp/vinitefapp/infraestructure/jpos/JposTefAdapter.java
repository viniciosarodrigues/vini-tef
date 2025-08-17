package br.com.viniapp.vinitefapp.infraestructure.jpos;

import br.com.viniapp.vinitefapp.domain.Transaction;
import br.com.viniapp.vinitefapp.domain.ports.out.TefProviderPort;
import br.com.viniapp.vinitefapp.infraestructure.exception.HttpException;
import br.com.viniapp.vinitefapp.infraestructure.web.dto.enums.BitField;
import br.com.viniapp.vinitefapp.infraestructure.web.dto.enums.MandatoryOrOptional;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

@Component
public class JposTefAdapter implements TefProviderPort {

    private final Logger logger;

    @Value("${tef.server.host}")
    private String tefHost;

    @Value("${tef.server.port}")
    private int tefPort;

    @Value("${tef.server.timeout}")
    private int tefTimeout;

    @Value("classpath:fields.xml")
    private Resource resourceFile;

    public JposTefAdapter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Transaction processTransaction(final String merchantId, final Transaction transaction) {
        try (InputStream inputStream = resourceFile.getInputStream()) {
            ISOMsg isoMsg = IsoMessageMapper.toIsoMsg(merchantId, transaction);

            GenericPackager packager = new GenericPackager(inputStream);
            ASCIIChannel channel = new ASCIIChannel(tefHost, tefPort, packager);
            channel.setTimeout(tefTimeout);

            logger.info("Conectando ao VINI-TEF-SERVER em {}:{}", tefHost, tefPort);
            channel.connect();

            logger.info("Enviando mensagem ISO 8583...");
            isoMsg.setPackager(packager);
            logMessage(isoMsg);
            logger.info(new String(isoMsg.pack()));
            channel.send(isoMsg);

            logger.info("Aguardando resposta...");
            ISOMsg responseMsg = channel.receive();
            channel.disconnect();
            logger.info("Desconectado.");

            return IsoMessageMapper.toTransaction(responseMsg);
        } catch (SocketTimeoutException timeoutException) {
            throw new HttpException(HttpStatus.GATEWAY_TIMEOUT, "VINI-TEF-SERVER demorou muito para responder, a conexão foi encerrada", timeoutException);
        } catch (ISOException | IOException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao enviar mensagem ISO 8583 para o VINI-TEF-SERVER.", e);
        }
    }

    private void logMessage(ISOMsg msg) {
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