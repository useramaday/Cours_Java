import java.util.ArrayList;
import java.util.List;

// Classe Battler
class Battler {
    public String name;  // Nom du personnage
    public int hp;       // Points de vie
    public int power;    // Puissance d'attaque

    public Battler(String name, int hp, int power) {
        this.name = name;
        this.hp = hp;
        this.power = power;
    }

    // Fonction pour attaquer un adversaire
    public void attack(Battler opponent) {
        System.out.println(this.name + " attaque " + opponent.name + " avec " + this.power + " de puissance.");
        opponent.looseHP(this.power);
    }

    // Fonction pour perdre des HP
    public void looseHP(int amount) {
        this.hp -= amount;
        if (this.hp < 0) this.hp = 0;
        System.out.println(this.name + " perd " + amount + " HP. Points de vie restants : " + this.hp);
    }

    // Vérifie si le combattant est vaincu
    public boolean isDefeated() {
        return this.hp <= 0;
    }
}

// Classe Team
class Team {
    public List<Battler> battlers;

    public Team(Battler battler) {
        this.battlers = new ArrayList<>();
        this.battlers.add(battler);
    }

    // Fonction pour vérifier si l'équipe a perdu
    public boolean isLoose() {
        for (Battler b : battlers) {
            if (!b.isDefeated()) {
                return false; // Tant qu'un combattant a encore des HP, l'équipe n'a pas perdu
            }
        }
        return true; // Tous les combattants sont vaincus
    }
}

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
