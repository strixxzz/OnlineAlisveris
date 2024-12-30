package Onlinealisveris;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=online_alisveris;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa"; 
    private static final String PASSWORD = "123"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        Connection connection = null;

        try {
            connection = getConnection();
            System.out.println("Bağlantı başarılı!");
        } catch (SQLException e) {
            System.out.println("Bağlantı hatası: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Bağlantı kapatıldı.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
