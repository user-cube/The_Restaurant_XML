package sharedRegions.table;


import entities.states.StudentState;
import entities.states.WaiterState;
import logging.Logger;
import main.Constants;
import sharedRegions.interfaces.Bar;
import sharedRegions.interfaces.Kitchen;
import sharedRegions.interfaces.Table;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation da table.
 */
public class RestaurantTable implements Table {
    /**
     * Garantia de acesso em regime de exlusão mútua.
     */
    private final Lock mutex;

    /**
     * Referencia ao logger.
     */
    private final Logger infoLogger;
    /**
     * Referencia ao bar.
     */
    private final Bar bar;
    /**
     * Referencia a kitchen.
     */
    private final Kitchen kitchen;
    /**
     * Número de estudantes.
     */
    private final int nStudents;
    /**
     * Número de pratos.
     */
    private final int totalCourses;

    /**
     * Condição que garante que o waiter saudou os estudantes.
     */
    private final Condition waiterSalutedStudent;
    /**
     * Variável de controlo que permite a garantia que todos os estudantes foram saudados.
     */
    private boolean waiterSalutedStudentFlag;

    /**
     * Condição que garante que todos os estudantes leram o menu.
     */
    private final Condition studentFinishReadingTheMenu;
    /**
     * Variável de controlo que permite a garantia que todos os estudantes leram o menu.
     */
    private boolean studentFinishReadingTheMenuFlag;

    /**
     * Condição que garante que todos os estudantes terminaram de escolher o seu pedido.
     */
    private final Condition studentFinishedChoosing;
    
    /**
     * Variável de controlo que permite a garantia que todos os estudantes termiram de escolher.s 
     */
    private boolean studentFinishedChoosingFlag;
    
    /**
     * Verificação se foi o primeiro a chegar.
     */
    private boolean firstOrder;

    /**
     * Refeições escolhidas.
     */
    private int mealsChosen;

    /**
     * Condição que garante que o Waiter já tem o pedido dos estudantes.
     */
    private final Condition waiterHasThePad;
    /**
     * Variável de controlo que permite garantir que o Waiter já tem o pedido dos estudantes.
     */
    private boolean waiterHasThePadFlag;

    /**
     * Condição que garante que os estudantes já descreveram o seu pedido.
     */
    private final Condition studentDescribedOrder;
    /**
     * Variável de controlo que permite garantir que o estudante já descreveu o seu pedido.
     */
    private boolean studentDescribedOrderFlag;
    /**
     * Número de estudantes servidos.
     */
    private int studentsServed;

    /**
     * Condição que garante que toda a comida já chegou.
     */
    private final Condition allFoodArrived;
    /**
     * Variável de controlo que permite garantir que toda a comida já chegou.
     */
    private boolean allFoodArrivedFlag;
    /**
     * Condição que garante que todos já acabaram de comer o seu prato.
     */
    private final Condition finishedEatingCourse;
    /**
     * Variável de controlo que permite garantir que todos terminaram o seu prato.
     */
    private boolean finishedEatingCourseFlag;
    /**
     * Número de estudantes que terminaram.
     */
    private int studentsFinishedEating;
    /**
     * Condição que garante que todos os estudantes terminaram a sua refeição.
     */
    private final Condition everybodyFinishedEating;
    /**
     * Variável de controlo que permite garantir que todos os estudantes termiram a sua refeição.
     */
    private boolean everybodyFinishedEatingFlag;
    /**
     * Número do prato.
     */
    private int courseNumber;
    /**
     * Condição que garante que a conta foi apresentada ao último estudante.
     */
    private final Condition billReady;
    /**
     * Variável de controlo que permite garantir que a conta foi entregue ao último estudante.
     */
    private boolean billReadyFlag;
    /**
     * Condição que garante que a conta foi paga.
     */
    private final Condition paidBill;
    /**
     * Variável de controlo que permite garantir que a conta foi paga.
     */
    private boolean paidBillFlag;

    public RestaurantTable(Logger log, Bar bar, Kitchen kitchen) {

        mutex = new ReentrantLock();
        this.infoLogger = log;
        this.bar = bar;
        this.kitchen = kitchen;
        this.nStudents = Constants.STUDENTS_NUMBER;
        this.totalCourses = Constants.COURSES_NUMBER;

        waiterSalutedStudent = mutex.newCondition();
        waiterSalutedStudentFlag = false;

        studentFinishReadingTheMenu = mutex.newCondition();
        studentFinishReadingTheMenuFlag = false;

        studentFinishedChoosing = mutex.newCondition();
        firstOrder = true;
        studentFinishedChoosingFlag = false;
        mealsChosen = 0;

        waiterHasThePad = mutex.newCondition();
        waiterHasThePadFlag = false;
        studentDescribedOrder = mutex.newCondition();
        studentDescribedOrderFlag = false;

        studentsServed = 0;
        allFoodArrived = mutex.newCondition();
        allFoodArrivedFlag = false;
        finishedEatingCourse = mutex.newCondition();
        finishedEatingCourseFlag = false;
        studentsFinishedEating = 0;
        everybodyFinishedEating = mutex.newCondition();
        everybodyFinishedEatingFlag = false;

        courseNumber = 1;

        billReady = mutex.newCondition();
        billReadyFlag = false;
        paidBill = mutex.newCondition();
        paidBillFlag = false;
    }


    @Override
    public int enter(long studentId) {
        mutex.lock();
        int arrivalOrder = bar.enter();
        infoLogger.updateStudentState(studentId, StudentState.TAKING_SEAT_AT_TABLE);
        while (!waiterSalutedStudentFlag) {
            try {
                waiterSalutedStudent.await();
            } catch (InterruptedException e) {
            }
        }
        waiterSalutedStudentFlag = false;
        mutex.unlock();
        return arrivalOrder;
    }

    @Override
    public void saluteTheClient() {
        mutex.lock();
        infoLogger.updateWaiterState(WaiterState.PRESENTING_MENU);
        waiterSalutedStudent.signal();
        waiterSalutedStudentFlag = true;
        while (!studentFinishReadingTheMenuFlag) {
            try {

                studentFinishReadingTheMenu.await();
            } catch (InterruptedException e) {
            }
        }
        studentFinishReadingTheMenuFlag = false;
        mutex.unlock();
    }

    @Override
    public void readMenu(long studentId) {
        mutex.lock();
        infoLogger.updateStudentState(studentId, StudentState.SELECTING_COURSES);
        studentFinishReadingTheMenu.signal();
        studentFinishReadingTheMenuFlag = true;
        mutex.unlock();

    }

    @Override
    public boolean everybodyChosen() {
        boolean ret = false;
        mutex.lock();
        if (mealsChosen == nStudents - 1) {
            ret = true;
        }
        mutex.unlock();
        return ret;
    }

    @Override
    public void prepareOrder(long studentId) {
        mutex.lock();
        if (firstOrder) {
            infoLogger.updateStudentState(studentId, StudentState.ORGANIZING_ORDER);
            firstOrder = false;
        }
        while (!studentFinishedChoosingFlag) {
            try {
                studentFinishedChoosing.await();
            } catch (InterruptedException e) {
            }
        }
        studentFinishedChoosingFlag = false;
        mutex.unlock();
    }

    @Override
    public void describeOrder() {
        mutex.lock();
        while (!waiterHasThePadFlag) {
            try {
                waiterHasThePad.await();
            } catch (InterruptedException e) {
            }
        }
        studentDescribedOrder.signal();
        studentDescribedOrderFlag = true;
        mutex.unlock();
    }


    @Override
    public void getThePad() {
        mutex.lock();
        infoLogger.updateWaiterState(WaiterState.TAKING_ORDER);
        waiterHasThePad.signal();
        waiterHasThePadFlag = true;
        while (!studentDescribedOrderFlag) {
            try {
                studentDescribedOrder.await();
            } catch (InterruptedException e) {
            }
        }
        mutex.unlock();

    }

    @Override
    public void joinTalk(long studentId) {
        mutex.lock();
        infoLogger.updateStudentState(studentId, StudentState.CHATTING_WITH_COMPANIONS);
        while (!allFoodArrivedFlag) {
            try {
                allFoodArrived.await();
            } catch (InterruptedException e) {
            }
        }
        mutex.unlock();
    }

    @Override
    public void informCompanion(long studentId) {
        mutex.lock();
        mealsChosen++;
        infoLogger.updateStudentState(studentId, StudentState.CHATTING_WITH_COMPANIONS);
        studentFinishedChoosing.signal();
        studentFinishedChoosingFlag = true;
        while (!allFoodArrivedFlag) {
            try {
                allFoodArrived.await();
            } catch (InterruptedException e) {
            }
        }
        mutex.unlock();
    }

    @Override
    public void deliverPortion() {
        mutex.lock();
        studentsServed++;
        mutex.unlock();
    }

    @Override
    public boolean allStudentsServed() {
        boolean ret = false;
        mutex.lock();
        if (studentsServed == nStudents) {
            ret = true;
            studentsServed = 0;
            allFoodArrived.signalAll();
            allFoodArrivedFlag = true;
            kitchen.allStudentsServed();
            while (!finishedEatingCourseFlag) {
                try {
                    finishedEatingCourse.await();
                } catch (InterruptedException e) {
                }
            }
            finishedEatingCourseFlag = false;
            if (courseNumber < totalCourses) {
                courseNumber++;
                infoLogger.updateCourse(courseNumber);
            }
        }
        mutex.unlock();
        return ret;
    }

    @Override
    public void startEating(long studentId) {
        mutex.lock();
        infoLogger.updateStudentState(studentId, StudentState.ENJOYING_MEAL);
        mutex.unlock();
    }

    @Override
    public boolean endEating(long studentId) {
        boolean ret;
        mutex.lock();
        infoLogger.updateStudentState(studentId, StudentState.CHATTING_WITH_COMPANIONS);
        ret = (courseNumber == totalCourses);
        mutex.unlock();
        return ret;
    }

    @Override
    public boolean everybodyFinished() {
        boolean ret = false;
        mutex.lock();
        studentsFinishedEating++;
        everybodyFinishedEatingFlag = false;
        if (studentsFinishedEating == nStudents) {
            ret = true;
            studentsFinishedEating = 0;
            allFoodArrivedFlag = false;
            everybodyFinishedEating.signalAll();
            everybodyFinishedEatingFlag = true;
        }
        else {
            while (!everybodyFinishedEatingFlag) {
                try {
                    everybodyFinishedEating.await();
                } catch (InterruptedException e) {
                }
            }
            if (courseNumber < totalCourses) {
                while (!allFoodArrivedFlag) {
                    try {
                        allFoodArrived.await();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
        mutex.unlock();
        return ret;
    }

    @Override
    public void signalWaiter() {
        mutex.lock();
        finishedEatingCourse.signal();
        finishedEatingCourseFlag = true;
        if (courseNumber < totalCourses) {
            while (!allFoodArrivedFlag) {
                try {
                    allFoodArrived.await();
                } catch (InterruptedException e) {
                }
            }
        }
        mutex.unlock();
    }

    @Override
    public void shouldArriveEarlier(long studentId) {
        mutex.lock();
        infoLogger.updateStudentState(studentId, StudentState.PAYING_MEAL);
        bar.shouldArriveEarlier();
        while (!billReadyFlag) {
            try {
                billReady.await();
            } catch (InterruptedException e) {
            }
        }
        mutex.unlock();
    }

    @Override
    public void presentBill() {
        mutex.lock();
        infoLogger.updateWaiterState(WaiterState.RECEIVING_PAYMENT);
        billReady.signal();
        billReadyFlag = true;
        while (!paidBillFlag) {
            try {
                paidBill.await();
            } catch (InterruptedException e) {
            }
        }
        mutex.unlock();
    }


    @Override
    public void honourBill() {
        mutex.lock();
        paidBill.signalAll();
        paidBillFlag = true;
        mutex.unlock();
    }

    @Override
    public void exit(long studentId) {
        mutex.lock();
        while (!paidBillFlag) {
            try {
                paidBill.await();
            } catch (InterruptedException e) {
            }
        }
        mutex.unlock();
        bar.exit(studentId);

    }
}
