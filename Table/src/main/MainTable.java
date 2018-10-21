package main;


import commonStructures.ClientProxy;
import commonStructures.ServerCom;
import logging.Logger;
import logging.LoggerStub;
import sharedRegions.bar.BarStub;
import sharedRegions.interfaces.Bar;
import sharedRegions.interfaces.Kitchen;
import sharedRegions.interfaces.Table;
import sharedRegions.kitchen.KitchenStub;
import sharedRegions.table.RestaurantTable;
import sharedRegions.table.RestaurantTableInterface;

import java.net.SocketTimeoutException;

/**
 * The Main Program for the Table Server
 */
public class MainTable {
    /**
     * For controlling when to shutdown the server
     */
    public static boolean waitConnection;

    public static void main(String[] args) {
        Logger  log;
        Table   table;
        Bar     bar;
        Kitchen kitchen;

        RestaurantTableInterface tableInterface;
        ServerCom                sconi;
        ServerCom                scon;
        ClientProxy              clientProxy;

        // instantiation of Shared Regions
        log = new LoggerStub(Constants.loggerHostName, Constants.loggerPort);
        bar = new BarStub(Constants.barHostName, Constants.barPort);
        kitchen = new KitchenStub(Constants.kitchenHostName, Constants.kitchenPort);
        table = new RestaurantTable(log, bar, kitchen);
        tableInterface = new RestaurantTableInterface(table);

        scon = new ServerCom(Constants.tablePort);
        scon.start();
        System.out.println("O serviço da Table foi estabelecido");
        System.out.println("O servidor da Table está à escuta");
        waitConnection = true;
        while (waitConnection) {
            try {
                sconi = scon.accept();
                clientProxy = new ClientProxy(sconi, tableInterface);
                clientProxy.start();
            } catch (SocketTimeoutException e) {

            }
        }
        // Giving an heads up that we've finished
        log.tableFinished();
        scon.end();
        System.out.println("O servidor da Table foi desligado!");
    }
}
