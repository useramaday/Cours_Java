
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
