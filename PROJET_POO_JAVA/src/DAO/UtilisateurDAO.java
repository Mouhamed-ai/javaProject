package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Utilisateur;
import model.Etudiant;
import model.Enseignant;
import model.UtilisateurType;

public class UtilisateurDAO {
    
    public void ajouterUtilisateur(Utilisateur utilisateur) throws SQLException {
        String sql = "INSERT INTO utilisateurs (nom, prenom, matricule, type_utilisateur) "
                   + "VALUES (?, ?, ?, ?)";
// PreparedStatement est un modèle de requête SQL pré-compilé avec des trous (?) qu'on remplit après avec des valeurs.

// En une phrase :
// C'est un formulaire pré-remplissable pour la base de données où on met les valeurs dans les champs □ après avoir créé le formulaire.
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getPrenom());
            pstmt.setString(3, utilisateur.getMatricule());
            pstmt.setString(4, utilisateur.getTypeUtilisateur().toString());  
            
            int rowsAffected = pstmt.executeUpdate();
            // C'est la méthode qui exécute une requête SQL (INSERT, UPDATE, DELETE) et retourne le nombre de lignes affectées.
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    // getGeneratedKeys:C'est la méthode qui récupère les IDs auto-générés (comme AUTO_INCREMENT) après un INSERT.
                    if (generatedKeys.next()) {
                        utilisateur.setIdUtilisateur(generatedKeys.getInt(1));
                        
                    }
                }
            }
            
            System.out.println("Utilisateur ajouté avec succès");
        }
    }
    
    public List<Utilisateur> getAllUtilisateurs() throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs";
        
         try (Connection conn = DBConnection.getConnection(); //Obtient une connexion à la base de données
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {  
           
            while (rs.next()) { 
                Utilisateur user = creerUtilisateurFromResultSet(rs); 
                utilisateurs.add(user);
            }
        }
        return utilisateurs;
    }
    

    private Utilisateur creerUtilisateurFromResultSet(ResultSet rs) throws SQLException {           
        int id = rs.getInt("id_utilisateur");           
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        String matricule = rs.getString("matricule");
        String typeStr = rs.getString("type_utilisateur");
        
        UtilisateurType type = UtilisateurType.valueOf(typeStr);
        
        if (type == UtilisateurType.ETUDIANT) {
            return new Etudiant(id, nom, prenom, matricule);
        } else {
            return new Enseignant(id, nom, prenom, matricule);
        }
    }

     public boolean modifierUtilisateur(Utilisateur utilisateur) throws SQLException {
        String sql = "UPDATE utilisateurs SET nom = ?, prenom = ?, matricule = ?, "
                   + "type_utilisateur = ? WHERE id_utilisateur = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getPrenom());
            pstmt.setString(3, utilisateur.getMatricule());
            pstmt.setString(4, utilisateur.getTypeUtilisateur().toString());
            pstmt.setInt(5, utilisateur.getIdUtilisateur());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Utilisateur modifié avec succès (ID: " + utilisateur.getIdUtilisateur() + ")");
                return true;
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID: " + utilisateur.getIdUtilisateur());
                return false;
            }
        }
    }

    public boolean supprimerUtilisateur(int id) throws SQLException {
        String sql = "DELETE FROM utilisateurs WHERE id_utilisateur = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès (ID: " + id + ")");
                return true;
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID: " + id);
                return false;
            }
        }
    }
    
}