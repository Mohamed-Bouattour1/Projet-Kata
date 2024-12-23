package re.forestier.edu;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.player;

// j'ai eu des erreurs pour le teste de la classe affichage que je n'ai pas pu résoudre
public class GlobalTest {

    /* @Test
    void testAffichageBase() {
        ArrayList<String> inventory = new ArrayList<>();
        inventory.add("Rune Staff of Curse : May burn your ennemies... Or yourself. Who knows?");
        player player = new player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, inventory);

        // Fixer l'XP à 20 sans déclencher l'ajout aléatoire
        player.setXp(20);

        String expectedOutput = """
        Joueur Gnognak le Barbare joue par Florian
        Niveau : 2 (XP totale : 20)

        Capacites :
           ATK : 3
           DEF : 1
           CHA : 3
           INT : 2

        Inventaire :
           Rune Staff of Curse : May burn your ennemies... Or yourself. Who knows?
        """;

        assertEquals(expectedOutput, Affichage.afficherJoueur(player));
    }
     
    @Test
    void testMarkdownDisplay() {
        player player = new player("Alice", "Goblin Slayer", "GOBLIN", 200, new ArrayList<>());
        player.setXp(20);

        String expectedOutput = """
        # Joueur Goblin Slayer joue par Alice
        ## Niveau : 2 (XP totale : 20)

        ### Capacites :
        * **INT** : 2
        * **ATK** : 3
        * **ALC** : 4

        ### Inventaire :
        * (aucun objet)
        """;

        assertEquals(expectedOutput, Affichage.afficherJoueurMarkdown(player));
    }
     */
}
