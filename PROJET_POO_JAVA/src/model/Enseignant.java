package model;

public class Enseignant extends Utilisateur {

    public Enseignant(String nom, String prenom, String matricule) {
        super(nom, prenom, matricule,UtilisateurType.ENSEIGNANT);
    }

    @Override
    public int getMaxEmprunts() {
        return 5;
    }
}