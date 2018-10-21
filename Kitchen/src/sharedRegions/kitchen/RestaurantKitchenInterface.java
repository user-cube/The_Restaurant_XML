package sharedRegions.kitchen;


import commonStructures.ClientProxy;
import commonStructures.Message;
import commonStructures.MessageException;
import main.MainKitchen;
import sharedRegions.interfaces.Kitchen;
import sharedRegions.interfaces.SharedRegionInterface;

/**
 * Implementação da Interface da Kitchen.
 */
public class RestaurantKitchenInterface implements SharedRegionInterface {
    /**
     * Referenciação da Kitchen.
     */
    private Kitchen kitchen;

    /**
     * Instanciação da Interface da Kitchen.
     *
     * @param kitchen
     */
    public RestaurantKitchenInterface(Kitchen kitchen) {
        this.kitchen = kitchen;
    }


    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;                       

        /* validação da mensagem recebida */

        switch (inMessage.getType()) {
            case WATCH_NEWS:
                break;
            case HAND_NOTE_TO_CHEF:
                break;
            case START_PREPARATION:
                break;
            case PROCEED_TO_PRESENTATION:
                break;
            case KITCHEN_ALERT_WAITER:
                break;
            case COLLECT_PORTION:
                break;
            case ALL_PORTIONS_DELIVERED:
                break;
            case KITCHEN_ALL_STUDENTS_SERVED:
                break;
            case HAVE_NEXT_PORTION_READY:
                break;
            case CONTINUE_PREPARATION:
                break;
            case ORDER_COMPLETED:
                break;
            case CLEAN_UP:                                                     
                break;
            default:
                throw new MessageException("Tipo inválido!", inMessage);
        }

        /* seu processamento */

        switch (inMessage.getType())

        {
            case WATCH_NEWS:
                kitchen.watchNews();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case HAND_NOTE_TO_CHEF:
                kitchen.handNoteToChef();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case START_PREPARATION:
                kitchen.startPreparation();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case PROCEED_TO_PRESENTATION:
                kitchen.proceedToPresentation();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case KITCHEN_ALERT_WAITER:
                kitchen.alertWaiter();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case COLLECT_PORTION:
                kitchen.collectPortion();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case ALL_PORTIONS_DELIVERED:
                boolean ret_all_portions_delivered = kitchen.allPortionsDelivered();
                outMessage = new Message(Message.Type.ALL_PORTIONS_DELIVERED_RESPONSE)
                        .setKitchenAllPortionsDelivered(ret_all_portions_delivered);
                break;
                
            case KITCHEN_ALL_STUDENTS_SERVED:
                kitchen.allStudentsServed();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case HAVE_NEXT_PORTION_READY:
                kitchen.haveNextPortionReady();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;

            case CONTINUE_PREPARATION:
                kitchen.continuePreparation();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case ORDER_COMPLETED:
                boolean ret_order_completed = kitchen.orderCompleted();
                outMessage = new Message(Message.Type.ORDER_COMPLETED_RESPONSE)
                        .setKitchenOrderCompleted(ret_order_completed);
                break;
                
            case CLEAN_UP:
                kitchen.cleanUp();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                MainKitchen.waitConnection = false;
                (((ClientProxy) (Thread.currentThread())).getScon()).setTimeout(10);
                break;
                
            default:
                throw new MessageException("Tipo inválido!", inMessage);
        }

        return (outMessage);
    }
}
