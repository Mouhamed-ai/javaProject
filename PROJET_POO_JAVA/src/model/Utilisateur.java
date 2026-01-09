public class Utilisateur {

    protected int id_utilisateur;
    protected String nom;
    protected String prenom;
    protected String matricule;
    protected TypeUtilisateur type_utilisateur;

    public Utilisateur(int id_utilisateur,
                       String nom,
                       String prenom,
                       String matricule,
                       TypeUtilisateur type_utilisateur) {

        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.type_utilisateur = type_utilisateur;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public TypeUtilisateur getType_utilisateur() {
        return type_utilisateur;
    }
}
