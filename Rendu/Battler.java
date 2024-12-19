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
        // Retour console de chaque attaque de notre personnage
        opponent.looseHP(this.power); // Utilisation de la fonction looseHP qui permet de faire perdre les HP du personnage adverse
    }

    // Fonction pour perdre des HP
    public void looseHP(int amount) {
        this.hp -= amount; // Retire les HP en fonction du montant total des dégats avec les HP restants
        if (this.hp < 0) this.hp = 0; // Regarde si les PV sont en dessous de 0 et les met à 0 / Renvoie 0 (pas en console)
        System.out.println(this.name + " perd " + amount + " HP. Points de vie restants : " + this.hp);
    } // Retourne en console les PV restants du personnage en question

    // Vérifie si le combattant est vaincu
    public boolean isDefeated() {
        return this.hp <= 0; // Si le personnage est vaincu, renvoie que les PV du personnage sont à 0
    }
}
