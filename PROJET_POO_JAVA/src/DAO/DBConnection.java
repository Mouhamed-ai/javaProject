package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // 1. Assure-toi que le nom de la base est en minuscules (consigne PDF)
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque";
    private static final String USER = "root"; 
    
    // 2. ICI : Ton mot de passe configuré lors de l'installation
    private static final String PASSWORD = "SK141102"; 
    
    private static Connection connection = null;
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL non trouvé", e);
            } catch (SQLException e) {
                // Message d'erreur plus clair en cas de mauvais mot de passe
                throw new SQLException("Erreur de connexion : vérifiez l'utilisateur, le mot de passe ou si MySQL est lancé.", e);
            }
        }
        return connection;
    }
    
    // ... reste du code (closeConnection)

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}