package sharedRegions.kitchen;

import commonStructures.ClientCom;
import commonStructures.Message;
import sharedRegions.interfaces.Kitchen;
import sharedRegions.interfaces.SharedRegionStub;

/**
 * Stub da Kitchen
 */
public class KitchenStub extends SharedRegionStub implements Kitchen {
    /**
     * Instaciação da Kitchen
     *
     * @param hostName nome da máquina em que o servidor se encontra alojado.
     * @param port     server's listening  port number
     */
    public KitchenStub(String hostName, int port) {
        super(hostName, port);
    }

    @Override
    public void watchNews() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.WATCH_NEWS);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();

    }

    @Override
    public void handNoteToChef() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.HAND_NOTE_TO_CHEF);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();

    }

    @Override
    public void startPreparation() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.START_PREPARATION);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void proceedToPresentation() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.PROCEED_TO_PRESENTATION);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();

    }

    @Override
    public void alertWaiter() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.KITCHEN_ALERT_WAITER);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void collectPortion() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.COLLECT_PORTION);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public boolean allPortionsDelivered() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.ALL_PORTIONS_DELIVERED);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.ALL_PORTIONS_DELIVERED_RESPONSE);
        con.close();
        return inMessage.getKitchenAllPortionsDelivered();
    }

    @Override
    public void allStudentsServed() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.KITCHEN_ALL_STUDENTS_SERVED);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void haveNextPortionReady() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.HAVE_NEXT_PORTION_READY);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public void continuePreparation() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.CONTINUE_PREPARATION);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }

    @Override
    public boolean orderCompleted() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.ORDER_COMPLETED);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.ORDER_COMPLETED_RESPONSE);
        con.close();
        return inMessage.getKitchenOrderCompleted();
    }

    @Override
    public void cleanUp() {
        ClientCom con = createAndOpenConnection();
        Message   inMessage, outMessage;
        outMessage = new Message(Message.Type.CLEAN_UP);
        String outString = outMessage.toXMLString ();                         // converte resposta para XML
        con.writeObject (outString);  
        inMessage = waitForMessage(con, Message.Type.MESSAGE_RECEIVED);
        con.close();
    }
}
