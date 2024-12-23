package re.forestier.edu.rpg;

public class Item {

    private final String name;
    private final String description;
    private final double weight;
    private final int value;

    public Item(String name, String description, double weight, int value) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }
}
