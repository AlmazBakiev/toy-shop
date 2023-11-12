import java.util.ArrayList;
import java.util.regex.Pattern;

public class Toy {

    private int id;
    private String name;
    private int probability;
    private static int amount = 0;
    private static int counter = 0;
    private static ArrayList<Toy> allToys = new ArrayList<>();

    private Toy(String name, int probability) {
        amount++;
        counter++;
        this.id = counter;
        this.name = name;
        this.probability = probability;
        allToys.add(this);
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public static void createToy(String name, int probability) throws NameOrProbabilityException {
        if (!Pattern.matches("[A-zА-я]{3,}", name) || !(probability > 0 && probability <= 100)) {
            throw new NameOrProbabilityException("Некорректно введены данные!", name, probability);
        }
        new Toy(name, probability);
    }

    public static int getCounter() {
        return counter;
    }

    public static ArrayList<Toy> getAllToys() {
        return allToys;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getProbability() {
        return probability;
    }

    public static int getAmount() {
        return amount;
    }

    public static Toy getToy(int numberId) {
        Toy result = null;
        for (Toy toy : allToys) {
            if (toy.getId() == numberId) {
                result = toy;
            }
        }
        return result;
    }

    public static void removeToy(Toy toy) {
        allToys.remove(toy);
    }

    public static void decrementAmount() {
        amount--;
    }

    public static void getInstant() {
        new Toy("Doll", 77);
        new Toy("Car", 66);
        new Toy("Ball", 55);
    }
}
