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
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getPrenom());
            pstmt.setString(3, utilisateur.getMatricule());
            pstmt.setString(4, utilisateur.getTypeUtilisateur().toString());  
            
            int rowsAffected = pstmt.executeUpdate();
            
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    
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
        
         try (Connection conn = DBConnection.getConnection(); 
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
}