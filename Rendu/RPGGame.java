

// Classe principale avec le main
public class RPGGame {
    public static void main(String[] args) {
        // Création des combattants
        Battler playerBattler = new Battler("Héros", 50, 10);
        Battler enemyBattler = new Battler("Monstre", 40, 12);

        // Création des équipes avec un seul combattant
        Team playerTeam = new Team(playerBattler);
        Team enemyTeam = new Team(enemyBattler);

        // Création et lancement du jeu
        Game game = new Game(playerTeam, enemyTeam);
        game.play();
    }
}
