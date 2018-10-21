package main;

import entities.Chef;
import sharedRegions.interfaces.Kitchen;
import sharedRegions.kitchen.KitchenStub;

/**
 * Main do chef.
 */
public class MainChef {

    public static void main(String[] args) {
        Kitchen kitchen = new KitchenStub(Constants.kitchenHostName, Constants.kitchenPort);
        Chef chef = new Chef(kitchen);
        chef.start();
        try {
            chef.join();
        } catch (InterruptedException e) {
            System.err.println("ERROR CODE 6: Could not join with Chef!");
            System.exit(6);
        }
    }
}

