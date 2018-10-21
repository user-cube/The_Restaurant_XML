package logging;

import commonStructures.ClientCom;
import commonStructures.Message;
import entities.states.ChefState;
import entities.states.StudentState;
import entities.states.WaiterState;
import sharedRegions.interfaces.SharedRegionStub;

/**
 * Stub do Logger - (General Repository of Information)
 */
public class LoggerStub extends SharedRegionStub implements Logger {

    /**
     * Instanciação do logger (General Repository of Information)
     *
     * @param hostName nome do computador onde o server está alojado.
     * @param port     porta em que o servidor se encontra à escuta.
     */
    public LoggerStub(String hostName, int port) {
        super(hostName, port);
    }


    @Override
    public void updateWaiterState(WaiterState state) {
        ClientCom clientCom = createAndOpenConnection();
        Message   outMessage;

        outMessage = new Message(Message.Type.UPDATE_WAITER)
                .setWaiterState(state);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        clientCom.writeObject (outString);  
        waitForMessage(clientCom, Message.Type.LOGGER_MESSAGE_RECEIVED);
        clientCom.close();
    }


    @Override
    public void updateStudentState(long studentId, StudentState state) {
        ClientCom clientCom = createAndOpenConnection();
        Message   outMessage;
        outMessage = new Message(Message.Type.UPDATE_STUDENT)
                .setStudentState(state).setStudentId(studentId);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        clientCom.writeObject (outString);  
        waitForMessage(clientCom, Message.Type.LOGGER_MESSAGE_RECEIVED);
        clientCom.close();
    }

    @Override
    public void updateChefState(ChefState state) {
        ClientCom clientCom = createAndOpenConnection();
        Message   outMessage;
        outMessage = new Message(Message.Type.UPDATE_CHEF)
                .setChefState(state);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        clientCom.writeObject (outString);  
        waitForMessage(clientCom, Message.Type.LOGGER_MESSAGE_RECEIVED);
        clientCom.close();
    }

    @Override
    public void updateCourse(int numberCourse) {
        ClientCom clientCom = createAndOpenConnection();
        Message   outMessage;
        outMessage = new Message(Message.Type.UPDATE_COURSE)
                .setNumberCourse(numberCourse);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        clientCom.writeObject (outString);  
        waitForMessage(clientCom, Message.Type.LOGGER_MESSAGE_RECEIVED);
        clientCom.close();
    }

    @Override
    public void kitchenFinished() {
        ClientCom clientCom = createAndOpenConnection();
        Message   outMessage;
        outMessage = new Message(Message.Type.KITCHEN_FINISHED);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        clientCom.writeObject (outString);  
        waitForMessage(clientCom, Message.Type.LOGGER_MESSAGE_RECEIVED);
        clientCom.close();
    }

    @Override
    public void barFinished() {
        ClientCom clientCom = createAndOpenConnection();
        Message   outMessage;
        outMessage = new Message(Message.Type.BAR_FINISHED);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        clientCom.writeObject (outString);  
        waitForMessage(clientCom, Message.Type.LOGGER_MESSAGE_RECEIVED);
        clientCom.close();
    }

    @Override
    public void tableFinished() {
        ClientCom clientCom = createAndOpenConnection();
        Message   outMessage;
        outMessage = new Message(Message.Type.TABLE_FINISHED);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        clientCom.writeObject (outString);  
        waitForMessage(clientCom, Message.Type.LOGGER_MESSAGE_RECEIVED);
        clientCom.close();
    }

}
