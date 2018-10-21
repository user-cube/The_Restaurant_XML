package entities;

import entities.states.WaiterState;
import sharedRegions.interfaces.Bar;
import sharedRegions.interfaces.Kitchen;
import sharedRegions.interfaces.Table;

/**
 * Implementação da entidade Waiter.
 */
public class Waiter extends Thread {
    /**
     * Referenciação da Kitchen.
     */
    private final Kitchen kitchen;
    /**
     * Referenciação da Table.
     */
    private final Table table;
    /**
     * Referenciação do Bar.
     */
    private final Bar bar;
    /**
     * Estado interno.
     */
    private WaiterState state;

    /**
     * @param kitchen local onde o chef trabalha.
     * @param table   local onde os estudantes se sentam.
     * @param bar     bar do restaurante.
     */
    public Waiter(Kitchen kitchen, Table table, Bar bar) {
        this.kitchen = kitchen;
        this.table = table;
        this.bar = bar;
        this.setName("Waiter Thread");
    }

    /**
     * Setter do estado interno do Waiter.
     *
     * @param estado do Waiter.
     */
    public void setWaiterState(WaiterState state) {
        this.state = state;
    }

    /**
     * Getter do estado interno do Waiter.
     *
     * @return estado do Waiter.
     */
    public WaiterState getWaiterState() {
        return this.state;
    }
    /**
     * Ciclo de vida do estudante.
     */
    @Override
    public void run() {
        char alt;
        while ((alt = bar.lookAround()) != 'E') {
            switch (alt) {
                case 'N':
                    table.saluteTheClient();
                    break;
                case 'O':
                    table.getThePad();
                    kitchen.handNoteToChef();
                    break;
                case 'C':
                    do {
                        kitchen.collectPortion();
                        table.deliverPortion();
                    } while (!table.allStudentsServed());
                    break;
                case 'P':
                    bar.prepareBill();
                    table.presentBill();
                    break;
                case 'G':
                    bar.sayGoodBye();
                    break;
            }
            if (alt != 'G') {
                bar.returnToBar();
            }
        }
        bar.waiterFinished();
    }

}
