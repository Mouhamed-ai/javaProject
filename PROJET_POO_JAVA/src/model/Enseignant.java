package model;

public class Enseignant extends Utilisateur {

    public Enseignant(int id, String nom, String prenom, String matricule) {
        super(id, nom, prenom, matricule);
    }

    @Override
    public int getMaxEmprunts() {
        return 5;
    }
}