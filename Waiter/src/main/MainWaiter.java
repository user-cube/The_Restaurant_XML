package main;


import entities.Waiter;
import sharedRegions.bar.BarStub;
import sharedRegions.interfaces.Bar;
import sharedRegions.interfaces.Kitchen;
import sharedRegions.interfaces.Table;
import sharedRegions.kitchen.KitchenStub;
import sharedRegions.table.TableStub;

/**
 * Main do Waiter.
 */
public class MainWaiter {


    public static void main(String[] args) {
        Kitchen kitchen = new KitchenStub(Constants.kitchenHostName, Constants.kitchenPort);
        Bar     bar     = new BarStub(Constants.barHostName, Constants.barPort);
        Table   table   = new TableStub(Constants.tableHostName, Constants.tablePort);
        Waiter  waiter  = new Waiter(kitchen, table, bar);
        waiter.start();
        try {
            waiter.join();
        } catch (InterruptedException e) {
            System.err.println("ERROR CODE 6: Could not join with Waiter!");
            System.exit(6);
        }
    }
}

