package sharedRegions.bar;

import commonStructures.ExtendedFIFO;
import entities.states.StudentState;
import entities.states.WaiterState;
import logging.Logger;
import main.Constants;
import sharedRegions.interfaces.Bar;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementação do Bar.
 */
public class DistBar implements Bar {

    /**
     * Garantia de acesso em regime de exlusão mútua.
     */
    private final Lock mutex;
    /**
     * Condições para gestão das tarefas do Waiter.
     */
    private final Condition waiterTask;

    private final Condition waitForGoodbye;
    private boolean studentWasSaidGoodbye;

    private final Condition waiterCanSayGoodbye;
    private boolean waiterCanSayGoodbyeFlag;
    /**
     * Referenciação do logger onde vão ser feitos os updates de estados.
     */
    private final Logger infoLogger;
    /**
     * Número de estudantes no Restaurante.
     */
    private final int nStudents;

    /**
     * Ordem de chegada.
     */
    private int studentArrivalOrder;

    /**
     * Variável de controlo.
     */
    private int tasksLeft;
    /**
     * FIFO de armazenamento de tarefas.
     */
    private ExtendedFIFO<Character> tasksToDo;

    public static final char EXIT         = 'E';
    public static final char SALUTE       = 'N';
    public static final char ORDER        = 'O';
    public static final char COLLECT      = 'C';
    public static final char SAY_GOODBYE  = 'G';
    public static final char PROCESS_BILL = 'P';
    /**
     * Número de estudantes que saíram do Restaurante.
     */
    private int studentsExited;

    public DistBar(Logger infoLogger) {
        mutex = new ReentrantLock();
        waiterTask = mutex.newCondition();
        waitForGoodbye = mutex.newCondition();
        waiterCanSayGoodbye = mutex.newCondition();
        
        studentWasSaidGoodbye = false;
        waiterCanSayGoodbyeFlag = false;

        tasksLeft = 0;
        studentArrivalOrder = 0;
        studentsExited = 0;
        
        this.infoLogger = infoLogger;
        this.nStudents = Constants.STUDENTS_NUMBER;
        this.tasksToDo = new ExtendedFIFO<>(nStudents * nStudents);
        
    }

    @Override
    public char lookAround() {
        char task;
        mutex.lock();
        while (tasksLeft == 0) {
            try {
                waiterTask.await();
            } catch (InterruptedException e) {
            }
        }
        task = tasksToDo.remove();
        tasksLeft--;
        mutex.unlock();
        return task;

    }

    @Override
    public void returnToBar() {
        mutex.lock();
        infoLogger.updateWaiterState(WaiterState.APPRAISING_SITUATION);
        mutex.unlock();
    }

    @Override
    public int enter() {
        mutex.lock();
        studentArrivalOrder++;
        int ret = studentArrivalOrder;
        tasksToDo.insert(SALUTE);
        waiterTask.signal();
        tasksLeft++;
        mutex.unlock();
        return ret;
    }

    @Override
    public void callWaiter() {
        mutex.lock();
        tasksToDo.insert(ORDER);
        waiterTask.signal();
        tasksLeft++;
        mutex.unlock();
    }

    @Override
    public void alertWaiter() {
        mutex.lock();
        tasksToDo.insert(COLLECT);
        waiterTask.signal();
        tasksLeft++;
        mutex.unlock();
    }

    @Override
    public void prepareBill() {
        mutex.lock();
        infoLogger.updateWaiterState(WaiterState.PROCESSING_BILL);
        mutex.unlock();
    }

    @Override
    public void shouldArriveEarlier() {
        mutex.lock();
        tasksToDo.insert(PROCESS_BILL);
        waiterTask.signal();
        tasksLeft++;
        mutex.unlock();
    }

    @Override
    public void sayGoodBye() {
        mutex.lock();
        while (!waiterCanSayGoodbyeFlag) {
            try {
                waiterCanSayGoodbye.await();
            } catch (InterruptedException e) {
            }
        }
        waitForGoodbye.signal();
        studentWasSaidGoodbye = true;
        studentsExited--;
        mutex.unlock();
    }

    @Override
    public void exit(long id) {
        mutex.lock();
        studentsExited++;
        tasksToDo.insert(SAY_GOODBYE);
        waiterTask.signal();
        tasksLeft++;
        if (studentsExited == nStudents) {
            waiterCanSayGoodbye.signal();
            waiterCanSayGoodbyeFlag = true;
            tasksToDo.insert(EXIT);
            waiterTask.signal();
            tasksLeft++;
        }
        while (!studentWasSaidGoodbye) {
            try {
                waitForGoodbye.await();
            } catch (InterruptedException e) {
            }
        }
        infoLogger.updateStudentState(id, StudentState.GOING_HOME);
        mutex.unlock();
    }

    @Override
    public void waiterFinished() {}
}

