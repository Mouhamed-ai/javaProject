package model;
enum UtilisateurType {
    ETUDIANT,
    ENSEIGNANT
}

public abstract class Utilisateur {

    protected int id_utilisateur;
    protected String nom;
    protected String prenom;
    protected String matricule;
    protected UtilisateurType type_utilisateur;

    public Utilisateur(int id_utilisateur, String nom, String prenom, String matricule, UtilisateurType type_utilisateur) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.type_utilisateur = type_utilisateur;
    }

    public abstract int getMaxEmprunts();

    public int getIdUtilisateur() {
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
    public UtilisateurType getTypeUtilisateur() {
        return type_utilisateur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
    public void setIdUtilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }
}
