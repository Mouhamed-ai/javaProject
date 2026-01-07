package model;

import java.time.LocalDate;

public class Emprunt {

    protected int id_emprunt;
    protected Utilisateur utilisateur;
    protected Livre livre;

    protected LocalDate date_emprunt;
    protected LocalDate date_retour_prevue;
    protected LocalDate date_retour_effective;

    protected double penalite;

    public Emprunt(int id_emprunt,
                   Utilisateur id_utilisateur,
                   Livre livre,
                   LocalDate date_emprunt,
                   LocalDate date_retour_prevue,
                   LocalDate date_retour_effective,
                   double penalite) {

        this.id_emprunt = id_emprunt;
        this.utilisateur = id_utilisateur;
        this.livre = livre;
        this.date_emprunt = date_emprunt;
        this.date_retour_prevue = date_retour_prevue;
        this.date_retour_effective = date_retour_effective;
        this.penalite = penalite;
    }

    // -------- GETTERS --------

    public int getId_emprunt() {
        return id_emprunt;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Livre getLivre() {
        return livre;
    }

    public LocalDate getDate_emprunt() {
        return date_emprunt;
    }

    public LocalDate getDate_retour_prevue() {
        return date_retour_prevue;
    }

    public LocalDate getDate_retour_effective() {
        return date_retour_effective;
    }

    public double getPenalite() {
        return penalite;
    }

    // -------- SETTERS --------
 

    public void setDate_retour_effective(LocalDate date_retour_effective) {
        this.date_retour_effective = date_retour_effective;
    }

    public void setPenalite(double penalite) {
        if (penalite >= 0) {
            this.penalite = penalite;
        }
    }
}