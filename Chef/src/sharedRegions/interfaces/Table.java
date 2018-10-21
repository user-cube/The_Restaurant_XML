package sharedRegions.interfaces;


/**
 * Interface que define as operações a realizar na table.
 */
public interface Table {
    /**
     * Os estudantes entram no bar.
     *
     * @return ordem de chegada dos estudantes.
     */
    int enter(long studentId);

    /**
     * O Waiter recebe os estudantes.
     */
    void saluteTheClient();

    /**
     * Os estudantes leem o menu.
     */
    void readMenu(long studentId);

    /**
     * Verificação se todos terminaram de escolher.
     * 
     * @return True - Se todos terminaram de ler o menu.
     */
    boolean everybodyChosen();

    /**
     * Os estudantes organizam o seu pedido.
     */
    void prepareOrder(long studentId);

    /**
     * Os estudantes descrevem o seu pedido.
     */
    void describeOrder();

    /**
     * O Waiter recolhe o pedido.
     */
    void getThePad();

    /**
     * Após terminarem de escolher os estundantes começam a falar entre si.
     */
    void joinTalk(long studentId);

    /**
     * O estudante informa os colegas sobre o que vai comer.
     */
    void informCompanion(long studentId);

    /**
     * Porções entregues.
     */
    void deliverPortion();

    /**
     * Verificação se todos os estudantes já receberam a sua porção.
     * 
     * @return True - Se todos os estudantes receberam a sua porção.
     */
    boolean allStudentsServed();

    /**
     * Os estudantes começam a comer.
     */
    void startEating(long studentId);

    /**
     * Os estudantes terminam de comer.
     * 
     * @return True - Se for o último prato.
     */
    boolean endEating(long studentId);

    /**
     * Verificação se todos já terminaram.
     * 
     * @return True - Se todos os estudantes terminaram de comer.
     */
    boolean everybodyFinished();

    /**
     * Os estudantes sinalizam o Waiter quando terminam de comer.
     */
    void signalWaiter();

    /**
     * O último estudante a chegar pede a conta.
     */
    void shouldArriveEarlier(long studentId);


    /**
     * O estudante apresenta a conta ao último estudante a chegar.
     */
    void presentBill();


    /**
     * O último estudante a chegar paga a conta.
     */
    void honourBill();

    /**
     * Após pagarem os estudantes saiem do Restaurante.
     */
    void exit(long studentId);
}
