package entities.states;

/**
 * Estados do estudante.
 */
public enum StudentState {
    GOING_TO_RESTAURANT("GO_REST"),
    TAKING_SEAT_AT_TABLE("SEAT"),
    SELECTING_COURSES("SELECT"),
    ORGANIZING_ORDER("ORGANIZE"),
    CHATTING_WITH_COMPANIONS("CHAT"),
    ENJOYING_MEAL("ENJOY"),
    PAYING_MEAL("PAY_MEAL"),
    GOING_HOME("HOME");

    private final String id;

    StudentState(String id) {
        this.id = id;
    }

    /**
     * Passagem dos estados do Student. para String.
     *
     * @return string do estado do Student.
     */
    @Override
    public String toString() {
        return id;
    }
    
    public static StudentState getStudentStateById(String id) {
        for (StudentState s : values()) {
            if (s.id.equals(id)) {
                return s;
            }
        }
        return null;
    }
}
