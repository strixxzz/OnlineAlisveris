package gui;

import javax.swing.*;
import java.awt.*;

public class YoneticiFormu extends JFrame {
    private static final long serialVersionUID = 1L;

    public YoneticiFormu(String ad, String email) {
        setTitle("Yönetici Paneli");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

      
        JPanel solPanel = new JPanel();
        solPanel.setLayout(new GridLayout(3, 1, 10, 10));
        solPanel.setBorder(BorderFactory.createTitledBorder("Yönetici Bilgileri"));
        solPanel.setPreferredSize(new Dimension(200, 0));
        solPanel.setBackground(new Color(245, 245, 245)); // Sol panel için açık gri


        JLabel adLabel = new JLabel("Ad: " + ad);
        adLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel emailLabel = new JLabel("<html>E-posta: " + email + "</html>");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLabel.setForeground(Color.BLACK);
        solPanel.add(emailLabel);

        solPanel.add(adLabel);
        solPanel.add(emailLabel);

        add(solPanel, BorderLayout.WEST);

        JPanel sagPanel = new JPanel();
        sagPanel.setLayout(new GridLayout(3, 1, 10, 10));
        sagPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sagPanel.setBackground(new Color(240, 240, 240)); // Sağ panel için açık gri


        JButton siparislerButton = new JButton("Sipariş Yönetimi");
        siparislerButton.setFont(new Font("Arial", Font.BOLD, 14));
        siparislerButton.setIcon(new ImageIcon("path_to_siparisler_icon.png"));
        siparislerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                SiparislerFormu siparislerFormu = new SiparislerFormu();
                siparislerFormu.setVisible(true);
            });
        });

        JButton urunListelemeButton = new JButton("Ürün Yönetimi");
        urunListelemeButton.setFont(new Font("Arial", Font.BOLD, 14));
        urunListelemeButton.setIcon(new ImageIcon("path_to_urunler_icon.png"));
        urunListelemeButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                UrunListelemeFormu urunListelemeFormu = new UrunListelemeFormu();
                urunListelemeFormu.setVisible(true);
            });
        });

        JButton cikisButton = new JButton("Çıkış Yap");
        cikisButton.setFont(new Font("Arial", Font.BOLD, 14));
        cikisButton.setIcon(new ImageIcon("path_to_exit_icon.png"));
        cikisButton.setBackground(new Color(255, 69, 0));
        cikisButton.setForeground(Color.WHITE);
        cikisButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Çıkış yapılıyor...");
            dispose(); 
            SwingUtilities.invokeLater(() -> {
                GirisFormu girisFormu = new GirisFormu();
                girisFormu.setVisible(true);
            });
        });

        sagPanel.add(siparislerButton);
        sagPanel.add(urunListelemeButton);
        sagPanel.add(cikisButton);

        add(sagPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	String ad = "Admin Adı";
            String email = "admin@example.com";
            YoneticiFormu yoneticiFormu = new YoneticiFormu(ad, email);
            yoneticiFormu.setVisible(true);
        });
    }
}
