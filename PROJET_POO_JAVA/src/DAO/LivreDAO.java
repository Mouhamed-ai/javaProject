package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // Collection obligatoire selon le sujet
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Livre;

public class LivreDAO {

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

    public List<Livre> getAllLivres() throws SQLException {
        List<Livre> livres = new ArrayList<>(); // Utilisation de ArrayList
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

    public void supprimerLivre(int id) throws SQLException {
        String query = "DELETE FROM livres WHERE id_livre = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}