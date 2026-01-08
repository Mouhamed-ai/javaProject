package model;

public class Etudiant extends Utilisateur {

    public Etudiant(int id_utilisateur, String nom, String prenom, String matricule) {
        super(id_utilisateur, nom, prenom, matricule, UtilisateurType.ETUDIANT);
    }

    @Override
    public int getMaxEmprunts() {
        return 3;
    }
}
