package re.forestier.edu.rpg;

public class Affichage {

    public static String afficherJoueur(player player) {
        StringBuilder finalString = new StringBuilder("Joueur " + player.getAvatarName() + " joue par " + player.getPlayerName());
        finalString.append("\nNiveau : ").append(player.retrieveLevel()).append(" (XP totale : ").append(player.getXp()).append(")");
        finalString.append("\n\nCapacites :");
        if (player.getAbilities().isEmpty()) {
            finalString.append("\n   (aucune capacite)");
        } else {
            player.getAbilities().forEach((name, level) -> {
                finalString.append("\n   ").append(name).append(" : ").append(level);
            });
        }
        finalString.append("\n\nInventaire :");
        if (player.getInventory().isEmpty()) {
            finalString.append("\n   (aucun objet)");
        } else {
            player.getInventory().forEach(item -> {
                finalString.append("\n   ").append(item);
            });
        }
        return finalString.toString();
    }

    public static String afficherJoueurMarkdown(player player) {
        StringBuilder sb = new StringBuilder();
        sb.append("# Joueur ").append(player.getAvatarName()).append(" joue par ").append(player.getPlayerName()).append("\n");
        sb.append("## Niveau : ").append(player.retrieveLevel()).append(" (XP totale : ").append(player.getXp()).append(")\n\n");
        sb.append("### Capacites :\n");
        if (player.getAbilities().isEmpty()) {
            sb.append("* (aucune capacite)\n");
        } else {
            player.getAbilities().forEach((name, level) -> sb.append("* **").append(name).append("** : ").append(level).append("\n"));
        }
        sb.append("\n### Inventaire :\n");
        if (player.getInventory().isEmpty()) {
            sb.append("* (aucun objet)\n");
        } else {
            player.getInventory().forEach(item -> sb.append("* ").append(item.toString()).append("\n"));
        }
        return sb.toString();
    }

}
