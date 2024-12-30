package Onlinealisveris;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class KullaniciEkle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        System.out.print("Ad: ");
        String ad = scanner.nextLine();

        System.out.print("Soyad: ");
        String soyad = scanner.nextLine();

        System.out.print("E-posta: ");
        String email = scanner.nextLine();

        try (Connection conn = dbconnection.getConnection()) {

            
            String sql = "INSERT INTO kullanicilar (ad, soyad, email) VALUES (?, ?, ?)";

            
            PreparedStatement pstmt = conn.prepareStatement(sql);

            
            pstmt.setString(1, ad); 
            pstmt.setString(2, soyad); 
            pstmt.setString(3, email); 

            
            int rowsInserted = pstmt.executeUpdate();

          
            if (rowsInserted > 0) {
                System.out.println("Kullanıcı başarıyla eklendi!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        scanner.close(); 
    }
}