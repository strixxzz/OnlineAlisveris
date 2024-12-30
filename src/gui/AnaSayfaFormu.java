package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnaSayfaFormu extends JFrame {
    private static final long serialVersionUID = 1L;
    private SepetFormu sepetFormu;
    private int musteriId;
    private JPanel urunPanel;

    public AnaSayfaFormu(String ad, String rol, String email, int musteriId) {
        this.musteriId = musteriId;
        this.sepetFormu = new SepetFormu(musteriId);

        setTitle("Ana Sayfa - AlYolunaBak");
        setSize(1700, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(58, 134, 255));
        headerPanel.setPreferredSize(new Dimension(1600, 80));
        add(headerPanel, BorderLayout.NORTH);

        JLabel hosgeldinizLabel = new JLabel("Hoş geldin, " + ad + " (" + rol + ")");
        hosgeldinizLabel.setFont(new Font("Arial", Font.BOLD, 24));
        hosgeldinizLabel.setForeground(Color.WHITE);
        hosgeldinizLabel.setHorizontalAlignment(SwingConstants.LEFT);
        hosgeldinizLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
        headerPanel.add(hosgeldinizLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        if (rol.equalsIgnoreCase("Admin")) {
            JButton yonetButton = new JButton("Yönet");
            yonetButton.setFont(new Font("Arial", Font.BOLD, 16));
            yonetButton.setPreferredSize(new Dimension(120, 40));
            yonetButton.setBackground(new Color(46, 204, 113));
            yonetButton.setForeground(Color.WHITE);
            yonetButton.setFocusPainted(false);
            buttonPanel.add(yonetButton);

            yonetButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
                YoneticiFormu yoneticiFormu = new YoneticiFormu(ad, email);
                yoneticiFormu.setLocationRelativeTo(null);
                yoneticiFormu.setVisible(true);
            }));
        }

        JButton sepetimButton = new JButton("Sepetim");
        sepetimButton.setFont(new Font("Arial", Font.BOLD, 16));
        sepetimButton.setPreferredSize(new Dimension(120, 40));
        sepetimButton.setBackground(new Color(255, 215, 0));
        sepetimButton.setForeground(Color.BLACK);
        buttonPanel.add(sepetimButton);

        sepetimButton.addActionListener(e -> sepetFormu.setVisible(true));

        JButton cikisButton = new JButton("Çıkış Yap");
        cikisButton.setFont(new Font("Arial", Font.BOLD, 16));
        cikisButton.setPreferredSize(new Dimension(120, 40));
        cikisButton.setBackground(new Color(231, 76, 60));
        cikisButton.setForeground(Color.WHITE);
        buttonPanel.add(cikisButton);

        cikisButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Çıkış yapılıyor...");
            dispose();
            SwingUtilities.invokeLater(() -> {
                GirisFormu girisFormu = new GirisFormu();
                girisFormu.setVisible(true);
            });
        });

     
        JPanel kategoriPanel = new JPanel();
        kategoriPanel.setLayout(new BoxLayout(kategoriPanel, BoxLayout.Y_AXIS));
        kategoriPanel.setBackground(new Color(240, 240, 240));
        kategoriPanel.setBorder(BorderFactory.createTitledBorder("Kategoriler"));
        kategoriPanel.setPreferredSize(new Dimension(200, 0));
        add(kategoriPanel, BorderLayout.WEST);

        String[] kategoriler = {"Tümü", "Elektronik", "Ev Aletleri", "Kitaplar", "Kozmetik","Oto Aksesuar","Süpermarket"};
        for (String kategori : kategoriler) {
            JButton kategoriButton = new JButton(kategori);
            kategoriButton.setFont(new Font("Arial", Font.PLAIN, 14));
            kategoriButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            kategoriButton.setBackground(Color.WHITE);
            kategoriButton.setFocusPainted(false);
            kategoriButton.addActionListener(e -> urunleriListele(kategori.equals("Tümü") ? null : kategori));
            kategoriPanel.add(kategoriButton);
            kategoriPanel.add(Box.createVerticalStrut(10)); 
        }

    
        urunPanel = new JPanel();
        urunPanel.setLayout(new GridLayout(0, 4, 20, 20));
        urunPanel.setBackground(new Color(245, 245, 245));
        urunPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(new JScrollPane(urunPanel), BorderLayout.CENTER);

     
        urunleriListele(null);
    }

    private void urunleriListele(String kategori) {
        urunPanel.removeAll(); 
        try (Connection conn = dbconnection.getConnection()) {
            String sql = """
                    SELECT urunId, urunAdi, fiyat, stok, urunFoto 
                    FROM urunler
                    """ + (kategori != null ? "WHERE kategori = ?" : "");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (kategori != null) {
                pstmt.setString(1, kategori);
            }
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int urunId = rs.getInt("urunId");
                String urunAdi = rs.getString("urunAdi");
                double fiyat = rs.getDouble("fiyat");
                int stok = rs.getInt("stok");
                byte[] urunFoto = rs.getBytes("urunFoto");

                JPanel urunKart = new JPanel(new BorderLayout());
                urunKart.setPreferredSize(new Dimension(320, 400));
                urunKart.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                urunKart.setBackground(Color.WHITE);

                JLabel fotoLabel = new JLabel();
                fotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                fotoLabel.setPreferredSize(new Dimension(300, 200));
                if (urunFoto != null) {
                    try {
                        ByteArrayInputStream bais = new ByteArrayInputStream(urunFoto);
                        BufferedImage img = ImageIO.read(bais);
                        Image scaledImage = img.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                        fotoLabel.setIcon(new ImageIcon(scaledImage));
                    } catch (Exception ex) {
                        fotoLabel.setText("Fotoğraf yüklenemedi");
                    }
                } else {
                    fotoLabel.setText("Fotoğraf yok");
                }
                urunKart.add(fotoLabel, BorderLayout.NORTH);

                JPanel bilgiPaneli = new JPanel(new GridLayout(3, 1, 5, 5));
                bilgiPaneli.setBackground(Color.WHITE);
                bilgiPaneli.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel urunAdiLabel = new JLabel(urunAdi);
                urunAdiLabel.setFont(new Font("Arial", Font.BOLD, 16));
                urunAdiLabel.setHorizontalAlignment(SwingConstants.CENTER);
                bilgiPaneli.add(urunAdiLabel);

                JLabel fiyatLabel = new JLabel(fiyat + " TL");
                fiyatLabel.setFont(new Font("Arial", Font.BOLD, 14));
                fiyatLabel.setForeground(new Color(46, 204, 113));
                fiyatLabel.setHorizontalAlignment(SwingConstants.CENTER);
                bilgiPaneli.add(fiyatLabel);

                JLabel stokLabel;
                if (stok > 0) {
                    stokLabel = new JLabel("Stokta Var"); 
                    stokLabel.setForeground(new Color(46, 204, 113)); // Yeşil renk
                } else {
                    stokLabel = new JLabel("Stokta Yok"); 
                    stokLabel.setForeground(new Color(231, 76, 60)); // Kırmızı renk
                }
                stokLabel.setFont(new Font("Arial", Font.BOLD, 14)); 
                stokLabel.setHorizontalAlignment(SwingConstants.CENTER); 
                bilgiPaneli.add(stokLabel);

                urunKart.add(bilgiPaneli, BorderLayout.CENTER);

                JButton sepeteEkleButton = new JButton("Sepete Ekle");
                sepeteEkleButton.setBackground(new Color(52, 152, 219));
                sepeteEkleButton.setForeground(Color.WHITE);
                sepeteEkleButton.setFont(new Font("Arial", Font.BOLD, 15));
                sepeteEkleButton.setPreferredSize(new Dimension(60, 40)); 
                urunKart.add(sepeteEkleButton, BorderLayout.SOUTH);

                sepeteEkleButton.addActionListener(e -> {
                    if (stok > 0) {
                        sepetFormu.urunEkle(urunAdi, fiyat, 1);
                        JOptionPane.showMessageDialog(null, urunAdi + " sepete eklendi!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Üzgünüz, bu ürün stokta yok!");
                    }
                });

                urunPanel.add(urunKart);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Ürünler yüklenirken bir hata oluştu: " + e.getMessage());
        }
        urunPanel.revalidate();
        urunPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AnaSayfaFormu anaSayfaFormu = new AnaSayfaFormu("Kullanıcı Adı", "Admin", "admin@example.com", 1);
            anaSayfaFormu.setVisible(true);
        });
    }
}
