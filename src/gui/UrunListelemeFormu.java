package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UrunListelemeFormu extends JFrame {
    private static final long serialVersionUID = 1L;
    private File selectedFile;

    public UrunListelemeFormu() {
        setTitle("Ürün Listeleme ve Ekleme");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

      
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Ürün ID");
        model.addColumn("Ürün Adı");
        model.addColumn("Fiyat");
        model.addColumn("Stok");
        model.addColumn("Kategori");

        JTable urunTablosu = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(urunTablosu);
        add(scrollPane, BorderLayout.CENTER);

      
        try (Connection conn = dbconnection.getConnection()) {
            String sql = "SELECT urunId, urunAdi, fiyat, stok, kategori FROM urunler";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int urunId = rs.getInt("urunId");
                String urunAdi = rs.getString("urunAdi");
                double fiyat = rs.getDouble("fiyat");
                int stok = rs.getInt("stok");
                String kategori = rs.getString("kategori");

                model.addRow(new Object[]{urunId, urunAdi, fiyat, stok, kategori});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Veritabanından ürünleri çekerken bir hata oluştu: " + e.getMessage());
        }

    
        JPanel urunEklemePanel = new JPanel(new GridBagLayout());
        urunEklemePanel.setBorder(BorderFactory.createTitledBorder("Ürün İşlemleri"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

   
        JLabel urunIdLabel = new JLabel("Ürün ID:");
        JTextField urunIdField = new JTextField(20);
        urunIdField.setEditable(false);

        JLabel urunAdiLabel = new JLabel("Ürün Adı:");
        JTextField urunAdiField = new JTextField(20);

        JLabel fiyatLabel = new JLabel("Fiyat:");
        JTextField fiyatField = new JTextField(20);

        JLabel stokLabel = new JLabel("Stok:");
        JTextField stokField = new JTextField(20);

        JLabel kategoriLabel = new JLabel("Kategori:");
        JComboBox<String> kategoriComboBox = new JComboBox<>(new String[]{
                "Elektronik", "Ev Aletleri", "Giyim", "Kitaplar", "Mobilya", "Oyuncak","Kozmetik","Oto Aksesuar","Süpermarket"
        });

        JLabel fotoLabel = new JLabel("Fotoğraf:");
        JButton fotoSecButton = new JButton("Fotoğraf Seç");

     
        JButton ekleButton = new JButton("Ürün Ekle");
        JButton duzenleButton = new JButton("Ürün Düzenle");
        JButton silButton = new JButton("Ürün Sil");
        JButton temizleButton = new JButton("Temizle");


        gbc.gridx = 0; gbc.gridy = 0;
        urunEklemePanel.add(urunIdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        urunEklemePanel.add(urunIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        urunEklemePanel.add(urunAdiLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        urunEklemePanel.add(urunAdiField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        urunEklemePanel.add(fiyatLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        urunEklemePanel.add(fiyatField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        urunEklemePanel.add(stokLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        urunEklemePanel.add(stokField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        urunEklemePanel.add(kategoriLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        urunEklemePanel.add(kategoriComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        urunEklemePanel.add(fotoLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        urunEklemePanel.add(fotoSecButton, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        urunEklemePanel.add(ekleButton, gbc);

        gbc.gridy = 7;
        urunEklemePanel.add(duzenleButton, gbc);

        gbc.gridy = 8;
        urunEklemePanel.add(silButton, gbc);

        gbc.gridy = 9;
        urunEklemePanel.add(temizleButton, gbc);

        add(urunEklemePanel, BorderLayout.SOUTH);

   
        
        
        urunTablosu.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = urunTablosu.getSelectedRow();
                if (selectedRow >= 0) {

                	urunIdField.setText(model.getValueAt(selectedRow, 0).toString());
                    urunAdiField.setText(model.getValueAt(selectedRow, 1).toString());
                    fiyatField.setText(model.getValueAt(selectedRow, 2).toString());
                    stokField.setText(model.getValueAt(selectedRow, 3).toString());
                    String kategori = model.getValueAt(selectedRow, 4).toString();
                    kategoriComboBox.setSelectedItem(kategori);
                }
            }
        });
        
        ekleButton.addActionListener(e -> {
            String urunAdi = urunAdiField.getText().trim();
            String fiyatText = fiyatField.getText().trim();
            String stokText = stokField.getText().trim();
            String kategori = (String) kategoriComboBox.getSelectedItem();


            if (urunAdi.isEmpty() || fiyatText.isEmpty() || stokText.isEmpty() || kategori == null || selectedFile == null) {
                JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun ve bir fotoğraf seçin!");
                return;
            }

            try {
                double fiyat = Double.parseDouble(fiyatText);
                int stok = Integer.parseInt(stokText);

                try (Connection conn = dbconnection.getConnection();
                     FileInputStream fis = new FileInputStream(selectedFile)) {
                    String sql = "INSERT INTO urunler (urunAdi, fiyat, stok, kategori, urunFoto) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, urunAdi);
                    pstmt.setDouble(2, fiyat);
                    pstmt.setInt(3, stok);
                    pstmt.setString(4, kategori);
                    pstmt.setBinaryStream(5, fis, (int) selectedFile.length());

                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        ResultSet generatedKeys = pstmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int urunId = generatedKeys.getInt(1);
                            model.addRow(new Object[]{urunId, urunAdi, fiyat, stok, kategori});
                        }
                        JOptionPane.showMessageDialog(null, "Ürün başarıyla eklendi!");
                        urunAdiField.setText("");
                        fiyatField.setText("");
                        stokField.setText("");
                        kategoriComboBox.setSelectedIndex(0);
                        selectedFile = null;
                    }
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Ürün eklenirken bir hata oluştu: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Fiyat ve Stok sadece sayı olmalıdır!");
            }
        });
        
        duzenleButton.addActionListener(e -> {
            String urunIdText = urunIdField.getText().trim();
            String urunAdi = urunAdiField.getText().trim();
            String fiyatText = fiyatField.getText().trim();
            String stokText = stokField.getText().trim();
            String kategori = (String) kategoriComboBox.getSelectedItem(); 

            if (urunIdText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lütfen düzenlemek istediğiniz ürünün ID'sini girin!");
                return;
            }

            try {
                int urunId = Integer.parseInt(urunIdText);
                Double fiyat = fiyatText.isEmpty() ? null : Double.parseDouble(fiyatText);
                Integer stok = stokText.isEmpty() ? null : Integer.parseInt(stokText);

                try (Connection conn = dbconnection.getConnection()) {
                    String sql = "UPDATE urunler SET urunAdi = ?, fiyat = ?, stok = ?, kategori = ?, urunFoto = ? WHERE urunId = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);

                    pstmt.setString(1, urunAdi.isEmpty() ? null : urunAdi); 
                    pstmt.setObject(2, fiyat); 
                    pstmt.setObject(3, stok); 
                    pstmt.setString(4, kategori); 

                    
                    if (selectedFile != null) {
                        try (FileInputStream fis = new FileInputStream(selectedFile)) {
                            pstmt.setBinaryStream(5, fis, (int) selectedFile.length());
                            pstmt.setInt(6, urunId); 
                            int rowsUpdated = pstmt.executeUpdate();

                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(null, "Ürün başarıyla güncellendi!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Ürün bulunamadı!");
                            }
                        }
                    } else {
                        pstmt.setNull(5, java.sql.Types.BLOB); 
                        pstmt.setInt(6, urunId); 
                        int rowsUpdated = pstmt.executeUpdate();

                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(null, "Ürün başarıyla güncellendi!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Ürün bulunamadı!");
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ürün ID, fiyat ve stok alanları geçerli bir sayı olmalıdır!");
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ürün düzenlenirken bir hata oluştu: " + ex.getMessage());
            }
        });

        
        silButton.addActionListener(e -> {
            String urunIdText = urunIdField.getText().trim();

            if (urunIdText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lütfen silmek istediğiniz ürünün ID'sini girin!");
                return;
            }

            try {
                int urunId = Integer.parseInt(urunIdText);

                int confirm = JOptionPane.showConfirmDialog(null, "Ürünü silmek istediğinize emin misiniz?", "Ürün Sil", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = dbconnection.getConnection()) {
                        String sql = "DELETE FROM urunler WHERE urunId = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, urunId);

                        int rowsDeleted = pstmt.executeUpdate();
                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(null, "Ürün başarıyla silindi!");

                       
                            for (int i = 0; i < model.getRowCount(); i++) {
                                if ((int) model.getValueAt(i, 0) == urunId) {
                                    model.removeRow(i);
                                    break;
                                }
                            }

                            urunIdField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Ürün bulunamadı!");
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ürün ID geçerli bir sayı olmalıdır!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ürün silinirken bir hata oluştu: " + ex.getMessage());
            }
        });        
        temizleButton.addActionListener(e -> {
            urunIdField.setText("");
            urunAdiField.setText("");
            fiyatField.setText("");
            stokField.setText("");
            kategoriComboBox.setSelectedIndex(0); 
            selectedFile = null;
            urunTablosu.clearSelection();
            JOptionPane.showMessageDialog(null, "Ürün bilgileri temizlendi !");
        });
        fotoSecButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "jpeg"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(this, "Seçilen dosya: " + selectedFile.getName());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UrunListelemeFormu urunListelemeFormu = new UrunListelemeFormu();
            urunListelemeFormu.setVisible(true);
        });
    }
}
