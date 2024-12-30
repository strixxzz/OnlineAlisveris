package Onlinealisveris;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class listele {

	public static void main(String[] args) {
		
		try (Connection conn = dbconnection.getConnection();
	             Statement stmt = conn.createStatement()) {

	  
	            String sql = "SELECT * FROM kullanicilar";

	    
	            ResultSet rs = stmt.executeQuery(sql);

	     
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String ad = rs.getString("ad");
	                String soyad = rs.getString("soyad");
	                String email = rs.getString("email");
	                String sifre = rs.getString("sifre");

	                
	                System.out.println("ID: " + id + ", Ad: " + ad + ", Soyad: " + soyad + ", Email: " + email+ ",Şifre: "+sifre);
	                
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}

}
