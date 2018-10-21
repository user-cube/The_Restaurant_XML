package sharedRegions.table;

import commonStructures.ClientProxy;
import commonStructures.Message;
import commonStructures.MessageException;
import main.MainTable;
import main.Constants;
import sharedRegions.interfaces.SharedRegionInterface;
import sharedRegions.interfaces.Table;

/**
 * Interface da Table.
 */
public class RestaurantTableInterface implements SharedRegionInterface {
    /**
     * Referencia à table.
     */
    private Table table;
    /**
     * controlo do termino da table.
     */
    private static int   studentsLeft = 0;

    /**
     * Instanciação da Interface da Table.
     *
     * @param table an instance of the Table
     */
    public RestaurantTableInterface(Table table) {
        this.table = table;
    }

    public Message processAndReply(Message inMessage) throws MessageException {
        // mensagem de resposta
        Message outMessage = null;

        /* validação da mensagem recebida */
        switch (inMessage.getType()) {
            case TABLE_ENTER:
                if (inMessage.getStudentId() < 0) {
                    throw new MessageException("Id do estudante inválido!", inMessage);
                }
                break;
            case SALUTE_CLIENT:
                break;
            case READ_MENU:
                if (inMessage.getStudentId() < 0) {
                    throw new MessageException("Id do estudante inválido!", inMessage);
                }
                break;
            case EVERYBODY_CHOSEN:
                break;
            case PREPARE_ORDER:
                if (inMessage.getStudentId() < 0) {
                    throw new MessageException("Id do estudante inválido!", inMessage);
                }
                break;
            case DESCRIBE_ORDER:
                break;
            case GET_THE_PAD:
                break;
            case JOIN_TALK:
                if (inMessage.getStudentId() < 0) {
                    throw new MessageException("Id do estudante inválido!", inMessage);
                }
                break;
            case INFORM_COMPANION:
                if (inMessage.getStudentId() < 0) {
                    throw new MessageException("Id do estudante inválido!", inMessage);
                }
                break;
            case DELIVER_PORTION:
                break;
            case TABLE_ALL_STUDENTS_SERVED:
                break;
            case START_EATING:
                if (inMessage.getStudentId() < 0) {
                    throw new MessageException("Id do estudante inválido!", inMessage);
                }
                break;
            case END_EATING:
                if (inMessage.getStudentId() < 0) {
                    throw new MessageException("Id do estudante inválido!", inMessage);
                }
                break;
            case EVERYBODY_FINISHED:
                break;
            case SIGNAL_WAITER:
                break;
            case TABLE_SHOULD_ARRIVE_EARLIER:
                if (inMessage.getStudentId() < 0) {
                    throw new MessageException("Id do estudante inválido!", inMessage);
                }
                break;
            case PRESENT_BILL:
                break;
            case HONOUR_BILL:
                break;
            case TABLE_EXIT:
                if (inMessage.getStudentId() < 0) {
                    throw new MessageException("Id do estudante inválido!", inMessage);
                }
                break;
            default:
                throw new MessageException("Tipo inválido!", inMessage);

        }

        /* seu processamento */

        switch (inMessage.getType()) {
            case TABLE_ENTER:
                int arrivalOrder = table.enter(inMessage.getStudentId());
                outMessage = new Message(Message.Type.TABLE_ENTER_RESPONSE)
                        .setArrivalOrder(arrivalOrder);
                break;
                
            case SALUTE_CLIENT:
                table.saluteTheClient();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case READ_MENU:
                table.readMenu(inMessage.getStudentId());
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case EVERYBODY_CHOSEN:
                boolean ret_everybody_chosen = table.everybodyChosen();
                outMessage = new Message(Message.Type.EVERYBODY_CHOSEN_RESPONSE)
                        .setEverybodyChosen(ret_everybody_chosen);
                break;
                
            case PREPARE_ORDER:
                table.prepareOrder(inMessage.getStudentId());
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case DESCRIBE_ORDER:
                table.describeOrder();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case GET_THE_PAD:
                table.getThePad();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case JOIN_TALK:
                table.joinTalk(inMessage.getStudentId());
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case INFORM_COMPANION:
                table.informCompanion(inMessage.getStudentId());
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case DELIVER_PORTION:
                table.deliverPortion();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case TABLE_ALL_STUDENTS_SERVED:
                boolean ret_all_students_served = table.allStudentsServed();
                outMessage = new Message(Message.Type.TABLE_ALL_STUDENTS_SERVED_RESPONSE)
                        .setTableAllStudentsServed(ret_all_students_served);
                break;
                
            case START_EATING:
                table.startEating(inMessage.getStudentId());
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case END_EATING:
                boolean ret_end_eating = table.endEating(inMessage.getStudentId());
                outMessage = new Message(Message.Type.END_EATING_RESPONSE)
                        .setEndEating(ret_end_eating);
                break;
                
            case EVERYBODY_FINISHED:
                boolean ret_everybody_finished = table.everybodyFinished();
                outMessage = new Message(Message.Type.EVERYBODY_FINISHED_RESPONSE)
                        .setEverybodyFinished(ret_everybody_finished);
                break;
                
            case SIGNAL_WAITER:
                table.signalWaiter();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case TABLE_SHOULD_ARRIVE_EARLIER:
                table.shouldArriveEarlier(inMessage.getStudentId());
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case PRESENT_BILL:
                table.presentBill();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
                
            case HONOUR_BILL:
                table.honourBill();
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;

            case TABLE_EXIT:
                table.exit(inMessage.getStudentId());
                studentsLeft++;
                if (studentsLeft >= Constants.STUDENTS_NUMBER) {
                    MainTable.waitConnection = false;
                    (((ClientProxy) (Thread.currentThread())).getScon()).setTimeout(10);
                }
                outMessage = new Message(Message.Type.MESSAGE_RECEIVED);
                break;
        }

        return (outMessage);
    }
}