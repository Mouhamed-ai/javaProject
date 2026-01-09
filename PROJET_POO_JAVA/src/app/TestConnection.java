package app;
import dao.DBConnection;


import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("=== Test MySQL sans Class.forName ===");
        
        // Essayer différentes URLs
        String[] urls = {
            "jdbc:mysql://localhost:3306/",
            "jdbc:mysql://127.0.0.1:3306/",
            "jdbc:mysql://localhost:3306/mysql?useSSL=false",
            "jdbc:mysql://localhost:3306/?allowPublicKeyRetrieval=true"
        };
        
        for (String url : urls) {
            System.out.println("\nEssai: " + url);
            try {
                // JDBC 4.0+ charge le driver automatiquement
                Connection conn = DriverManager.getConnection(url, "root", "");
                System.out.println("✅ SUCCÈS!");
                
                // Tester
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT VERSION()");
                if (rs.next()) {
                    System.out.println("Version: " + rs.getString(1));
                }
                
                conn.close();
                break; // Arrêter au premier succès
                
            } catch (SQLException e) {
                System.out.println("❌ Échec: " + e.getMessage());
            }
        }
    }
}