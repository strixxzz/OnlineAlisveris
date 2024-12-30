package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UrunSilmeGuncellemeFormu extends JFrame {
    private static final long serialVersionUID = 1L;

    public UrunSilmeGuncellemeFormu() {
        setTitle("Ürün Silme ve Güncelleme");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));
        setLocationRelativeTo(null);


        JLabel urunIdLabel = new JLabel("Ürün ID:");
        JTextField urunIdField = new JTextField();

        JLabel urunAdiLabel = new JLabel("Ürün Adı:");
        JTextField urunAdiField = new JTextField();

        JLabel fiyatLabel = new JLabel("Fiyat:");
        JTextField fiyatField = new JTextField();

        JLabel stokLabel = new JLabel("Stok:");
        JTextField stokField = new JTextField();

        JButton silButton = new JButton("Ürün Sil");
        JButton guncelleButton = new JButton("Ürün Güncelle");
        JButton getirButton = new JButton("Bilgileri Getir");

        add(urunIdLabel);
        add(urunIdField);
        add(urunAdiLabel);
        add(urunAdiField);
        add(fiyatLabel);
        add(fiyatField);
        add(stokLabel);
        add(stokField);
        add(getirButton);
        add(new JLabel());
        add(silButton);
        add(guncelleButton);

        getirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String urunId = urunIdField.getText();
                if (urunId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Lütfen bir Ürün ID girin!");
                    return;
                }

                try (Connection conn = dbconnection.getConnection()) {
                    String sql = "SELECT urunAdi, fiyat, stok FROM urunler WHERE urunId = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, urunId);

                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        urunAdiField.setText(rs.getString("urunAdi"));
                        fiyatField.setText(String.valueOf(rs.getDouble("fiyat")));
                        stokField.setText(String.valueOf(rs.getInt("stok")));
                    } else {
                        JOptionPane.showMessageDialog(null, "Ürün bulunamadı!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
                }
            }
        });

        silButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String urunId = urunIdField.getText();
                if (urunId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Lütfen bir Ürün ID girin!");
                    return;
                }

                try (Connection conn = dbconnection.getConnection()) {
                    String sql = "DELETE FROM urunler WHERE urunId = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, urunId);

                    int rowsDeleted = pstmt.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(null, "Ürün başarıyla silindi!");
                        urunIdField.setText("");
                        urunAdiField.setText("");
                        fiyatField.setText("");
                        stokField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ürün bulunamadı!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
                }
            }
        });

        guncelleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String urunId = urunIdField.getText();
                String urunAdi = urunAdiField.getText();
                String fiyat = fiyatField.getText();
                String stok = stokField.getText();

                if (urunId.isEmpty() || urunAdi.isEmpty() || fiyat.isEmpty() || stok.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun!");
                    return;
                }

                try (Connection conn = dbconnection.getConnection()) {
                    String sql = "UPDATE urunler SET urunAdi = ?, fiyat = ?, stok = ? WHERE urunId = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, urunAdi);
                    pstmt.setString(2, fiyat);
                    pstmt.setString(3, stok);
                    pstmt.setString(4, urunId);

                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Ürün başarıyla güncellendi!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ürün bulunamadı!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UrunSilmeGuncellemeFormu form = new UrunSilmeGuncellemeFormu();
            form.setVisible(true);
        });
    }
}
