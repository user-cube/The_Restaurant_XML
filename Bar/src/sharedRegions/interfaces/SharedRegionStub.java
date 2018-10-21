package sharedRegions.interfaces;

import commonStructures.ClientCom;
import commonStructures.Message;

/**
 * Classe abstrata de definição dos Stubs.
 */
public abstract class SharedRegionStub {
    /**
     * Nome da máquina em que o servidor se encontra alojado.
     *
     * @serialField serverHostName
     */
    private String serverHostName;

    /**
     * Porta em que o servidor se encontra à escuta.
     *
     * @serialField serverPortNumb
     */
    private int serverPortNumb;

    /**
     * Instanciação do Stub
     *
     * @param hostName nome do computador em que o servidor se encontra alojado.
     * @param port     porta em que o servidor se encontra à escuta.
     */
    public SharedRegionStub(String hostName, int port) {
        serverHostName = hostName;
        serverPortNumb = port;
    }

    protected Message waitForMessage(ClientCom socket, Message.Type message) {
    	String inString = (String) socket.readObject();
        Message inMessage = new Message (inString);
        if ((inMessage.getType() != message)) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        return inMessage;
    }

    protected ClientCom createAndOpenConnection() {
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        while (!con.open()) {
            try {
                Thread.sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        return con;
    }

}
