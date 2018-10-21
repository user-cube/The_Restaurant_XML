package commonStructures;

import sharedRegions.interfaces.SharedRegionInterface;


/**
 * Este tipo de dados define o thread agente prestador de serviço para uma solução do 
 * Problema do Restaurante que implementa o modelo cliente-servidor de tipo 2 (replicação do servidor). 
 * A comunicação baseia-se em passagem de mensagens sobre sockets
 * usando o protocolo TCP.
 */
public class ClientProxy extends Thread {

    /**
     * Contador de threads lançados
     *
     * @serialField nProxy
     */
    private static int nProxy = 0;

    /**
     * Canal de comunicação
     *
     * @serialField sconi
     */
    private ServerCom sconi;

    /**
     * Interface de uma região partilhada
     *
     * @serialField regionInterface
     */
    private SharedRegionInterface regionInterface;

    /**
     * Instanciação de um Agente Prestador de Serviço associado a uma determinada interface de uma região partilhada
     *
     * @param sconi           canal de comunicação
     * @param regionInterface interface de uma região partilhada
     */
    public ClientProxy(ServerCom sconi, SharedRegionInterface regionInterface) {
        super("Proxy_" + getProxyId());
        this.sconi = sconi;
        this.regionInterface = regionInterface;
    }

    /**
     * Ciclo de vida do thread agente prestador de serviço.
     */
    @Override
    public void run() {
        Message inMessage = null,                                      // mensagem de entrada
                outMessage = null;                      // mensagem de saída
        String inString = (String) sconi.readObject();
        inMessage = new Message (inString);                     // ler pedido do cliente
        try {
            outMessage = regionInterface.processAndReply(inMessage);         // processá-lo
        } catch (MessageException e) {
            System.out.println("Thread " + getName() + ": " + e.getMessage() + "!");
            System.out.println(e.getMessageVal().toString());
            System.exit(1);
        }
        String outString = outMessage.toXMLString();
        sconi.writeObject(outString);                                // enviar resposta ao cliente
        sconi.close();                                                // fechar canal de comunicação
    }

    /**
     * Geração do identificador da instanciação.
     *
     * @return identificador da instanciação
     */
    private static int getProxyId() {
        Class<?> cl = null;
        int proxyId;

        try {
            cl = Class.forName("commonStructures.ClientProxy");
        } catch (ClassNotFoundException e) {
            System.out.println("O tipo de dados commonStructures.ClientProxy não foi encontrado!");
            e.printStackTrace();
            System.exit(1);
        }

        synchronized (cl) {
            proxyId = nProxy;
            nProxy += 1;
        }

        return proxyId;
    }

    /**
     * Obtenção do canal de comunicação.
     *
     * @return canal de comunicação
     */
    public ServerCom getScon() {
        return sconi;
    }
}

