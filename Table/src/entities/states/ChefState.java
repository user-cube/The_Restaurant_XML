package entities.states;

/**
 * Estados do Chef.
 */
public enum ChefState {
    WAITING_FOR_ORDER("WAIT_ORDER"),
    PREPARING_COURSE("PREP_COURSE"),
    DISHING_PORTIONS("DISH_PORT"),
    DELIVERING_PORTIONS("DELIVER"),
    CLOSING_SERVICE("CLOSE");

    private String id;

    ChefState(String id) {
        this.id = id;
    }

    /**
     * Passagem dos estados do chef para String.
     *
     * @return string do estado do Chef.
     */
    @Override
    public String toString() {
        return id;
    }

    public static ChefState getChefStateById(String id) {
        for (ChefState s : values()) {
            if (s.id.equals(id)) {
                return s;
            }
        }
        return null;
    }
}
