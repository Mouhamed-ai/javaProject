package model;

public class Etudiant extends Utilisateur {

    public Etudiant( String nom, String prenom, String matricule) {
        super (nom, prenom, matricule, UtilisateurType.ETUDIANT);
    }

    @Override
    public int getMaxEmprunts() {
        return 3;
    }
}
