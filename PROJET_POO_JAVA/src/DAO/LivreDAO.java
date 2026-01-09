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

    // 1. AJOUTER avec récupération de l'ID généré
    public void ajouterLivre(Livre livre) {
        String query = "INSERT INTO livres (titre, auteur, isbn, quantite_totale, quantite_disponible) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getIsbn());
            stmt.setInt(4, livre.getQuantiteTotale());
            stmt.setInt(5, livre.getQuantiteDisponible());
            
            int rowsAffected = stmt.executeUpdate();

            // Intégration de la récupération de l'ID généré
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // On met à jour l'objet Livre avec l'ID auto-incrémenté par MySQL
                        livre.setIdLivre(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Livre ajouté avec succès. ID : " + livre.getIdLivre());
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du livre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 2. AFFICHER TOUS 
    public List<Livre> getAllLivres() {
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
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
        return livres;
    }

    // 3. MODIFIER 
    public void modifierLivre(Livre livre) {
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
            System.out.println("Livre modifié avec succès.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du livre : " + e.getMessage());
        }
    }

    // 4. SUPPRIMER 
    public void supprimerLivre(int id) {
        String query = "DELETE FROM livres WHERE id_livre = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Livre supprimé avec succès.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }
    }

    // 5. RECHERCHER 
    public List<Livre> rechercherLivres(String motCle) {
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
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche : " + e.getMessage());
        }
        return resultats;
    }
}