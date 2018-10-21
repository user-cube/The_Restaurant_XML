package sharedRegions.interfaces;

/**
 * 	Interface de definição das ações ocorridas no Bar.
 */
public interface Bar {
    /**
     * Bloqueio do Waiter se não tiver tarefas a realizar.
     * 
     * @return caractér correspondente à ação a realizar.
     */
    char lookAround();

    /**
     * Alteração do estado do Waiter para APPRAISING SITUATION
     */
    void returnToBar();

    /**
     * Entrada dos estudantes no Restaurante.
     *
     * @return ordem de chegada dos estudantes.
     */
    int enter();

    /**
     * O Waiter é chamado pelo primeiro estudante a chegar ao Restaurante.
     */
    void callWaiter();

    /**
     * O Waiter é chamado pleo Chef quando as porções estão prontas.
     */
    void alertWaiter();

    /**
     * O Waiter prepara a conta (PROCESSING_BILL)
     */
    void prepareBill();

    /**
     * O último estudante a chegar paga a conta.
     */
    void shouldArriveEarlier();

    /**
     * O Waiter despede-se dos estudantes.
     */
    void sayGoodBye();

    /**
     * Os estudantes após pagarem abandonam o restaurante.
     */
    void exit(long id);

    /**
     * Chamado quando o ciclo de vida do Waiter termina.
     */
    void waiterFinished();
}
