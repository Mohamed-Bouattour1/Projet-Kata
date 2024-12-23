package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;

public class player {

    private final String playerName;
    private final String avatarName;
    private final String avatarClass;

    private int money;
    private final ArrayList<Item> inventory;
    private int level;
    private int healthPoints;
    private int currentHealthPoints;
    private int xp;

    private final HashMap<String, Integer> abilities;

    private final double maxWeight = 50.0; // Poids maximal
    private double currentWeight = 0.0; // Poids actuel

    public boolean addItem(Item item) {
        if (currentWeight + item.getWeight() > maxWeight) {
            return false; // Échec si le poids dépasse la limite
        }
        inventory.add(item);
        currentWeight += item.getWeight();
        return true;
    }

    public void sellItem(Item item) {
        if (inventory.remove(item)) {
            currentWeight -= item.getWeight();
            addMoney(item.getValue());
        }
    }

    public boolean addItemByName(String itemName) {
        for (Item item : UpdatePlayer.getOBJECT_LIST()) {
            if (item.getName().equals(itemName)) {
                return addItem(item);
            }
        }
        return false; // Objet non trouvé
    }

    public player(String playerName, String avatarName, String avatarClass, int money, ArrayList<Item> inventory) {
        if (!isValidClass(avatarClass)) {
            throw new IllegalArgumentException("Invalid avatar class: " + avatarClass);
        }
        this.playerName = playerName;
        this.avatarName = avatarName;
        this.avatarClass = avatarClass;
        this.money = money;
        this.inventory = new ArrayList<>(inventory);
        this.abilities = new HashMap<>(UpdatePlayer.abilitiesPerTypeAndLevel()
                .get(avatarClass).getOrDefault(1, new HashMap<>()));
    }

    public String getAvatarClass() {
        return avatarClass;
    }

    public void removeMoney(int amount) {
        if (money < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
        money -= amount;
    }

    public void addMoney(int amount) {
        money += Math.max(0, amount);
    }

    public int getXp() {
        return xp;
    }

    private boolean isValidClass(String avatarClass) {
        return UpdatePlayer.abilitiesPerTypeAndLevel().containsKey(avatarClass);
    }

    public int retrieveLevel() {
        if (xp < 10) {
            return 1;
        } else if (xp < 27) {
            return 2;
        } else if (xp < 57) {
            return 3;
        } else if (xp < 111) {
            return 4;
        } else {
            return 5;
        }
    }

    public ArrayList<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public void setCurrentHealthPoints(int healthPoints) {
        this.currentHealthPoints = Math.min(healthPoints, this.healthPoints);
    }

    public HashMap<String, Integer> getAbilities() {
        return abilities;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public int getMoney() {
        return money;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }
}
