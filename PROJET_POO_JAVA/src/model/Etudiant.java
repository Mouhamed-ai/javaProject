package model;

public class Etudiant extends Utilisateur {

    public Etudiant(int id, String nom, String prenom, String matricule) {
        super(id, nom, prenom, matricule);
    }

    @Override
    public int getMaxEmprunts() {
        return 3;
    }
}
