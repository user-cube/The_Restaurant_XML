package main;


import entities.states.ChefState;
import entities.states.StudentState;
import entities.states.WaiterState;

public final class Constants {

    /**
     * Número de estudantes.
     */
    public static final int STUDENTS_NUMBER = 7;

    /**
     * Número de pratos.
     */
    public static final int COURSES_NUMBER = 3;

    /**
     * Tempo de caminhada dos estudantes até ao Restaurante.
     */
    public static final int STUDENT_WALK_TIME = 15;

    /**
     * Nome do ficheiro de log.
     */
    public static final String fileName = "TheRestaurant-CD.txt";

    /**
     * Formatação do logger.
     */
    public static final int formatSpaces = 15;
    /**
     * Estado inicial do Waiter.
     */
    public static final WaiterState initialWaiterState = WaiterState.APPRAISING_SITUATION;
    /**
     * Estado inicial do estudante.
     */
    public static final StudentState initialStudentState = StudentState.GOING_TO_RESTAURANT;
    /**
     * Estado inicial do Chef.
     */
    public static final ChefState initialChefState = ChefState.WAITING_FOR_ORDER;

    /**
     * Localização do Bar.
     */
    public static final String barHostName = "l040101-ws01.ua.pt";
    /**
     * Porta do Bar.
     */
    public static final int barPort = 22510;

    /**
     * Localização da Kitchen.
     */
    public static final String kitchenHostName = "l040101-ws02.ua.pt";
    /**
     * Porta da Kitchen.
     */
    public static final int kitchenPort = 22511;

    /**
     * Localização da Table
     */
    public static final String tableHostName = "l040101-ws03.ua.pt";
    /**
     * Porta da table.
     */
    public static final int tablePort = 22512;
    /**
     * Localização do servidor.
     */
    public static final String loggerHostName  = "l040101-ws04.ua.pt";
    /**
     * Port of the Logger
     */
    public static final int loggerPort = 22513;
    
}
