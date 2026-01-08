package model;

import java.time.LocalDate;

/**
 * Classe Emprunt correspondant à la table emprunts
 */
public class Emprunt {

    // Attributs correspondant exactement à la table SQL
    private int id_emprunt;
    private int id_utilisateur; // stocke l'ID, on peut aussi avoir l'objet Utilisateur
    private int id_livre;       // stocke l'ID, on peut aussi avoir l'objet Livre
    private LocalDate date_emprunt;
    private LocalDate date_retour_prevue;
    private LocalDate date_retour_effective; // nullable
    private double penalite;

   
    // ======== Constructeurs ========

    /** Constructeur sans arguments pour créer un emprunt vide */
    public Emprunt() {
        // Rien à initialiser, valeurs par défaut
    }

    /** Constructeur avec tous les attributs */
    public Emprunt(int id_emprunt, int id_utilisateur, int id_livre,
                   LocalDate date_emprunt, LocalDate date_retour_prevue,
                   LocalDate date_retour_effective, double penalite,
                   Utilisateur utilisateur, Livre livre) {
        this.id_emprunt = id_emprunt;
        this.id_utilisateur = id_utilisateur;
        this.id_livre = id_livre;
        this.date_emprunt = date_emprunt;
        this.date_retour_prevue = date_retour_prevue;
        this.date_retour_effective = date_retour_effective;
        this.penalite = penalite;
        this.utilisateur = utilisateur;
        this.livre = livre;
    }

    // ======== Getters et Setters ========

    public int getId_emprunt() {
        return id_emprunt;
    }

    public void setId_emprunt(int id_emprunt) {
        this.id_emprunt = id_emprunt;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public int getId_livre() {
        return id_livre;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }

    public LocalDate getDate_emprunt() {
        return date_emprunt;
    }

    public void setDate_emprunt(LocalDate date_emprunt) {
        this.date_emprunt = date_emprunt;
    }

    public LocalDate getDate_retour_prevue() {
        return date_retour_prevue;
    }

    public void setDate_retour_prevue(LocalDate date_retour_prevue) {
        this.date_retour_prevue = date_retour_prevue;
    }

    public LocalDate getDate_retour_effective() {
        return date_retour_effective;
    }

    public void setDate_retour_effective(LocalDate date_retour_effective) {
        this.date_retour_effective = date_retour_effective;
    }

    public double getPenalite() {
        return penalite;
    }

    public void setPenalite(double penalite) {
        this.penalite = penalite;
    }

   

    // ======== Optionnel : toString ========
    @Override
    public String toString() {
        return "Emprunt{" +
                "id_emprunt=" + id_emprunt +
                ", id_utilisateur=" + id_utilisateur +
                ", id_livre=" + id_livre +
                ", date_emprunt=" + date_emprunt +
                ", date_retour_prevue=" + date_retour_prevue +
                ", date_retour_effective=" + date_retour_effective +
                ", penalite=" + penalite ;
                
                '}';
    }
}
