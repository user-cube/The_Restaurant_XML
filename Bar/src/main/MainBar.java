package main;


import commonStructures.ClientProxy;
import commonStructures.ServerCom;
import logging.Logger;
import logging.LoggerStub;
import sharedRegions.bar.DistBar;
import sharedRegions.bar.RestaurantBarInterface;
import sharedRegions.interfaces.Bar;

import java.net.SocketTimeoutException;

/**
 * Main do Bar.
 */
public class MainBar {
    /**
     * Controlo de encerramento de servidor.
     */
    public static boolean waiterFinished;

    public static void main(String[] args) {
        Logger log;
        Bar bar;
        RestaurantBarInterface barInterface;
        ServerCom sconi;
        ServerCom scon;
        ClientProxy clientProxy;

        log = new LoggerStub(Constants.loggerHostName, Constants.loggerPort);
        bar = new DistBar(log);
        barInterface = new RestaurantBarInterface(bar);

        scon = new ServerCom(Constants.barPort);
        scon.start();
        System.out.println("O serviço foi estabelecido");
        System.out.println("O servidor está à escuta");
        waiterFinished = false;
        while (!waiterFinished) {
            try {
                sconi = scon.accept();
                clientProxy = new ClientProxy(sconi, barInterface);
                clientProxy.start();
            } catch (SocketTimeoutException e) {
            }
        }
        log.barFinished();
        scon.end();
        System.out.println("O servidor foi desligado!");

    }
}
