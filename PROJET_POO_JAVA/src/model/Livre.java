package model;

public class Livre {
    private  int idLivre;
    private String titre;
    private String auteur;
    private String isbn;
    private int quantiteTotale;
    private int quantiteDisponible;

    // Constructeur
    public Livre(int idLivre, String titre, String auteur, String isbn, int qteT, int qteD) {
        this.idLivre = idLivre;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.quantiteTotale = qteT;
        this.quantiteDisponible = qteD;
    }

    // Getters (nécessaires pour le TableView de JavaFX)
    public int getIdLivre() { return idLivre; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public String getIsbn() { return isbn; }
    public int getQuantiteTotale() { return quantiteTotale; }
    public int getQuantiteDisponible() { return quantiteDisponible; }
      public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }


public void setTitre(String titre) {
    this.titre = titre;
}

public void setAuteur(String auteur) {
    this.auteur = auteur;
}

public void setIsbn(String isbn) {
    this.isbn = isbn;
}

public void setQuantiteTotale(int quantiteTotale) {
    this.quantiteTotale = quantiteTotale;
}

public void setQuantiteDisponible(int quantiteDisponible) {
    this.quantiteDisponible = quantiteDisponible;
  }
}