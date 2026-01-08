package model;

public class Enseignant extends Utilisateur {

    public Enseignant(int id_utilisateur, String nom, String prenom, String matricule) {
        super(id_utilisateur, nom, prenom, matricule, UtilisateurType.ENSEIGNANT);
    }

    @Override
    public int getMaxEmprunts() {
        return 5;
    }
}
