package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Livre;

public class LivreDAO {

    // 1. AJOUTER 
    public void ajouterLivre(Livre livre) throws SQLException {
        String query = "INSERT INTO livres (titre, auteur, isbn, quantite_totale, quantite_disponible) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getIsbn());
            stmt.setInt(4, livre.getQuantiteTotale());
            stmt.setInt(5, livre.getQuantiteDisponible());
            stmt.executeUpdate();
        }
    }

    // 2. AFFICHER TOUS 
    public List<Livre> getAllLivres() throws SQLException {
        List<Livre> livres = new ArrayList<>(); 
        String query = "SELECT * FROM livres";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                livres.add(new Livre(
                    rs.getInt("id_livre"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getString("isbn"),
                    rs.getInt("quantite_totale"),
                    rs.getInt("quantite_disponible")
                ));
            }
        }
        return livres;
    }

    // 3. MODIFIER 
    public void modifierLivre(Livre livre) throws SQLException {
        String query = "UPDATE livres SET titre=?, auteur=?, isbn=?, quantite_totale=?, quantite_disponible=? WHERE id_livre=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getIsbn());
            stmt.setInt(4, livre.getQuantiteTotale());
            stmt.setInt(5, livre.getQuantiteDisponible());
            stmt.setInt(6, livre.getIdLivre());
            stmt.executeUpdate();
        }
    }

    // 4. SUPPRIMER 
    public void supprimerLivre(int id) throws SQLException {
        String query = "DELETE FROM livres WHERE id_livre = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // 5. RECHERCHER 
    // Cette méthode cherche dans le titre, l'auteur OU l'ISBN
    public List<Livre> rechercherLivres(String motCle) throws SQLException {
        List<Livre> resultats = new ArrayList<>();
        String query = "SELECT * FROM livres WHERE titre LIKE ? OR auteur LIKE ? OR isbn LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String filtre = "%" + motCle + "%";
            stmt.setString(1, filtre);
            stmt.setString(2, filtre);
            stmt.setString(3, filtre);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultats.add(new Livre(
                        rs.getInt("id_livre"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("isbn"),
                        rs.getInt("quantite_totale"),
                        rs.getInt("quantite_disponible")
                    ));
                }
            }
        }
        return resultats;
    }
}