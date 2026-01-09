package test;
import dao.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("=== Test de connexion à la base de données ===");
        
        try {
            // Test 1: Connexion simple
            System.out.println("1. Tentative de connexion...");
            Connection conn = DBConnection.getConnection();
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ SUCCÈS: Connexion établie !");
                
                // Afficher le nom de la base de données connectée
                System.out.println("   Base de données: " + conn.getCatalog());

                // Fermer proprement
                conn.close();
                System.out.println("✅ Connexion fermée proprement.");
            } else {
                System.out.println("❌ ÉCHEC: Connexion null ou fermée.");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ ERREUR Générale: " + e.getMessage());
            e.printStackTrace();
        }
    }
}