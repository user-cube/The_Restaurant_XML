package main;


import commonStructures.ClientProxy;
import commonStructures.ServerCom;
import logging.Logger;
import logging.LoggerStub;
import sharedRegions.bar.BarStub;
import sharedRegions.interfaces.Bar;
import sharedRegions.interfaces.Kitchen;
import sharedRegions.kitchen.RestaurantKitchen;
import sharedRegions.kitchen.RestaurantKitchenInterface;

import java.net.SocketTimeoutException;

/**
 * Main da Kitchen.
 */
public class MainKitchen {
    /**
     * Controlo de encerramento do servidor.
     */
    public static boolean waitConnection;

    public static void main(String[] args) {
        Logger log;
        Kitchen kitchen;
        Bar bar;
        RestaurantKitchenInterface kitchenInterface;
        ServerCom sconi;
        ServerCom scon;
        ClientProxy  clientProxy;

        log = new LoggerStub(Constants.loggerHostName, Constants.loggerPort);
        bar = new BarStub(Constants.barHostName, Constants.barPort);
        kitchen = new RestaurantKitchen(log, bar);
        kitchenInterface = new RestaurantKitchenInterface(kitchen);

        scon = new ServerCom(Constants.kitchenPort);
        scon.start();
        System.out.println("O serviço da Kitchen foi estabelecido");
        System.out.println("O servidor da Kitchen está à escuta");
        waitConnection = true;
        while (waitConnection) {
            try {
                sconi = scon.accept();
                clientProxy = new ClientProxy(sconi, kitchenInterface);
                clientProxy.start();
            } catch (SocketTimeoutException e) {

            }
        }
        log.kitchenFinished();
        scon.end();
        System.out.println("O servidor da Kitchen foi desligado!");
    }
}
