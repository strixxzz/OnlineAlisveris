package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GirisFormu extends JFrame {
    private static final long serialVersionUID = 1L;

    public GirisFormu() {
        setTitle("AlYolunaBak - Giriş Yap");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

       
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(58, 134, 255));
        headerPanel.setPreferredSize(new Dimension(600, 100));
        JLabel titleLabel = new JLabel("AlYolunaBak");
        titleLabel.setFont(new Font("Forte", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.setLayout(new GridBagLayout());
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(loginPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel emailLabel = new JLabel("E-posta:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Şifre:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Giriş Yap");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(46, 204, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(120, 40));
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(loginButton, gbc);

        JButton registerButton = new JButton("Kayıt Ol");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(52, 152, 219));
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(120, 40));
        gbc.gridx = 1;
        gbc.gridy = 3;
        loginPanel.add(registerButton, gbc);

   
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setPreferredSize(new Dimension(600, 40));
        JLabel footerLabel = new JLabel("© 2024 AlYolunaBak - Tüm hakları saklıdır.");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);

     
        AbstractAction loginAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun!");
                } else {
                    try (Connection conn = dbconnection.getConnection()) {
                        String sql = "SELECT id, ad, email, rol FROM kullanicilar WHERE email = ? AND sifre = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, email);
                        pstmt.setString(2, password);

                        ResultSet rs = pstmt.executeQuery();

                        if (rs.next()) {
                            int musteriId = rs.getInt("id");
                            String ad = rs.getString("ad");
                            String kullaniciEmail = rs.getString("email");
                            String rol = rs.getString("rol");

                            JOptionPane.showMessageDialog(null, "Giriş başarılı! Hoş geldin, " + ad + " (" + rol + ")");

                            AnaSayfaFormu anaSayfaFormu = new AnaSayfaFormu(ad, rol, email, musteriId);
                            anaSayfaFormu.setVisible(true);

                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "E-posta veya şifre yanlış!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
                    }
                }
            }
        };

       
        loginButton.addActionListener(loginAction);

      
        emailField.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);

       
        registerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                KayitFormu kayitFormu = new KayitFormu();
                kayitFormu.setVisible(true);
            });
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GirisFormu girisFormu = new GirisFormu();
            girisFormu.setVisible(true);
        });
    }
}
