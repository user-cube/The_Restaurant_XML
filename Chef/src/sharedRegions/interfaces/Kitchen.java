package sharedRegions.interfaces;



/**
 * Interface que define as operações ocorridas na Kitchen.
 */
public interface Kitchen {
    /**
     * Chef aguarda até que tenha tarefas para executar.
     */
    void watchNews();

    /**
     * O Waiter entrega os pedidos ao Chef.
     */
    void handNoteToChef();

    /**
     * Chef inicia a preparação do pratos.
     */
    void startPreparation();

    /**
     * Chef inicia o empretamento.
     */
    void proceedToPresentation();

    /**
     * Quando o chefe tem as porções prontas alerta o Waiter.
     */
    void alertWaiter();

    /**
     * O Waiter recolhe as porções prontas.
     */
    void collectPortion();

    /**
     * Verificação se todas as porções foram entregues.
     * @return True - Se todas as porções foram entregues.
     */
    boolean allPortionsDelivered();

    /**
     * Verificação se todos os estudantes já foram servidos.
     */
    void allStudentsServed();

    /**
     * Chef prepara a próxima porção-.
     */
    void haveNextPortionReady();

    /**
     * O Chef continua a preparação.
     */
    void continuePreparation();

    /**
     * Verificação se o pedido já foi completado.
     *
     * @return True - Se todos os pratos já foram terminados.
     */
    boolean orderCompleted();

    /**
     * O Chef termina o serviço e limpa a cozinha.
     */
    void cleanUp();
}
