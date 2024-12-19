import java.util.ArrayList;
import java.util.List;

public class Matiere {
    public String label;      // Nom de la matière
    public float[] notes;     // Tableau de notes

    // Constructeur
    public Matiere(String label, float[] notes) {
        this.label = label;
        this.notes = notes;
    }

    // Méthode pour calculer la moyenne d'une matière
    public float calculMoyenne() {
        // Faire la somme des notes
        float sum = 0f;

        for (float note : notes) {
            sum += note;
        }

        // Diviser la somme par le nombre de notes
        float average = sum / notes.length;

        // Afficher les informations
        System.out.println("Somme de " + label + " : " + sum);
        System.out.println("La moyenne de " + label + " est : " + average);

        return average;
    }

    public static void main(String[] args) {
        // -- Création des matières avec leurs notes
        Matiere francais = new Matiere("Français", new float[]{12, 8, 13, 6});
        Matiere math = new Matiere("Math", new float[]{4, 9, 16, 18});
        Matiere sport = new Matiere("Sport", new float[]{15, 13, 17, 2, 6});

        // -- Créer une liste de matières
        List<Matiere> matieres = new ArrayList<>();
        matieres.add(francais);
        matieres.add(math);
        matieres.add(sport);

        // -- Parcourir la liste pour calculer les moyennes
        float sommeDesMoyennes = 0;

        System.out.println("\nCalcul des moyennes par matière :");
        for (Matiere matiere : matieres) {
            float moyenne = matiere.calculMoyenne();
            sommeDesMoyennes += moyenne; // Additionner chaque moyenne
        }

        // -- Calcul de la moyenne générale
        float moyenneGenerale = sommeDesMoyennes / matieres.size();

        // Afficher la moyenne générale
        System.out.println("\nLa moyenne générale des matières est : " + moyenneGenerale);
    }
}
