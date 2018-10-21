package sharedRegions.kitchen;

import entities.states.ChefState;
import entities.states.WaiterState;
import logging.Logger;
import main.Constants;
import sharedRegions.interfaces.Bar;
import sharedRegions.interfaces.Kitchen;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementação da Kitchen.
 */
public class RestaurantKitchen implements Kitchen {
    /**
     * Garantia de acesso em regime de exclusão mútua.
     */
    private final Lock mutex;


    /**
     * Referenciação do logger.
     */
    private Logger infoLogger;
    /**
     * Referenciação do bar.
     */
    private Bar bar;
    /**
     * Número de estudantes.
     */
    private final int nStudents;
    /**
     * Número de pratos.
     */
    private final int totalCourses;

    /**
     * Condição para gerir o Chef.
     */
    private final Condition watchNews;
    /**
     * Variável de controlo.
     */
    private boolean chefHasOrder;

    /**
     * Coindição para gerir a preparação.
     */
    private final Condition chefReceivedOrder;
    /**
     * Variável de condição.
     */
    private boolean chefReceivedOrderFlag;

    /**
     * Condição para gerir as porções coletadas.
     */
    private final Condition chefGavePortionToWaiter;
    /**
     * Variável de condição.
     */
    private boolean chefGavePortionToWaiterFlag;

    /**
     * Condição.
     */
    private final Condition waiterReadyToDeliverPortion;
    /**
     * Variável de condição.
     */
    private boolean waiterReadyToDeliverPortionFlag;
    /**
     * Condição para gerir a entrega de porções.
     */
    private final Condition allDelivered;
    /**
     * Variável de condição.
     */
    private boolean allDeliveredFlag;

    /**
     * Número de pratos prontos.
     */
    private int courseNumber;
    /**
     * Número de porções entregues.
     */
    private int portionsDelivered;


    public RestaurantKitchen(Logger logger, Bar bar) {

        mutex = new ReentrantLock();
        chefGavePortionToWaiter = mutex.newCondition();
        chefReceivedOrder = mutex.newCondition();
        watchNews = mutex.newCondition();
        waiterReadyToDeliverPortion = mutex.newCondition();
        allDelivered = mutex.newCondition();

        this.bar = bar;
        infoLogger = logger;
        allDeliveredFlag = false;
        chefGavePortionToWaiterFlag = false;
        waiterReadyToDeliverPortionFlag = false;
        chefHasOrder = false;
        chefReceivedOrderFlag = false;

        this.totalCourses = Constants.COURSES_NUMBER;
        this.nStudents = Constants.STUDENTS_NUMBER;

        courseNumber = 1;
        portionsDelivered = 0;

    }

    @Override
    public void watchNews() {
        mutex.lock();
        while (!chefHasOrder) {
            try {
                watchNews.await();
            } catch (InterruptedException e) {
            }
        }
        mutex.unlock();
    }


    @Override
    public void handNoteToChef() {
        mutex.lock();
        infoLogger.updateWaiterState(WaiterState.PLACING_ORDER);
        watchNews.signal();
        chefHasOrder = true;
        while (!chefReceivedOrderFlag) {
            try {
                chefReceivedOrder.await();
            } catch (InterruptedException e) {
            }
        }
        mutex.unlock();
    }

    @Override
    public void startPreparation() {
        mutex.lock();
        infoLogger.updateCourse(courseNumber);
        infoLogger.updateChefState(ChefState.PREPARING_COURSE);
        chefReceivedOrder.signal();
        chefReceivedOrderFlag = true;
        mutex.unlock();
    }

    @Override
    public void proceedToPresentation() {
        mutex.lock();
        portionsDelivered = 0;
        infoLogger.updateChefState(ChefState.DISHING_PORTIONS);
        mutex.unlock();
    }

    @Override
    public void alertWaiter() {
        mutex.lock();
        infoLogger.updateChefState(ChefState.DELIVERING_PORTIONS);
        if (portionsDelivered == 0) {
            bar.alertWaiter();
        }
        while (!waiterReadyToDeliverPortionFlag) {
            try {
                waiterReadyToDeliverPortion.await();
            } catch (InterruptedException e) {
            }
        }
        portionsDelivered++;
        waiterReadyToDeliverPortionFlag = false;
        chefGavePortionToWaiter.signal();
        chefGavePortionToWaiterFlag = true;
        mutex.unlock();
    }

    @Override
    public void collectPortion() {
        mutex.lock();
        if (portionsDelivered == 0) {
            infoLogger.updateWaiterState(WaiterState.WAITING_FOR_PORTION);
        }
        waiterReadyToDeliverPortion.signal();
        waiterReadyToDeliverPortionFlag = true;
        while (!chefGavePortionToWaiterFlag && (portionsDelivered < nStudents)) {
            try {
                chefGavePortionToWaiter.await();
            } catch (InterruptedException e) {
            }
        }
        chefGavePortionToWaiterFlag = false;
        mutex.unlock();
    }

    @Override
    public boolean allPortionsDelivered() {
        boolean ret = false;
        mutex.lock();
        if (portionsDelivered == nStudents) {
            ret = true;
            while (!allDeliveredFlag) {
                try {
                    allDelivered.await();
                } catch (InterruptedException e) {
                }
            }
            allDeliveredFlag = false;
        }
        mutex.unlock();
        return ret;
    }

    @Override
    public void allStudentsServed() {
        mutex.lock();
        allDelivered.signal();
        allDeliveredFlag = true;
        mutex.unlock();
    }

    @Override
    public void haveNextPortionReady() {
        mutex.lock();
        infoLogger.updateChefState(ChefState.DISHING_PORTIONS);
        mutex.unlock();
    }

    @Override
    public void continuePreparation() {
        mutex.lock();
        courseNumber++;
        infoLogger.updateChefState(ChefState.PREPARING_COURSE);
        mutex.unlock();
    }

    @Override
    public boolean orderCompleted() {
        boolean ret;
        mutex.lock();
        ret = (courseNumber == totalCourses);
        mutex.unlock();
        return ret;
    }

    @Override
    public void cleanUp() {
        mutex.lock();
        infoLogger.updateChefState(ChefState.CLOSING_SERVICE);
        mutex.unlock();
    }

}