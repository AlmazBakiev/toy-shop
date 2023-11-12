public class NameOrProbabilityException extends Exception {

    private final String name;
    private final int probability;

    public NameOrProbabilityException(String message, String name, int probability) {
        super(message);
        this.name = name;
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public int getProbability() {
        return probability;
    }
}
