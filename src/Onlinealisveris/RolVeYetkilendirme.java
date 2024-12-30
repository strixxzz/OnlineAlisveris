package Onlinealisveris;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RolVeYetkilendirme {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sisteme giriş yapmak ister misiniz? (E/H): ");
        String cevap = scanner.nextLine();

        if (cevap.equalsIgnoreCase("E")) {
            
            System.out.print("E-posta: ");
            String email = scanner.nextLine();

            System.out.print("Şifre: ");
            String sifre = scanner.nextLine();

            try (Connection conn = dbconnection.getConnection()) {
             
                String sql = "SELECT ad, rol FROM kullanicilar WHERE email = ? AND sifre = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                pstmt.setString(2, sifre);

              
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String ad = rs.getString("ad");
                    String rol = rs.getString("rol");

                    System.out.println("Giriş başarılı! Hoş geldin, " + ad + " (" + rol + ")");

                  
                    if (rol.equalsIgnoreCase("Admin")) {
                        System.out.println("Admin yetkilerine sahipsin. Ürün ekleyebilir, silebilirsin.");
                    } else if (rol.equalsIgnoreCase("Kullanıcı")) {
                        System.out.println("Kullanıcı yetkilerine sahipsin. Ürünleri görebilir ve sipariş verebilirsin.");
                    }
                } else {
                    System.out.println("E-posta veya şifre yanlış!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
          
            System.out.println("Sisteme giriş yapılmadı. Misafir olarak devam ediyorsunuz.");
            System.out.println("Misafir yetkilerine sahipsiniz. Sadece ürünlere göz atabilirsiniz.");
        }

        scanner.close();
    }
}
