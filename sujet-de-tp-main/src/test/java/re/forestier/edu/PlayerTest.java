package re.forestier.edu;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.Item;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

class PlayerTest {

    private player testPlayer;
    private ArrayList<Item> inventory;

    @BeforeEach
    void setUp() {
        inventory = new ArrayList<>();
        inventory.add(new Item("Sword", "A sharp blade", 2.0, 50)); // Ajout d'un objet de type Item
        testPlayer = new player("TestPlayer", "TestAvatar", "ARCHER", 100, inventory);
    }

    @Test
    void constructor_WithValidClass_CreatesPlayer() {
        assertNotNull(testPlayer);
        assertEquals("TestPlayer", testPlayer.getPlayerName());
        assertEquals("TestAvatar", testPlayer.getAvatarName());
        assertEquals("ARCHER", testPlayer.getAvatarClass());
        assertEquals(100, testPlayer.getMoney());
        assertTrue(testPlayer.getInventory().contains(inventory.get(0)));
    }

    @Test
    void constructor_WithInvalidClass_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new player("TestPlayer", "TestAvatar", "INVALID", 100, inventory);
        });
    }

    @Test
    void constructor_WithAllValidClasses_CreatesPlayer() {
        String[] validClasses = {"ARCHER", "ADVENTURER", "DWARF", "GOBLIN"};
        for (String validClass : validClasses) {
            player p = new player("Test", "Avatar", validClass, 100, inventory);
            assertEquals(validClass, p.getAvatarClass());
        }
    }

    @Test
    void removeMoney_WithValidAmount_ReducesMoney() {
        testPlayer.removeMoney(50);
        assertEquals(50, testPlayer.getMoney());
    }

    @Test
    void removeMoney_WithExactAmount_SetsMoneyToZero() {
        testPlayer.removeMoney(100);
        assertEquals(0, testPlayer.getMoney());
    }

    @Test
    void removeMoney_WithExcessiveAmount_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            testPlayer.removeMoney(150);
        });
    }

    @Test
    void addMoney_WithPositiveAmount_IncreasesMoney() {
        testPlayer.addMoney(50);
        assertEquals(150, testPlayer.getMoney());
    }

    @Test
    void addMoney_WithZero_DoesNotChangeMoney() {
        testPlayer.addMoney(0);
        assertEquals(100, testPlayer.getMoney());
    }

    @Test
    void retrieveLevel_WithXPLessThanLevel2_ReturnsLevel1() {
        UpdatePlayer.addXp(testPlayer, 5 - testPlayer.getXp());
        assertEquals(1, testPlayer.retrieveLevel());
    }

    @Test
    void retrieveLevel_WithXPForLevel2_ReturnsLevel2() {
        UpdatePlayer.addXp(testPlayer, 10 - testPlayer.getXp());
        assertEquals(2, testPlayer.retrieveLevel());
    }

    @Test
    void retrieveLevel_WithXPForLevel3_ReturnsLevel3() {
        UpdatePlayer.addXp(testPlayer, 27 - testPlayer.getXp());
        assertEquals(3, testPlayer.retrieveLevel());
    }

    @Test
    void retrieveLevel_WithXPForLevel4_ReturnsLevel4() {
        UpdatePlayer.addXp(testPlayer, 57 - testPlayer.getXp());
        assertEquals(4, testPlayer.retrieveLevel());
    }

    @Test
    void retrieveLevel_WithXPForLevel5_ReturnsLevel5() {
        UpdatePlayer.addXp(testPlayer, 111 - testPlayer.getXp());
        assertEquals(5, testPlayer.retrieveLevel());
    }

    @Test
    void getXp_ReturnsCorrectValue() {
        UpdatePlayer.addXp(testPlayer, 100);
        assertEquals(100, testPlayer.getXp());
    }

    @Test
    void testAddItemByName_ItemNotFound() {
        player player = new player("John", "Avatar1", "ARCHER", 100, new ArrayList<>());
        boolean result = player.addItemByName("NonExistentItem");

        assertFalse(result, "La méthode doit retourner false si l'objet n'est pas trouvé.");
    }

    @Test
    void testAddItem_ExceedingWeightLimit() {
        player player = new player("John", "Avatar1", "ARCHER", 100, new ArrayList<>());
        Item heavyItem = new Item("Heavy Rock", "Extremely heavy rock", 100.0, 10);

        boolean result = player.addItem(heavyItem);

        assertFalse(result, "La méthode doit retourner false si l'objet dépasse la limite de poids.");
    }

    @Test
    void testAddItem_WithinWeightLimit() {
        player player = new player("John", "Avatar1", "ARCHER", 100, new ArrayList<>());
        Item lightItem = new Item("Feather", "Very light feather", 0.5, 10);

        boolean result = player.addItem(lightItem);

        assertTrue(result, "L'objet devrait être ajouté car il respecte la limite de poids.");
    }

    @Test
    void testSellItem_Success() {
        player player = new player("John", "Avatar1", "ARCHER", 100, new ArrayList<>());
        Item sword = new Item("Sword", "A sharp blade", 2.0, 50);

        player.addItem(sword); // Ajouter l'objet à l'inventaire
        player.sellItem(sword); // Vendre l'objet

        assertFalse(player.getInventory().contains(sword), "L'objet vendu ne devrait plus être dans l'inventaire.");
        assertEquals(150, player.getMoney(), "Le joueur devrait avoir gagné 50 pièces d'or.");
        assertEquals(0.0, player.getCurrentWeight(), "Le poids actuel devrait être mis à jour après la vente.");
    }

    @Test
    void testSellItem_ItemNotInInventory() {
        player player = new player("John", "Avatar1", "ARCHER", 100, new ArrayList<>());
        Item sword = new Item("Sword", "A sharp blade", 2.0, 50);

        player.sellItem(sword); // Essayer de vendre un objet qui n'est pas dans l'inventaire

        assertFalse(player.getInventory().contains(sword), "L'inventaire ne devrait pas contenir l'objet.");
        assertEquals(100, player.getMoney(), "L'argent du joueur ne devrait pas changer.");
        assertEquals(0.0, player.getCurrentWeight(), "Le poids actuel ne devrait pas changer.");
    }

}
