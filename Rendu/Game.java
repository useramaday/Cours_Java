
// Classe Game
class Game {
    public Team playerTeam;
    public Team enemyTeam;

    public Game(Team playerTeam, Team enemyTeam) {
        this.playerTeam = playerTeam;
        this.enemyTeam = enemyTeam;
    }

    // Fonction principale de jeu
    public void play() {
        System.out.println("Début du combat !");
        
        Battler player = playerTeam.battlers.get(0); // Premier combattant de l'équipe du joueur
        Battler enemy = enemyTeam.battlers.get(0);   // Premier combattant de l'équipe ennemie

        // Tant qu'aucune équipe n'a perdu
        while (!playerTeam.isLoose() && !enemyTeam.isLoose()) {
            // Le joueur attaque l'ennemi
            player.attack(enemy);
            if (enemy.isDefeated()) {
                System.out.println(enemy.name + " est vaincu !");
                break; // L'ennemi est vaincu
            }

            // L'ennemi attaque le joueur
            enemy.attack(player);
            if (player.isDefeated()) {
                System.out.println(player.name + " est vaincu !");
                break; // Le joueur est vaincu
            }
        }

        // Annonce de l'équipe gagnante
        Team winner = getWinnerTeam();
        System.out.println("\nL'équipe gagnante est : " + (winner == playerTeam ? "Joueur" : "Ennemi"));
    }

    // Fonction pour déterminer l'équipe gagnante
    public Team getWinnerTeam() {
        if (playerTeam.isLoose()) {
            return enemyTeam;
        } else {
            return playerTeam;
        }
    }
}