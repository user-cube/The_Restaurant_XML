package sharedRegions.bar;

import commonStructures.ClientProxy;
import commonStructures.Message;
import commonStructures.MessageException;
import main.MainBar;
import sharedRegions.interfaces.Bar;
import sharedRegions.interfaces.SharedRegionInterface;

/**
 * Interface da Implementação do Bar.
 */
public class RestaurantBarInterface implements SharedRegionInterface {
    /**
     * Referenciação ao bar.
     */
    private Bar bar;

    /**
     * Iniciaçização da interface do Bar.
     *
     * @param bar an instance of the Bar
     */
    public RestaurantBarInterface(Bar bar) {
        this.bar = bar;
    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;                         
        
        switch (inMessage.getType()) {
            case LOOK_AROUND:
                break;
            case RETURN_TO_BAR:
                break;
            case BAR_ENTER:
                break;
            case CALL_WAITER:
                break;
            case BAR_ALERT_WAITER:
                break;
            case PREPARE_BILL:
                break;
            case BAR_SHOULD_ARRIVE_EARLIER:
                break;
            case SAY_GOODBYE:
                break;
            case BAR_EXIT:
                if (inMessage.getStudentId() < 0) {
                    throw new MessageException("Id do estudante inválido!", inMessage);
                }
                break;
            case WAITER_FINISHED:
                break;
            default:
                throw new MessageException("Tipo inválido!", inMessage);

        }

        /* seu processamento */

        switch (inMessage.getType())

        {
            case LOOK_AROUND:
                char task = bar.lookAround();
                outMessage = new Message(Message.Type.LOOK_AROUND_RESPONSE).setTask(task);
                break;

            case RETURN_TO_BAR:
                bar.returnToBar();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case BAR_ENTER:
                int arrivalOrder = bar.enter();
                outMessage = new Message(Message.Type.BAR_ENTER_RESPONSE).setArrivalOrder(arrivalOrder);
                break;

            case CALL_WAITER:
                bar.callWaiter();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case BAR_ALERT_WAITER:
                bar.alertWaiter();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case PREPARE_BILL:
                bar.prepareBill();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case BAR_SHOULD_ARRIVE_EARLIER:
                bar.shouldArriveEarlier();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case SAY_GOODBYE:
                bar.sayGoodBye();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case BAR_EXIT:
                bar.exit(inMessage.getStudentId());
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case WAITER_FINISHED:
            	outMessage = new Message(Message.Type.LOGGER_MESSAGE_RECEIVED);
                MainBar.waiterFinished = true;
                (((ClientProxy) (Thread.currentThread())).getScon()).setTimeout(10);
                break;
                
            default:
                throw new MessageException("Tipo inválido!", inMessage);

        }
        return (outMessage);
    }
}
