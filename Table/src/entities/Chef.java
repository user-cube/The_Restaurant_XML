package entities;

import entities.states.ChefState;
import main.Constants;
import sharedRegions.interfaces.Kitchen;


/**
 * Implementação da entidade Chef.
 */
public class Chef extends Thread {
    /**
     * Referenciação da Kitchen.
     */
    private Kitchen kitchen;
    /**
     * Número total de estudantes.
     */
    private final int STUDENTS_TOTAL;
    /**
     * How many courses we're going to cook
     */
    private final int COURSES_TOTAL;
    /**
     * Estado interno do estudante.
     */
    @SuppressWarnings("unused")
	private ChefState state;

    /**
     * @param kitchen
     */
    public Chef(Kitchen kitchen) {
        this.kitchen = kitchen;
        this.COURSES_TOTAL = Constants.COURSES_NUMBER;
        this.STUDENTS_TOTAL = Constants.STUDENTS_NUMBER;
        this.setName("Chef Thread");
    }

    /**
     * Ciclo de vida do Chef.
     */
    @Override
    public void run() {
        kitchen.watchNews();
        kitchen.startPreparation();
        for (int numberCourses = 0; numberCourses < COURSES_TOTAL; numberCourses++) {
            kitchen.proceedToPresentation();
            for (int numberPortion = 0; numberPortion < STUDENTS_TOTAL; numberPortion++) {
                kitchen.alertWaiter();
                if (!kitchen.allPortionsDelivered()) {
                    kitchen.haveNextPortionReady();
                }
            }
            if (!kitchen.orderCompleted()) {
                kitchen.continuePreparation();
            }
        }
        kitchen.cleanUp();
    }

    /**
     * Setter do estado interno do Chef.
     * @param estado do chef.
     */
    public void setState(ChefState state) {
        this.state = state;
    }
}
