import java.util.Random;
import java.util.Scanner;

// Classe de base pour les combattants
abstract class Battler {
    public String name;
    public String team; // Équipe du combattant (ROUGE ou BLEU)
    public int hp;
    public int maxHp;
    public int power;
    public int armor; // Réduction globale des dégâts (en pourcentage FLAT)
    public boolean stunned = false; // État de stun
    public int stunDuration = 0;

    public Battler(String name, String team, int hp, int power, int armor) {
        this.name = name;
        this.team = team;
        this.hp = hp;
        this.maxHp = hp;
        this.power = power;
        this.armor = armor;
    }

    public void takeDamage(int damage) {
        int reducedDamage = damage - (damage * armor / 100);
        hp -= reducedDamage;
        if (hp < 0) hp = 0;
        System.out.println("[" + getClass().getSimpleName() + "] [" + team + "] " + name +
                " reçoit " + reducedDamage + " dégâts. PV restants : " + hp);
    }

    public boolean isDefeated() {
        return hp <= 0;
    }

    public void updateTurn() {
        if (stunned && stunDuration > 0) {
            stunDuration--;
            if (stunDuration == 0) stunned = false;
        }
    }

    public abstract void action(Battler[] enemies, Scanner scanner);

    // Afficher les actions disponibles
    public abstract void displayAvailableActions();

    public void displayStats() {
        System.out.println("[" + name + "] [" + team + "] PV: " + hp + "/" + maxHp);
    }
}

// Classe Chevalier
class Chevalier extends Battler {
    private int shieldCooldown = 0;

    public Chevalier(String name, String team) {
        super(name, team, 150, 20, 10); // HP, Power, Armor
    }

    @Override
    public void action(Battler[] enemies, Scanner scanner) {
        Random rand = new Random();
        if (stunned) {
            System.out.println("[Chevalier] [" + team + "] " + name + " est étourdi et ne peut pas agir !");
            return;
        }

        displayAvailableActions();
        int choice = scanner.nextInt();
        Battler target = enemies[rand.nextInt(enemies.length)];

        switch (choice) {
            case 1:
                attack(target);
                break;
            case 2:
                coupAveuglant(target);
                break;
            case 3:
                bouclierSaint();
                break;
            default:
                System.out.println("[Chevalier] [" + team + "] " + name + " a choisi une action invalide !");
                break;
        }
    }

    public void attack(Battler target) {
        System.out.println("[Chevalier] [" + team + "] " + name + " utilise Coup d'épée !");
        target.takeDamage(power);
    }

    public void coupAveuglant(Battler target) {
        Random rand = new Random();
        if (rand.nextInt(100) < 25) {
            System.out.println("[Chevalier] [" + team + "] " + name + " utilise Coup Aveuglant et stun " + target.name + " !");
            target.stunned = true;
            target.stunDuration = 2;
        } else {
            System.out.println("[Chevalier] [" + team + "] " + name + " utilise Coup Aveuglant, mais échoue à stun !");
        }
    }

    public void bouclierSaint() {
        if (shieldCooldown == 0) {
            System.out.println("[Chevalier] [" + team + "] " + name + " utilise Bouclier Saint !");
            armor += 20;
            shieldCooldown = 3;
        } else {
            System.out.println("[Chevalier] [" + team + "] " + name + " Bouclier Saint est en recharge.");
        }
    }

    @Override
    public void displayAvailableActions() {
        System.out.println("[Chevalier] [" + team + "] " + name + " :");
        System.out.println("1. Coup d'épée");
        if (shieldCooldown == 0) {
            System.out.println("2. Coup Aveuglant");
        }
        System.out.println("3. Bouclier Saint");
    }

    @Override
    public void updateTurn() {
        super.updateTurn();
        if (shieldCooldown > 0) shieldCooldown--;
        else armor = 10;
    }
}

// Classe Archer
class Archer extends Battler {
    public Archer(String name, String team) {
        super(name, team, 110, 25, 5);
    }

    @Override
    public void action(Battler[] enemies, Scanner scanner) {
        Random rand = new Random();
        if (stunned) {
            System.out.println("[Archer] [" + team + "] " + name + " est étourdi et ne peut pas agir !");
            return;
        }

        displayAvailableActions();
        int choice = scanner.nextInt();
        Battler target = enemies[rand.nextInt(enemies.length)];

        switch (choice) {
            case 1:
                attack(target);
                break;
            case 2:
                tirMultiple(enemies);
                break;
            default:
                System.out.println("[Archer] [" + team + "] " + name + " a choisi une action invalide !");
                break;
        }
    }

    public void attack(Battler target) {
        System.out.println("[Archer] [" + team + "] " + name + " utilise Tir !");
        target.takeDamage(power);
    }

    public void tirMultiple(Battler[] targets) {
        System.out.println("[Archer] [" + team + "] " + name + " utilise Tir Multiple !");
        for (Battler target : targets) {
            target.takeDamage(power / 2);
        }
    }

    @Override
    public void displayAvailableActions() {
        System.out.println("[Archer] [" + team + "] " + name + " :");
        System.out.println("1. Tir");
        System.out.println("2. Tir Multiple");
    }
}

// Classe Mage
class Mage extends Battler {
    public Mage(String name, String team) {
        super(name, team, 75, 40, 0);
    }

    @Override
    public void action(Battler[] enemies, Scanner scanner) {
        Random rand = new Random();
        if (stunned) {
            System.out.println("[Mage] [" + team + "] " + name + " est étourdi et ne peut pas agir !");
            return;
        }

        displayAvailableActions();
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                attack(enemies[rand.nextInt(enemies.length)]);
                break;
            case 2:
                bouleDeFeu(enemies);
                break;
            default:
                System.out.println("[Mage] [" + team + "] " + name + " a choisi une action invalide !");
                break;
        }
    }

    public void attack(Battler target) {
        System.out.println("[Mage] [" + team + "] " + name + " utilise Missile Magique !");
        target.takeDamage(power);
    }

    public void bouleDeFeu(Battler[] targets) {
        System.out.println("[Mage] [" + team + "] " + name + " utilise Boule de Feu !");
        for (Battler target : targets) {
            target.takeDamage(power / 2);
        }
    }

    @Override
    public void displayAvailableActions() {
        System.out.println("[Mage] [" + team + "] " + name + " :");
        System.out.println("1. Missile Magique");
        System.out.println("2. Boule de Feu");
    }
}

// Classe principale
public class RPGGame {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Battler[] teamRed = new Battler[3];
        Battler[] teamBlue = new Battler[3];

        System.out.println("Bienvenue dans le RPG!");

        // Choix des personnages pour l'équipe ROUGE
        System.out.println("Création de l'équipe ROUGE:");
        for (int i = 0; i < 3; i++) {
            System.out.println("Choisir la classe pour le personnage " + (i + 1) + " :");
            System.out.println("1. Chevalier");
            System.out.println("2. Archer");
            System.out.println("3. Mage");
            int classChoice = scanner.nextInt();
            System.out.println("Quel est le nom de ce personnage ?");
            String name = scanner.next();

            switch (classChoice) {
                case 1:
                    teamRed[i] = new Chevalier(name, "ROUGE");
                    break;
                case 2:
                    teamRed[i] = new Archer(name, "ROUGE");
                    break;
                case 3:
                    teamRed[i] = new Mage(name, "ROUGE");
                    break;
                default:
                    System.out.println("Choix invalide, par défaut un Chevalier sera créé.");
                    teamRed[i] = new Chevalier(name, "ROUGE");
            }
        }

        // Choix des personnages pour l'équipe BLEU
        System.out.println("Création de l'équipe BLEU:");
        for (int i = 0; i < 3; i++) {
            System.out.println("Choisir la classe pour le personnage " + (i + 1) + " :");
            System.out.println("1. Chevalier");
            System.out.println("2. Archer");
            System.out.println("3. Mage");
            int classChoice = scanner.nextInt();
            System.out.println("Quel est le nom de ce personnage ?");
            String name = scanner.next();

            switch (classChoice) {
                case 1:
                    teamBlue[i] = new Chevalier(name, "BLEU");
                    break;
                case 2:
                    teamBlue[i] = new Archer(name, "BLEU");
                    break;
                case 3:
                    teamBlue[i] = new Mage(name, "BLEU");
                    break;
                default:
                    System.out.println("Choix invalide, par défaut un Chevalier sera créé.");
                    teamBlue[i] = new Chevalier(name, "BLEU");
            }
        }

        // Tour de jeu
        while (!isTeamDefeated(teamRed) && !isTeamDefeated(teamBlue)) {
            System.out.println("\n--- Tour de l'équipe ROUGE ---");
            for (Battler battler : teamRed) {
                if (!battler.isDefeated()) {
                    battler.action(teamBlue, scanner);
                }
            }

            // Affichage de l'état de l'équipe ROUGE après toutes les actions
            displayTeamStatus(teamRed);

            System.out.println("\n--- Tour de l'équipe BLEU ---");
            for (Battler battler : teamBlue) {
                if (!battler.isDefeated()) {
                    battler.action(teamRed, scanner);
                }
            }

            // Affichage de l'état de l'équipe BLEU après toutes les actions
            displayTeamStatus(teamBlue);
        }

        if (isTeamDefeated(teamRed)) {
            System.out.println("\nL'équipe BLEU remporte la victoire !");
        } else {
            System.out.println("\nL'équipe ROUGE remporte la victoire !");
        }
    }

    public static boolean isTeamDefeated(Battler[] team) {
        for (Battler battler : team) {
            if (!battler.isDefeated()) return false;
        }
        return true;
    }

    public static void displayTeamStatus(Battler[] team) {
        System.out.println("\nÉtat de l'équipe : ");
        for (Battler battler : team) {
            if (!battler.isDefeated()) {
                battler.displayStats();  // Affichage des PV
            }
        }
    }
}
