package entities.states;

/**
 * Estados do Waiter.
 */
public enum WaiterState {
    APPRAISING_SITUATION("APPR"),
    PRESENTING_MENU("PRESENT"),
    TAKING_ORDER("TAKE"),
    PLACING_ORDER("PLACE"),
    WAITING_FOR_PORTION("WAIT_PORT"),
    PROCESSING_BILL("PROCESS"),
    RECEIVING_PAYMENT("RECEIVE");

    private final String id;

    WaiterState(String id) {
        this.id = id;
    }

    /**
     * Passagem dos estados do Waiter para String.
     *
     * @return string do estado do Waiter.
     */
    @Override
    public String toString() {
        return id;
    }
    
    public static WaiterState getWaiterStateById(String id) {
        for (WaiterState s : values()) {
            if (s.id.equals(id)) {
                return s;
            }
        }
        return null;
    }
}
