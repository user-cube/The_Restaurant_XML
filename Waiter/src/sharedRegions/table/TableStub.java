package sharedRegions.table;

import commonStructures.ClientCom;
import commonStructures.Message;
import entities.states.StudentState;
import sharedRegions.interfaces.SharedRegionStub;
import sharedRegions.interfaces.Table;

/**
 * Stub da Table.
 */
public class TableStub extends SharedRegionStub implements Table {
    /**
     * Instanciação da Table.
     *
     * @param hostName nome do computador em que o servidor está alojado.
     * @param port     porta em que o servidor está à escuta.
     */
    public TableStub(String hostName, int port) {
        super(hostName, port);
    }


    @Override
    public int enter(long studentId) {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.TABLE_ENTER).setStudentId(studentId);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.TABLE_ENTER_RESPONSE);
        con.close();
        return inMessage.getArrivalOrder();

    }

    @Override
    public void saluteTheClient() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.SALUTE_CLIENT);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void readMenu(long studentId) {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.READ_MENU).setStudentId(studentId);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();

    }

    @Override
    public boolean everybodyChosen() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.EVERYBODY_CHOSEN);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.EVERYBODY_CHOSEN_RESPONSE);
        con.close();
        return inMessage.getEverybodyChosen();
    }

    @Override
    public void prepareOrder(long studentId) {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.PREPARE_ORDER).setStudentId(studentId);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void describeOrder() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.DESCRIBE_ORDER);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void getThePad() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.GET_THE_PAD);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void joinTalk(long studentId) {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.JOIN_TALK).setStudentId(studentId)
                                                        .setStudentState(StudentState.CHATTING_WITH_COMPANIONS);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void informCompanion(long studentId) {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.INFORM_COMPANION).setStudentId(studentId)
                                                               .setStudentState(StudentState.CHATTING_WITH_COMPANIONS);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void deliverPortion() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.DELIVER_PORTION);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();

    }

    @Override
    public boolean allStudentsServed() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.TABLE_ALL_STUDENTS_SERVED);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.TABLE_ALL_STUDENTS_SERVED_RESPONSE);
        con.close();
        return inMessage.getTableAllStudentsServed();
    }

    @Override
    public void startEating(long studentId) {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.START_EATING).setStudentId(studentId);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public boolean endEating(long studentId) {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.END_EATING).setStudentId(studentId);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.END_EATING_RESPONSE);
        con.close();
        return inMessage.getEndEating();
    }

    @Override
    public boolean everybodyFinished() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.EVERYBODY_FINISHED);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.EVERYBODY_FINISHED_RESPONSE);
        con.close();
        return inMessage.getEverybodyFinished();
    }

    @Override
    public void signalWaiter() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.SIGNAL_WAITER);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void shouldArriveEarlier(long studentId) {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.TABLE_SHOULD_ARRIVE_EARLIER).setStudentId(studentId);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void presentBill() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.PRESENT_BILL);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }


    @Override
    public void honourBill() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.HONOUR_BILL);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void exit(long studentId) {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.TABLE_EXIT).setStudentId(studentId);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }
}
