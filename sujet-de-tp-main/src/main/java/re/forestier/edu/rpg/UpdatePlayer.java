package re.forestier.edu.rpg;

import java.util.HashMap;
import java.util.Random;

public class UpdatePlayer {

    private static final Item[] OBJECT_LIST = {
        new Item("Lookout Ring", "Prevents surprise attacks", 0.5, 50),
        new Item("Scroll of Stupidity", "INT-2 when applied to an enemy", 0.3, 30),
        new Item("Draupnir", "Increases XP gained by 100%", 1.0, 100),
        new Item("Magic Charm", "Magic +10 for 5 rounds", 0.7, 70),
        new Item("Rune Staff of Curse", "May burn your enemies... Or yourself. Who knows?", 1.5, 150),
        new Item("Combat Edge", "Well, that's an edge", 2.0, 200),
        new Item("Holy Elixir", "Recover your HP", 0.5, 50)
    };


    /* private static final String[] OBJECT_LIST = {
        "Lookout Ring : Prevents surprise attacks",
        "Scroll of Stupidity : INT-2 when applied to an enemy",
        "Draupnir : Increases XP gained by 100%",
        "Magic Charm : Magic +10 for 5 rounds",
        "Rune Staff of Curse : May burn your ennemies... Or yourself. Who knows?",
        "Combat Edge : Well, that's an edge",
        "Holy Elixir : Recover your HP"
    };*/
    public static void majFinDeTour(player player) {
        if (player.getCurrentHealthPoints() <= 0) {
            player.setCurrentHealthPoints(0);
            return;
        }

        String avatarClass = player.getAvatarClass();
        int currentHP = player.getCurrentHealthPoints();
        int maxHP = player.getHealthPoints();

        switch (avatarClass) {
            case "ARCHER":
                currentHP += 1;
                if (player.getInventory().stream().anyMatch(item -> item.getName().equals("Magic Bow"))) {
                    currentHP += (currentHP / 8 - 1);
                }
                break;

            case "ADVENTURER":
                int level = player.retrieveLevel();
                if (level < 3) {
                    currentHP += 2 - 1;
                } else {
                    currentHP += 3;
                }
                break;

            case "DWARF":
                currentHP += 1;
                if (player.getInventory().stream().anyMatch(item -> item.getName().equals("Holy Elixir"))) {
                    currentHP += 1;
                }
                break;

            default:
                break;
        }

        player.setCurrentHealthPoints(Math.min(currentHP, maxHP));
    }

    public static boolean addXp(player player, int xp) {
        int currentLevel = player.retrieveLevel();
        player.setXp(player.getXp() + xp);
        int newLevel = player.retrieveLevel();

        if (newLevel > currentLevel) {
            levelUp(player, newLevel);
            return true;
        }
        return false;
    }

    private static void levelUp(player player, int newLevel) {
        Random random = new Random();
        Item randomObject = OBJECT_LIST[random.nextInt(OBJECT_LIST.length)];
        player.addItem(randomObject); // Ajoute un objet aléatoire à l'inventaire

        HashMap<String, Integer> newAbilities = abilitiesPerTypeAndLevel()
                .get(player.getAvatarClass())
                .get(newLevel);

        if (newAbilities != null) {
            player.getAbilities().clear(); // Supprime les anciennes capacités
            newAbilities.forEach((ability, value) -> player.getAbilities().put(ability, value));
        }
    }

    /* private static void levelUp(player player, int newLevel) {
        Random random = new Random();
        String randomObject = OBJECT_LIST[random.nextInt(OBJECT_LIST.length)];
        player.getInventory().add(randomObject);

        HashMap<String, Integer> newAbilities = abilitiesPerTypeAndLevel()
                .get(player.getAvatarClass())
                .get(newLevel);

        if (newAbilities != null) {
            newAbilities.forEach((ability, value) -> player.getAbilities().put(ability, value));
        }
    }*/
    public static HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel() {
        HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilities = new HashMap<>();

        HashMap<Integer, HashMap<String, Integer>> adventurerMap = new HashMap<>();
        adventurerMap.put(1, createAbilityMap("INT", 1, "DEF", 1, "ATK", 3, "CHA", 2));
        adventurerMap.put(2, createAbilityMap("INT", 2, "CHA", 3));
        adventurerMap.put(3, createAbilityMap("ATK", 5, "ALC", 1));
        adventurerMap.put(4, createAbilityMap("DEF", 3));
        adventurerMap.put(5, createAbilityMap("VIS", 1, "DEF", 4));
        abilities.put("ADVENTURER", adventurerMap);

        HashMap<Integer, HashMap<String, Integer>> archerMap = new HashMap<>();
        archerMap.put(1, createAbilityMap("INT", 1, "ATK", 3, "CHA", 1, "VIS", 3));
        archerMap.put(2, createAbilityMap("DEF", 1, "CHA", 2));
        archerMap.put(3, createAbilityMap("ATK", 3));
        archerMap.put(4, createAbilityMap("DEF", 2));
        archerMap.put(5, createAbilityMap("ATK", 4));
        abilities.put("ARCHER", archerMap);

        HashMap<Integer, HashMap<String, Integer>> dwarfMap = new HashMap<>();
        dwarfMap.put(1, createAbilityMap("ALC", 4, "INT", 1, "ATK", 3));
        dwarfMap.put(2, createAbilityMap("DEF", 1, "ALC", 5));
        dwarfMap.put(3, createAbilityMap("ATK", 4));
        dwarfMap.put(4, createAbilityMap("DEF", 2));
        dwarfMap.put(5, createAbilityMap("CHA", 1));
        abilities.put("DWARF", dwarfMap);

        HashMap<Integer, HashMap<String, Integer>> goblinMap = new HashMap<>();
        goblinMap.put(1, createAbilityMap("INT", 2, "ATK", 2, "ALC", 1));
        goblinMap.put(2, createAbilityMap("ATK", 3, "ALC", 4));
        goblinMap.put(3, createAbilityMap("VIS", 1));
        goblinMap.put(4, createAbilityMap("DEF", 1));
        goblinMap.put(5, createAbilityMap("DEF", 2, "ATK", 4));
        abilities.put("GOBLIN", goblinMap);

        return abilities;
    }

    private static HashMap<String, Integer> createAbilityMap(Object... abilities) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < abilities.length; i += 2) {
            map.put((String) abilities[i], (Integer) abilities[i + 1]);
        }
        return map;
    }

    public static Item[] getOBJECT_LIST() {
        return OBJECT_LIST;
    }
}
