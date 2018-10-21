package entities;

import entities.states.StudentState;
import main.Constants;
import sharedRegions.interfaces.Bar;
import sharedRegions.interfaces.Table;

/**
 * Implementation of the entities.Student entity.
 */
public class Student extends Thread {

    /**
     * Referenciação da Table.
     */
    private Table table;
    /**
     * Referenciação do Bar.
     */
    private Bar bar;
    /**
     * Estado interno.
     */
    private StudentState state;
    /**
     * ID do estudante.
     */
    private int studentId;

    /**
     * @param table 			local onde se vão sentar os estudantes.
     * @param bar				local onde o waiter vai aguardar por operações.
     * @param studentId 		id
     */
    public Student(Table table, Bar bar, int studentId) {
        this.table = table;
        this.bar = bar;
        this.studentId = studentId;
    }

    /**
     * Getter do ID do estudante.
     */
    public int getStudentId() {
        return this.studentId;
    }

    /**
     * Assegurar que o ID é sempre passado pelo construtor.
     */
    @Override
    public long getId() {
        return studentId;
    }

    /**
     * Setter do estado interno do estudante.
     *
     * @param estado interno do estudante.
     */
    public void setStudentState(StudentState state) {
        this.state = state;
    }

    /**
     * Getter do estado interno do estudante.
     *
     * @return estado do estudante.
     */
    public StudentState getStudentState() {
        return this.state;
    }

    /**
     * Simulação da ida para o restaurante.
     */
    private void walkABit() {
        try {
            Thread.sleep((int) (Constants.STUDENT_WALK_TIME * Math.random()));
        } catch (InterruptedException e) {
        }
    }


    /**
     * Ciclo de vida do estudante.
     */
    @Override
    public void run() {
        int     FIRST = 1;
        int     LAST  = Constants.STUDENTS_NUMBER;
        boolean firstClient;
        boolean lastClient;
        int     arrivalOrder;
        walkABit();
        arrivalOrder = table.enter(studentId);
        firstClient = arrivalOrder == FIRST;
        lastClient = arrivalOrder == LAST;
        table.readMenu(studentId);
        if (firstClient) {
            while (!table.everybodyChosen()) {
                table.prepareOrder(studentId);
            }
            bar.callWaiter();
            table.describeOrder();
            table.joinTalk(studentId);
        } else {
            table.informCompanion(studentId);
        }
        boolean lastCourse;
        do {
            table.startEating(studentId);
            lastCourse = table.endEating(studentId);
            if (table.everybodyFinished()) {
                table.signalWaiter();
            }
        } while (!lastCourse);
        if (lastClient) {
            table.shouldArriveEarlier(studentId);
            table.honourBill();
        }
        table.exit(studentId);
    }
}
