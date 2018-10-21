package logging;

import entities.states.ChefState;
import entities.states.StudentState;
import entities.states.WaiterState;

/**
 * Interface de definição do Logger (General Repository of Information)
 */
public interface Logger {
    /**
     * Update do número do prato.
     *
     * @param numberCourse - Número do prato.
     */
    void updateCourse(int numberCourse);

    /**
     * Update do estado do Waiter.
     *
     * @param state - Estado do Waiter.
     */
    void updateWaiterState(WaiterState state);

    /**
     * Update do estado do Chef.
     *
     * @param state - Estado do Chef.
     */
    void updateChefState(ChefState state);

    /**
     * Update do estado do Student.
     *
     * @param studentId ID do estudante.
     * @param state     estado do estudante.
     */
    void updateStudentState(long studentId, StudentState state);

    /**
     * Encerramento da Kitchen.
     */
    void kitchenFinished();

    /**
     * Encerramento do Bar.
     */
    void barFinished();

    /**
     * Encerramento da Table.
     */
    void tableFinished();
}
