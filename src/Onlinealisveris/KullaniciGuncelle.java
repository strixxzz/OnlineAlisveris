package Onlinealisveris;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class KullaniciGuncelle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Güncellenecek kullanıcının ID'sini girin: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Yeni Ad: ");
        String yeniAd = scanner.nextLine();

        System.out.print("Yeni Soyad: ");
        String yeniSoyad = scanner.nextLine();

        System.out.print("Yeni E-posta: ");
        String yeniEmail = scanner.nextLine();

        try (Connection conn = dbconnection.getConnection()) {

         
            String sql = "UPDATE kullanicilar SET ad = ?, soyad = ?, email = ? WHERE id = ?";

           
            PreparedStatement pstmt = conn.prepareStatement(sql);

            
            pstmt.setString(1, yeniAd); 
            pstmt.setString(2, yeniSoyad); 
            pstmt.setString(3, yeniEmail); 
            pstmt.setInt(4, id); 

          
            int rowsUpdated = pstmt.executeUpdate();

            
            if (rowsUpdated > 0) {
                System.out.println("Kullanıcı başarıyla güncellendi!");
            } else {
                System.out.println("Güncellenecek kullanıcı bulunamadı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        scanner.close(); 
    }
}
