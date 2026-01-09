public class Etudiant extends Utilisateur {

    public static final int MAX_EMPRUNTS = 3;

    public Etudiant(int id_utilisateur,
                    String nom,
                    String prenom,
                    String matricule) {

        super(id_utilisateur, nom, prenom, matricule, TypeUtilisateur.ETUDIANT);
    }
 }
