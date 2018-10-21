package main;

import commonStructures.ClientProxy;
import commonStructures.ServerCom;
import logging.Logger;
import logging.LoggerImpl;
import logging.LoggerInterface;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Main do Logger - General Repository of Information
 */
public class MainGRI {
    /**
     * Controlo de quando o servidor encerra.
     */
    public static boolean kitchenFinished;
    /**
     * Controlo de quando o servidor encerra.
     */
    public static boolean barFinished;

    /**
     * Controlo de quando o servidor encerra.
     */
    public static boolean tableFinished;


    public static void main(String[] args) {
        final String fileName = Constants.fileName;
        Logger log;
        LoggerInterface loggerImplInterface;
        ServerCom sconi;
        ServerCom scon;
        ClientProxy clientProxy;
        try {
            log = new LoggerImpl(fileName);
            loggerImplInterface = new LoggerInterface(log);
            scon = new ServerCom(Constants.loggerPort);
            scon.start();
            System.out.println("O serviço do Logger foi estabelecido");
            System.out.println("O servidor do Logger está à escuta");
            kitchenFinished = false;
            barFinished = false;
            tableFinished = false;
            while (true) {
                try {
                    sconi = scon.accept();
                    clientProxy = new ClientProxy(sconi, loggerImplInterface);
                    clientProxy.start();
                } catch (SocketTimeoutException e) {
                }
                if(kitchenFinished && barFinished && tableFinished){
                    break;
                }
            }
            scon.end();
            System.out.println("O servidor do Logger foi desligado!");
        } catch (IOException e) {
            System.err.println("ERROR CODE 1: Unable to open file for Writing:\n" + fileName);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
