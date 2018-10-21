package logging;


import entities.states.ChefState;
import entities.states.StudentState;
import entities.states.WaiterState;
import main.Constants;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementação da Interface do General Repository of Information.
 */
public class LoggerImpl implements Logger {
    /**
     * Garantia de acesso em regime de exlusão mútua.
     */
    private Lock mutex;

    /**
     * Estado atual do Chef.
     */
    private ChefState chefState;
    /**
     * Estado atual do Waiter.
     */
    private WaiterState waiterState;
    /**
     * Estado dos estudantes.
     */
    private StudentState[] studentStates;
    /**
     * Maps do Students Thread e ID de entrada.
     */
    private Map <Long, Integer> studentStateMap;
    /**
     * Posição seguinte do Map.
     */
    private int studentIndex;
    /**
     * Prato atual.
     */
    private int numberCourse;
    /**
     * Número total de estudantes.
     */
    private final int STUDENTS_TOTAL;

    /**
     * Formatação do Logger.
     */
    private final int formatSpaces;
    /**
     * Formatação das strings.
     */
    private final String formatString;
    /**
     * Referenciação do ficheiro.
     */
    private final BufferedWriter file;
    /**
     * Nome do ficheiro.
     */
    private final String filename;

    /**
     * Escrita de estados.
     */
    private final boolean debug = false;

    /**
     * Inicialização do logger.
     *
     * @param filename - Nome do ficheiro de log.
     *
     * @throws IOException- Se ocorre um erro de I/O
     */
    public LoggerImpl(String filename) throws IOException {
        mutex = new ReentrantLock();

        this.filename = filename;
        file = Files.newBufferedWriter(Paths.get(filename));

        this.waiterState = Constants.initialWaiterState;
        this.chefState = Constants.initialChefState;

        STUDENTS_TOTAL = Constants.STUDENTS_NUMBER;
        studentStates = new StudentState[STUDENTS_TOTAL];
        for (int i = 0; i < STUDENTS_TOTAL; i++) {
            studentStates[i] = Constants.initialStudentState;
        }
        studentStateMap = new HashMap<>();
        studentIndex = 0;

        formatSpaces = Constants.formatSpaces;
        formatString = "%" + formatSpaces + "s ";
        initFile();

    }

    public void updateCourse(int numberCourse) {
        mutex.lock();
        this.numberCourse = numberCourse;
        String courseLine = String.format("COURSE: %d %n", this.numberCourse);
        if (debug) {
            System.out.print(courseLine);
        }
        try {
            file.write(courseLine);
            file.flush();
        } catch (IOException e) {
            System.err.println("ERROR CODE 4: Unable to Update Course and write to file the line:\n" + courseLine);
            e.printStackTrace();
            System.exit(4);
        }
        mutex.unlock();
    }

    public void updateChefState(ChefState state) {
        mutex.lock();
        this.chefState = state;
        appendLine();
        mutex.unlock();
    }

    public void updateWaiterState(WaiterState state) {
        mutex.lock();
        this.waiterState = state;
        appendLine();
        mutex.unlock();
    }


    public void updateStudentState(long studentId, StudentState state) {
        mutex.lock();
        Integer stateIndex = studentStateMap.getOrDefault(studentId, null);
        if (stateIndex == null) {
            stateIndex = studentIndex;
            studentStateMap.put(studentId, studentIndex);
            studentIndex++;
        }
        studentStates[stateIndex] = state;
        appendLine();
        mutex.unlock();
    }

    @Override
    public void kitchenFinished() {}

    @Override
    public void barFinished() {}

    @Override
    public void tableFinished() {}

    /**
     * Inicialização do ficheiro de log.
     */
    private void initFile() {
        writeHeader();
        appendLine();
    }

    /**
     * Escrita do cabeçalho.
     */
    private void writeHeader() {
        String title = titleFormat();
        StringBuilder sb = new StringBuilder(title);
        sb.append(String.format(formatString, "C"));
        sb.append(String.format(formatString, "W"));
        for (int i = 1; i < STUDENTS_TOTAL + 1; i++) {
            sb.append(String.format(formatString, "S" + i));
        }
        sb.append(String.format("%n"));
        String headerString = sb.toString();
        if (debug) {
            System.out.print(headerString);
        }
        try {
            file.write(headerString);
            file.flush();
        } catch (IOException e) {
            System.err.println("ERROR CODE 2: Unable to write line\n" + headerString);
            e.printStackTrace();
            System.exit(2);
        }
    }

    /**
     * Formatação do título.
     *
     * @return linha de título.
     */
    private String titleFormat() {
        int waiterMult = 1;
        int chefMult = 1;
        int studentMult = STUDENTS_TOTAL;

        int preTotalSpaces = waiterMult + chefMult + studentMult;
        int totalSpaces = preTotalSpaces * formatSpaces + preTotalSpaces;
        String baseSpaces = "%" + totalSpaces + "s%n";
        int    nameSpaces = filename.length();
        if (nameSpaces >= totalSpaces) {
            return String.format(baseSpaces, filename);
        }
        int halfPoint = (int) (((double) totalSpaces / 2.0) + 0.5);
        int nameHalfPoint = (int) (((double) nameSpaces / 2.0) + 0.5);
        int indentNeeded = totalSpaces - halfPoint - nameHalfPoint;
        String indentString = "%" + indentNeeded + "s";
        return String.format(baseSpaces, filename + String.format(indentString, " "));

    }

    /**
     * Adicionar logs.
     */
    private void appendLine() {
        String statesLine = buildStatesLine();
        if (debug) {
            System.out.print(statesLine);
        }
        try {
            file.write(statesLine);
            file.flush();
        } catch (IOException e) {
            System.err.println("ERROR CODE 3: Unable to append line:\n" + statesLine);
            e.printStackTrace();
            System.exit(3);
        }

    }

    /**
     * Comstrução dos estados.
     */
    private String buildStatesLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(formatString, chefState));
        sb.append(String.format(formatString, waiterState));
        for (StudentState studentState : studentStates) {
            sb.append(String.format(formatString, studentState));
        }
        sb.append(String.format("%n"));
        return sb.toString();
    }
}
