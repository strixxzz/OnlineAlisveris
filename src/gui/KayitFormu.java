package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KayitFormu extends JFrame {
    private static final long serialVersionUID = 1L;

    public KayitFormu() {
        
        setTitle("AlYolunaBak - Kayıt Ol");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        getContentPane().setLayout(new BorderLayout());

        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 165, 0));
        headerPanel.setPreferredSize(new Dimension(600, 60));
        JLabel titleLabel = new JLabel("- AlYolunaBak - Kayıt Ol");
        titleLabel.setFont(new Font("forte", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

     
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(new Color(245, 245, 245));
        getContentPane().add(registerPanel, BorderLayout.CENTER);
                                                
                                                       
                                                        JLabel nameLabel = new JLabel("Ad :");
                                                        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                                                        GridBagConstraints gbcNameLabel = new GridBagConstraints();
                                                        gbcNameLabel.insets = new Insets(10, 10, 10, 10);
                                                        gbcNameLabel.gridx = 1;
                                                        gbcNameLabel.gridy = 1;
                                                        gbcNameLabel.anchor = GridBagConstraints.EAST;
                                                        registerPanel.add(nameLabel, gbcNameLabel);
                                                
                                                JTextField nameField = new JTextField(20);
                                                nameField.setFont(new Font("Arial", Font.PLAIN, 14));
                                                GridBagConstraints gbcNameField = new GridBagConstraints();
                                                gbcNameField.insets = new Insets(10, 10, 10, 10);
                                                gbcNameField.gridx = 2;
                                                gbcNameField.gridy = 1;
                                                gbcNameField.anchor = GridBagConstraints.WEST;
                                                registerPanel.add(nameField, gbcNameField);
                                                
                                                JLabel soyadLabel = new JLabel("Soyad :");
                                                soyadLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                                                GridBagConstraints gbcsoyadLabel = new GridBagConstraints();
                                                gbcsoyadLabel.insets = new Insets(10, 10, 10, 10);
                                                gbcsoyadLabel.gridx = 1;
                                                gbcsoyadLabel.gridy = 2;
                                                gbcsoyadLabel.anchor = GridBagConstraints.EAST;
                                                registerPanel.add(soyadLabel, gbcsoyadLabel);
                                                
                                                
                                                JTextField soyadField = new JTextField(20);
                                                soyadField.setFont(new Font("Arial", Font.PLAIN, 14));
                                                GridBagConstraints gbcsoyadField = new GridBagConstraints();
                                                gbcsoyadField.insets = new Insets(10, 10, 10, 10);
                                                gbcsoyadField.gridx = 2;
                                                gbcsoyadField.gridy = 2;
                                                gbcsoyadField.anchor = GridBagConstraints.WEST;
                                                registerPanel.add(soyadField, gbcsoyadField);
                                                
                                                
     

      
                                                JLabel emailLabel = new JLabel("E-posta:");
                                                emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                                                GridBagConstraints gbcEmailLabel = new GridBagConstraints();
                                                gbcEmailLabel.insets = new Insets(10, 10, 10, 10);
                                                gbcEmailLabel.gridx = 1;
                                                gbcEmailLabel.gridy = 3;
                                                gbcEmailLabel.anchor = GridBagConstraints.EAST;
                                                registerPanel.add(emailLabel, gbcEmailLabel);
                                        
                                                JTextField emailField = new JTextField(20);
                                                emailField.setFont(new Font("Arial", Font.PLAIN, 14));
                                                GridBagConstraints gbcEmailField = new GridBagConstraints();
                                                gbcEmailField.insets = new Insets(10, 10, 10, 10);
                                                gbcEmailField.gridx = 2;
                                                gbcEmailField.gridy = 3;
                                                gbcEmailField.anchor = GridBagConstraints.WEST;
                                                registerPanel.add(emailField, gbcEmailField);
                                
                                       
                                        JLabel passwordLabel = new JLabel("Şifre:");
                                        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                                        GridBagConstraints gbcPasswordLabel = new GridBagConstraints();
                                        gbcPasswordLabel.insets = new Insets(10, 10, 10, 10);
                                        gbcPasswordLabel.gridx = 1;
                                        gbcPasswordLabel.gridy = 4;
                                        gbcPasswordLabel.anchor = GridBagConstraints.EAST;
                                        registerPanel.add(passwordLabel, gbcPasswordLabel);
                        
                                JPasswordField passwordField = new JPasswordField(20);
                                passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
                                GridBagConstraints gbcPasswordField = new GridBagConstraints();
                                gbcPasswordField.insets = new Insets(10, 10, 10, 10);
                                gbcPasswordField.gridx = 2;
                                gbcPasswordField.gridy = 4;
                                gbcPasswordField.anchor = GridBagConstraints.WEST;
                                registerPanel.add(passwordField, gbcPasswordField);
                
                        JButton registerButton = new JButton("Kayıt Ol");
                        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
                        registerButton.setBackground(new Color(50, 205, 50));
                        registerButton.setForeground(Color.WHITE);
                        GridBagConstraints gbcRegisterButton = new GridBagConstraints();
                        gbcRegisterButton.insets = new Insets(10, 10, 10, 10);
                        gbcRegisterButton.gridx = 2;
                        gbcRegisterButton.gridy = 5;
                        gbcRegisterButton.anchor = GridBagConstraints.CENTER;
                        registerPanel.add(registerButton, gbcRegisterButton);
                        
                               
                                registerButton.addActionListener(new ActionListener() {
                                   
                                    public void actionPerformed(ActionEvent e) {
                                        String ad = nameField.getText();
                                        String soyad = soyadField.getText();
                                        String email = emailField.getText();
                                        String password = new String(passwordField.getPassword());
                                        
                        
                                        if (ad.isEmpty() || soyad.isEmpty() || email.isEmpty() || password.isEmpty()) {
                                            JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun!");
                                        } else {
                                            try (Connection conn = dbconnection.getConnection()) {
                                              
                                                String emailCheckSql = "SELECT COUNT(*) FROM kullanicilar WHERE email = ?";
                                                PreparedStatement emailCheckStmt = conn.prepareStatement(emailCheckSql);
                                                emailCheckStmt.setString(1, email);
                                                ResultSet rs = emailCheckStmt.executeQuery();

                                                if (rs.next() && rs.getInt(1) > 0) {
                                                    JOptionPane.showMessageDialog(null, "Bu e-posta adresi zaten kayıtlı!");
                                                    return; 
                                                }

                                             
                                                String sql = "INSERT INTO kullanicilar (ad, soyad, email, sifre, rol) VALUES (?, ?, ?, ?, 'Kullanıcı')";
                                                PreparedStatement pstmt = conn.prepareStatement(sql);
                                                pstmt.setString(1, ad);
                                                pstmt.setString(2, soyad);
                                                pstmt.setString(3, email);
                                                pstmt.setString(4, password);
                                                
                        
                                                int rowsInserted = pstmt.executeUpdate();
                                                if (rowsInserted > 0) {
                                                    JOptionPane.showMessageDialog(null, "Kayıt başarılı! Hoş geldiniz.");
                                                }
                                            } catch (SQLException ex) {
                                                ex.printStackTrace();
                                                JOptionPane.showMessageDialog(null, "Kayıt sırasında bir hata oluştu: " + ex.getMessage());
                                            }
                                        }
                                    }
                                });
        
                JButton backButton = new JButton("Giriş Ekranına Dön >>");
                backButton.setFont(new Font("Arial", Font.BOLD, 16));
                backButton.setBackground(new Color(70, 130, 180));
                backButton.setForeground(Color.WHITE);
                GridBagConstraints gbcBackButton = new GridBagConstraints();
                gbcBackButton.insets = new Insets(10, 10, 10, 10);
                gbcBackButton.gridx = 2;
                gbcBackButton.gridy = 6;
                gbcBackButton.anchor = GridBagConstraints.CENTER;
                registerPanel.add(backButton, gbcBackButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    GirisFormu girisFormu = new GirisFormu();
                    girisFormu.setVisible(true);
                });
                dispose(); 
            }
        });

    
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setPreferredSize(new Dimension(600, 40));
        JLabel footerLabel = new JLabel("© 2024 AlYolunaBak - Tüm hakları saklıdır.");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerPanel.add(footerLabel);
        getContentPane().add(footerPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KayitFormu kayitFormu = new KayitFormu();
            kayitFormu.setVisible(true);
        });
    }
}
