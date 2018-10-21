package sharedRegions.bar;

import commonStructures.ClientCom;
import commonStructures.Message;
import sharedRegions.interfaces.Bar;
import sharedRegions.interfaces.SharedRegionStub;

/**
 * Stub do Bar.
 */
public class BarStub extends SharedRegionStub implements Bar {
    /**
     * Instanciação do Stub do Bar.
     *
     * @param hostName nome do computador em que o Bar se encontra alojado.
     * @param port     porta em que o servidor se encontra à escuta.
     */
    public BarStub(String hostName, int port) {
        super(hostName, port);
    }

    @Override
    public char lookAround() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.LOOK_AROUND);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.LOOK_AROUND_RESPONSE);
        con.close();
        return inMessage.getTask();
    }

    @Override
    public void returnToBar() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.RETURN_TO_BAR);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public int enter() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.BAR_ENTER);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.BAR_ENTER_RESPONSE);
        con.close();
        return inMessage.getArrivalOrder();
    }

    @Override
    public void callWaiter() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.CALL_WAITER);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();

    }

    @Override
    public void alertWaiter() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.BAR_ALERT_WAITER);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void prepareBill() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.PREPARE_BILL);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void shouldArriveEarlier() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.BAR_SHOULD_ARRIVE_EARLIER);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void sayGoodBye() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.SAY_GOODBYE);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void exit(long id) {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.BAR_EXIT).setStudentId(id);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void waiterFinished() {
        ClientCom clientCom = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.WAITER_FINISHED);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        clientCom.writeObject (outString);  
        inMessage = waitForMessage(clientCom, Message.Type.LOGGER_MESSAGE_RECEIVED);
        clientCom.close();
    }


}
