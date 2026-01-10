package app;

import java.sql.SQLException;
import java.util.List;

import dao.DBConnection;
import dao.UtilisateurDAO;
import model.Enseignant;
import model.Etudiant;
import model.Utilisateur;

public class TestUtilisateur {
    
    public static void main(String[] args) throws SQLException {
        System.out.println("=== TEST SYSTEME UTILISATEUR ===\n");
        
        try {
           
            // Test création d'utilisateurs
            testCreationUtilisateurs();
            
            // Test récupération de tous les utilisateurs
            testGetAllUtilisateurs();
            
            // 4.Test modification d'un utilisateur
            testModificationUtilisateur();
            
            // 5. Test suppression d'un utilisateur
            testSuppressionUtilisateur();
            
        } catch (SQLException e) {
            System.err.println("Erreur SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fermeture de la connexion
            DBConnection.closeConnection();
            System.out.println("\n=== TESTS TERMINES ===");
        }
    }
    
    
    private static void testCreationUtilisateurs() throws SQLException {
        System.out.println("\n2. Test création d'utilisateurs...");
        
        UtilisateurDAO dao = new UtilisateurDAO();
        
        // Création d'étudiants
        Etudiant etudiant1 = new Etudiant("Dupont", "Jean", "ETU001");
        Etudiant etudiant2 = new Etudiant("Martin", "Marie", "ETU002");
        
        // Création d'enseignants
        Enseignant enseignant1 = new Enseignant("Bernard", "Pierre", "ENS001");
        Enseignant enseignant2 = new Enseignant("Leroy", "Sophie", "ENS002");
        
        // Ajout à la base
        dao.ajouterUtilisateur(etudiant1);
        dao.ajouterUtilisateur(etudiant2);
        dao.ajouterUtilisateur(enseignant1);
        dao.ajouterUtilisateur(enseignant2);
        
        System.out.println("✅ 4 utilisateurs créés avec succès");
        System.out.println("   - " + etudiant1.getNom() + " (ID: " + etudiant1.getIdUtilisateur() + ")");
        System.out.println("   - " + etudiant2.getNom() + " (ID: " + etudiant2.getIdUtilisateur() + ")");
        System.out.println("   - " + enseignant1.getNom() + " (ID: " + enseignant1.getIdUtilisateur() + ")");
        System.out.println("   - " + enseignant2.getNom() + " (ID: " + enseignant2.getIdUtilisateur() + ")");
    }
    
    private static void testGetAllUtilisateurs() throws SQLException {
        System.out.println("\n3. Test récupération de tous les utilisateurs...");
        
        UtilisateurDAO dao = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = dao.getAllUtilisateurs();
        
        System.out.println("✅ " + utilisateurs.size() + " utilisateur(s) trouvé(s):");
        for (Utilisateur user : utilisateurs) {
            System.out.println("   - ID: " + user.getIdUtilisateur() + ", Nom: " + user.getNom() +
                               ", Prénom: " + user.getPrenom() + ", Matricule: " + user.getMatricule() +
                               ", Type: " + user.getTypeUtilisateur());
        }
    }
    
    private static void testModificationUtilisateur() throws SQLException {
        System.out.println("\n4. Test modification d'un utilisateur...");
        
        UtilisateurDAO dao = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = dao.getAllUtilisateurs();
        
        if (!utilisateurs.isEmpty()) {
            // Modification du premier utilisateur
            Utilisateur user = utilisateurs.get(0);
            String ancienNom = user.getNom();
            
            user.setNom("NouveauNom");
            user.setPrenom("NouveauPrenom");
            
            boolean modifie = dao.modifierUtilisateur(user);
            
            if (modifie) {
                System.out.println("✅ Utilisateur modifié avec succès:");
                System.out.println("   Ancien nom: " + ancienNom);
                System.out.println("   Nouveau nom: " + user.getNom());
            }
        }
    }
    
    private static void testSuppressionUtilisateur() throws SQLException {
        System.out.println("\n5. Test suppression d'un utilisateur...");
        
        UtilisateurDAO dao = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = dao.getAllUtilisateurs();
        
        if (utilisateurs.size() > 1) {
            // Suppression du dernier utilisateur
            Utilisateur userASupprimer = utilisateurs.get(utilisateurs.size() - 1);
            int idASupprimer = userASupprimer.getIdUtilisateur();
            
            boolean supprime = dao.supprimerUtilisateur(idASupprimer);
            
            if (supprime) {
                System.out.println("✅ Utilisateur ID " + idASupprimer + " supprimé avec succès");
            }
        }
    }
    
   
   
    
    
}