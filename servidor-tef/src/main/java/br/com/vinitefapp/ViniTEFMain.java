package br.com.vinitefapp;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOServer;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ViniTEFMain {

    private static final Logger logger = LoggerFactory.getLogger(ViniTEFMain.class);

    private static final int DEFAULT_PORT = 8000;
    private static final int THREAD_POOL_MIN_SIZE = 5;
    private static final int THREAD_POOL_MAX_SIZE = 100;


    public static void main(String[] args) {
        showBanner();
        logger.info("Iniciando o servidor...");
        try (InputStream inputStream = ViniTEFMain.class.getResourceAsStream("/fields.xml")) {
            startIsoServer(new GenericPackager(inputStream));
        } catch (ISOException | IOException e) {
            logger.error("Erro ao configurar o servidor JPos.", e);
        }
    }

    private static void showBanner() {
        String version = "1.0.0-beta";
        String banner = """
                
                ░█░█░▀█▀░█▀█░▀█▀░░░░░▀█▀░█▀▀░█▀▀░░░░░█▀▀░█▀▀░█▀▄░█░█░█▀▀░█▀▄
                ░▀▄▀░░█░░█░█░░█░░▄▄▄░░█░░█▀▀░█▀▀░▄▄▄░▀▀█░█▀▀░█▀▄░▀▄▀░█▀▀░█▀▄
                ░░▀░░▀▀▀░▀░▀░▀▀▀░░░░░░▀░░▀▀▀░▀░░░░░░░▀▀▀░▀▀▀░▀░▀░░▀░░▀▀▀░▀░▀
                :: VINI TEF SERVER ::                                  (v%s)
                """.formatted(version);

        logger.info(banner);
    }

    private static void startIsoServer(GenericPackager packager) throws IOException {
        ASCIIChannel isoChannel = new ASCIIChannel(packager);
        ThreadPool serverThreadPool = new ThreadPool(THREAD_POOL_MIN_SIZE, THREAD_POOL_MAX_SIZE);

        ISOServer isoServer = new ISOServer(DEFAULT_PORT, isoChannel, serverThreadPool);
        isoServer.addISORequestListener(new ISOMessageProcessor());
        new Thread(isoServer, "vini-tef-server").start();
        logger.info("Servidor iniciado e escutando na porta {}.", DEFAULT_PORT);
    }
}