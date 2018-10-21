package main;


import entities.Student;
import sharedRegions.bar.BarStub;
import sharedRegions.interfaces.Bar;
import sharedRegions.interfaces.Table;
import sharedRegions.table.TableStub;

/**
 * Main do Estudante.
 */
public class MainStudent {


    public static void main(String[] args) {
        Bar   bar   = new BarStub(Constants.barHostName, Constants.barPort);
        Table table = new TableStub(Constants.tableHostName, Constants.tablePort);
        Student[] students = new Student[Constants.STUDENTS_NUMBER];
        for (int i = 0; i < Constants.STUDENTS_NUMBER; i++) {
            students[i] = new Student(table, bar, i);
        }
        for (Student student : students) {
            student.start();
        }
        for (Student student : students) {
            try {
                student.join();
            } catch (InterruptedException e) {
                System.err.println("ERROR CODE 5: Could not join with entities.Student + " + student.getId() + "!");
                System.exit(5);
            }
        }
    }
}

