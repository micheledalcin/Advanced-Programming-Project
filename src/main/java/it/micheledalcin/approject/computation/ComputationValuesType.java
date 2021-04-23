package it.micheledalcin.approject.computation;

public enum ComputationValuesType {

    GRID("GRID"),
    LIST("LIST");

    private final String command;

    ComputationValuesType(String command) {
        this.command = command;
    }

    public String getCommand() { return this.command; }

    public static ComputationValuesType findComputationValuesType(String command) throws IllegalArgumentException {
        if (command.equals(GRID.getCommand())) return GRID;
        else if (command.equals(LIST.getCommand())) return LIST;
        throw new IllegalArgumentException("String \"" + command + "\" is not a ComputationValuesType");
    }

}
