public class Enseignant extends Utilisateur {

    public static final int MAX_EMPRUNTS = 5;

    public Enseignant(int id_utilisateur,
                      String nom,
                      String prenom,
                      String matricule) {

        super(id_utilisateur, nom, prenom, matricule, TypeUtilisateur.ENSEIGNANT);
    }
}