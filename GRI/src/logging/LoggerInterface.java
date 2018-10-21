package logging;

import commonStructures.ClientProxy;
import commonStructures.Message;
import commonStructures.MessageException;
import main.MainGRI;
import sharedRegions.interfaces.SharedRegionInterface;

/**
 * Interface de Implementação do Logger-
 */
public class LoggerInterface implements SharedRegionInterface {
    /**
     * Referenciamento do logger.
     */
    private Logger loggerImpl;

    /**
     * Instanciação do Logger.
     *
     * @param loggerImpl
     */
    public LoggerInterface(Logger loggerImpl) {
        this.loggerImpl = loggerImpl;
    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;                     

        switch (inMessage.getType()) {
            case UPDATE_WAITER:
                if (inMessage.getWaiterState() == null) {
                    throw new MessageException("Não pode ser guardado um WaiterState nulo!", inMessage);
                }
                break;
            case UPDATE_CHEF:
                if (inMessage.getChefState() == null) {
                    throw new MessageException("Não pode ser guardado um ChefState nulo!", inMessage);
                }
                break;
            case UPDATE_STUDENT:
                if (inMessage.getStudentState() == null) {
                    throw new MessageException("Não pode ser guardado um StudentState nulo!", inMessage);
                }
                if (inMessage.getStudentId() < 0) {
                    throw new MessageException("Id do Student inválido!", inMessage);
                }
                break;

            case UPDATE_COURSE:
                if (inMessage.getNumberCourse() < 0) {
                    throw new MessageException("NumberCourse inválido!", inMessage);
                }
                break;
            case BAR_FINISHED:
                break;
            case KITCHEN_FINISHED:
                break;
            case TABLE_FINISHED:
                break;
            default:
                throw new MessageException("Tipo inválido!", inMessage);

        }

        /* seu processamento */

        /* validação da mensagem recebida */
        switch (inMessage.getType()) {
            case UPDATE_WAITER:
                loggerImpl.updateWaiterState(inMessage.getWaiterState());
                outMessage = new Message(Message.Type.LOGGER_MESSAGE_RECEIVED);
                break;
                
            case UPDATE_CHEF:
                loggerImpl.updateChefState(inMessage.getChefState());
                outMessage = new Message(Message.Type.LOGGER_MESSAGE_RECEIVED);
                break;
                
            case UPDATE_STUDENT:
                loggerImpl.updateStudentState(inMessage.getStudentId(), inMessage.getStudentState());
                outMessage = new Message(Message.Type.LOGGER_MESSAGE_RECEIVED);
                break;

            case UPDATE_COURSE:
                loggerImpl.updateCourse(inMessage.getNumberCourse());
                outMessage = new Message(Message.Type.LOGGER_MESSAGE_RECEIVED);
                break;
                
            case BAR_FINISHED:
                outMessage = new Message(Message.Type.LOGGER_MESSAGE_RECEIVED);
                MainGRI.barFinished = true;
                (((ClientProxy) Thread.currentThread()).getScon()).setTimeout(10);
                break;
                
            case KITCHEN_FINISHED:
                outMessage = new Message(Message.Type.LOGGER_MESSAGE_RECEIVED);
                MainGRI.kitchenFinished = true;
                (((ClientProxy) Thread.currentThread()).getScon()).setTimeout(10);
                break;
                
            case TABLE_FINISHED:
                outMessage = new Message(Message.Type.LOGGER_MESSAGE_RECEIVED);
                MainGRI.tableFinished = true;
                (((ClientProxy) Thread.currentThread()).getScon()).setTimeout(10);
                break;
                
            default:
                throw new MessageException("Tipo inválido!", inMessage);

        }
        return (outMessage);
    }
}
