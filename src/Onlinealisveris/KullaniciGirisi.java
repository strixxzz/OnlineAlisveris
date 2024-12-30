package Onlinealisveris;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class KullaniciGirisi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("E-Posta: ");
        String email = scanner.nextLine();

        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();    
        try (Connection conn = dbconnection.getConnection()) {

           
            String sql = "SELECT * FROM kullanicilar WHERE email = ? AND sifre = ?";

            
            PreparedStatement pstmt = conn.prepareStatement(sql);

           
            pstmt.setString(1, email);
            pstmt.setString(2, sifre);

           
            ResultSet rs = pstmt.executeQuery();

           
            if (rs.next()) {
            	String ad = rs.getString("ad");
                System.out.println("Giriş başarılı! Hoş geldin, " + ad + "!");
            } else {
                System.out.println("E-Posta veya şifre yanlış!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        scanner.close(); 
    }
}
