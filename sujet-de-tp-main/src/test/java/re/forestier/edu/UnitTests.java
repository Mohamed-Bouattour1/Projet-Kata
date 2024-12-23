package re.forestier.edu;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.Item;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

public class UnitTests {

    @Test
    @DisplayName("Test KO: Joueur avec 0 points de vie")
    void testPlayerKO() {
        player player = new player("John", "Avatar1", "ARCHER", 100, new ArrayList<>());
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(0);

        UpdatePlayer.majFinDeTour(player);

        assertEquals(0, player.getCurrentHealthPoints(), "Le joueur doit rester à 0 HP (KO).");
    }

    @Test
    @DisplayName("Test récupération points de vie pour un Archer")
    void testArcherHealthRecovery() {
        player player = new player("John", "Avatar1", "ARCHER", 100, new ArrayList<>());
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(40); // Moins de 50% des HP

        UpdatePlayer.majFinDeTour(player);

        assertEquals(41, player.getCurrentHealthPoints(), "Un Archer doit récupérer 1 HP par tour.");
    }

    @Test
    @DisplayName("Test récupération points de vie pour un Archer avec 'Magic Bow'")
    void testArcherWithMagicBow() {
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(new Item("Magic Bow", "A bow imbued with magical power", 1.2, 100)); // Création d'un Item pour l'inventaire
        player player = new player("John", "Avatar1", "ARCHER", 100, inventory);
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(40); // Moins de 50% des HP

        UpdatePlayer.majFinDeTour(player);

        int expectedRecovery = 41 + (40 / 8 - 1);
        assertEquals(expectedRecovery, player.getCurrentHealthPoints(), "L'Archer avec 'Magic Bow' doit récupérer des HP supplémentaires.");
    }

    @Test
    @DisplayName("Test récupération points de vie pour un Aventurier")
    void testAdventurerHealthRecovery() {
        player player = new player("John", "Avatar1", "ADVENTURER", 100, new ArrayList<>());
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(40); // Moins de 50% des HP

        // Supposer que le joueur est au niveau 1 ou 2
        UpdatePlayer.addXp(player, 0); // Initialise l'XP pour que le joueur reste au niveau 1

        // Vérifier l'état initial
        assertEquals(40, player.getCurrentHealthPoints(), "Les points de vie actuels devraient être 40 avant majFinDeTour.");

        UpdatePlayer.majFinDeTour(player);

        // L'aventurier devrait regagner 2 points mais perdre 1 point en raison de son niveau
        assertEquals(41, player.getCurrentHealthPoints(), "Le joueur ADVENTURER devrait regagner 2 points de vie puis perdre 1 point.");
    }

    @Test
    @DisplayName("Test récupération points de vie pour un Aventurier de niveau inférieur à 3")
    void testAdventurerLowLevel() {
        player player = new player("John", "Avatar1", "ADVENTURER", 100, new ArrayList<>());
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(40); // Moins de 50% des HP
        UpdatePlayer.addXp(player, 0); // XP reste à 0 pour être niveau 1

        UpdatePlayer.majFinDeTour(player);

        assertEquals(41, player.getCurrentHealthPoints(), "Un Aventurier de niveau inférieur à 3 récupère 2 HP mais en perd 1.");
    }

    @Test
    @DisplayName("Test récupération points de vie pour un Nain")
    void testDwarf() throws NoSuchFieldException, IllegalAccessException {
        player player = new player("John", "Avatar1", "DWARF", 100, new ArrayList<>());
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(40); // Moins de 50% des HP

        // Utiliser la réflexion pour régler l'XP
        UpdatePlayer.addXp(player, 0);

        UpdatePlayer.majFinDeTour(player);

        assertEquals(41, player.getCurrentHealthPoints(), "Le nain sans 'Holy Elixir' devrait gagner 1 point de vie.");
    }

    @Test
    @DisplayName("Test récupération points de vie pour un Nain avec 'Holy Elixir'")
    void testDwarfWithHolyElixir() {
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(new Item("Holy Elixir", "Recover your HP", 0.5, 50)); // Création d'un Item pour l'inventaire
        player player = new player("John", "Avatar1", "DWARF", 100, inventory);
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(40); // Moins de 50% des HP

        UpdatePlayer.majFinDeTour(player);

        assertEquals(42, player.getCurrentHealthPoints(), "Le nain avec 'Holy Elixir' devrait gagner 2 points de vie.");
    }

    @Test
    @DisplayName("Test récupération points de vie pour un joueur avec HP maximum")
    void testMaxHealthPoints() {
        player player = new player("John", "Avatar1", "ADVENTURER", 100, new ArrayList<>());
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(100); // Égal à HP maximum

        UpdatePlayer.majFinDeTour(player);

        assertEquals(100, player.getCurrentHealthPoints(), "Le joueur ne devrait pas gagner de points de vie s'il est déjà à HP maximum.");
    }

    @Test
    @DisplayName("Test récupération points de vie pour un joueur avec HP supérieur à maximum")
    void testExceedMaxHealthPoints() {
        player player = new player("John", "Avatar1", "ADVENTURER", 100, new ArrayList<>());
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(105); // Supérieur à HP maximum

        UpdatePlayer.majFinDeTour(player);

        assertEquals(100, player.getCurrentHealthPoints(), "Le joueur ne devrait pas dépasser ses points de vie maximum.");
    }

    @Test
    void testAdventurerLevelUpToLevel2() {
        player player = new player("John", "Avatar1", "ADVENTURER", 100, new ArrayList<>());
        UpdatePlayer.addXp(player, 10); // Passe au niveau 2

        System.out.println("Niveau après ajout XP : " + player.retrieveLevel());
        System.out.println("Capacités après ajout XP : " + player.getAbilities());

        HashMap<String, Integer> expectedAbilities = new HashMap<>();
        expectedAbilities.put("INT", 2);
        expectedAbilities.put("CHA", 3);

        assertEquals(2, player.retrieveLevel(), "Le joueur devrait être au niveau 2.");
        assertEquals(expectedAbilities, player.getAbilities(), "Les capacités de l'Aventurier au niveau 2 doivent être INT=2 et CHA=3.");
    }

    @Test
    void testAdventurerLevel3OrAboveHealthRecovery() {
        player player = new player("John", "Avatar1", "ADVENTURER", 100, new ArrayList<>());
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(40); // Moins de 50% des HP
        player.setXp(60); // Donne un niveau >= 3
        UpdatePlayer.majFinDeTour(player);
        assertEquals(43, player.getCurrentHealthPoints(), "Un Aventurier de niveau >= 3 devrait gagner 3 points de vie.");
    }

    @Test
    void testInvalidAvatarClass() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new player("John", "Avatar1", "INVALID", 100, new ArrayList<>());
        });
        assertEquals("Invalid avatar class: INVALID", thrown.getMessage());
    }

    @Test
    void testLevelUpWithNoAbilities() {
        player player = new player("John", "Avatar1", "ADVENTURER", 100, new ArrayList<>());
        player.setXp(111); // Donne un niveau 5
        UpdatePlayer.addXp(player, 10); // Test avec un niveau non défini pour les abilities
        assertNotNull(player.getAbilities(), "Les abilities ne doivent pas être null même si aucune capacité n’est ajoutée.");
    }

    @Test
    void testGoblinLevelUp() {
        player goblin = new player("GoblinPlayer", "Goblin Avatar", "GOBLIN", 100, new ArrayList<>());

        UpdatePlayer.addXp(goblin, 10); // Niveau 2
        assertEquals(2, goblin.retrieveLevel());
        assertTrue(goblin.getAbilities().containsKey("ATK"));
        assertEquals(3, goblin.getAbilities().get("ATK"));

        UpdatePlayer.addXp(goblin, 17); // Niveau 3
        assertEquals(3, goblin.retrieveLevel());
        assertTrue(goblin.getAbilities().containsKey("VIS"));
        assertEquals(1, goblin.getAbilities().get("VIS"));
    }

    @Test
    void testLevelUpAbilitiesForAllClasses() {
        String[] classes = {"ARCHER", "ADVENTURER", "DWARF", "GOBLIN"};
        for (String avatarClass : classes) {
            player player = new player("TestPlayer", "TestAvatar", avatarClass, 100, new ArrayList<>());
            UpdatePlayer.addXp(player, 112); // Passe au niveau 5

            assertEquals(5, player.retrieveLevel());
            assertNotNull(player.getAbilities(), "Les capacités doivent être définies pour la classe " + avatarClass);
        }
    }

}
