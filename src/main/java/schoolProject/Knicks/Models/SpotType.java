package schoolProject.Knicks.Models;

public enum SpotType {
    STANDARD("Standard"),
    COMPACT("Compact"),
    EV("EV"),
    ACCESSIBLE("Accessible");

    private final String label;

    SpotType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
