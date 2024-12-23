package re.forestier.edu;

import java.util.ArrayList;

import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.Item;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

public class Main {

    public static void main(String[] args) {
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(new Item("Rune Staff of Curse", "May burn your enemies... Or yourself. Who knows?", 1.5, 150));

        player firstPlayer = new player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, inventory);

        firstPlayer.addMoney(400);

        UpdatePlayer.addXp(firstPlayer, 15); // Ajoute 15 XP
        System.out.println(Affichage.afficherJoueur(firstPlayer)); // Affiche l'état du joueur
        System.out.println("------------------");
        UpdatePlayer.addXp(firstPlayer, 20); // Ajoute 20 XP
        System.out.println(Affichage.afficherJoueur(firstPlayer)); // Affiche l'état du joueur
    }

}
