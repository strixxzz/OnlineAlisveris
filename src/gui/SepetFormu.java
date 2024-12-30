package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SepetFormu extends JFrame {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel sepetModel; 
    private JLabel toplamTutarLabel; 
    private double toplamTutar = 0.0; 
    private int musteriId; 

   
    public SepetFormu(int musteriId) {
        this.musteriId = musteriId; 
        setTitle("AlYolunaBak - Sepetim");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 165, 0));
        headerPanel.setPreferredSize(new Dimension(600, 60));
        JLabel titleLabel = new JLabel("Sepetim");
        titleLabel.setFont(new Font("Forte", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

     
        String[] columnNames = {"Ürün Adı", "Fiyat", "Adet"};
        sepetModel = new DefaultTableModel(columnNames, 0);
        JTable sepetTable = new JTable(sepetModel);
        JScrollPane scrollPane = new JScrollPane(sepetTable);
        add(scrollPane, BorderLayout.CENTER);

  
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        toplamTutarLabel = new JLabel("Toplam Tutar: 0.0 TL");
        toplamTutarLabel.setFont(new Font("Arial", Font.BOLD, 14));
        footerPanel.add(toplamTutarLabel);

        JButton satinAlButton = new JButton("Satın Al");
        satinAlButton.setBackground(new Color(50, 205, 50));
        satinAlButton.setForeground(Color.WHITE);
        satinAlButton.setFont(new Font("Arial", Font.BOLD, 14));
        footerPanel.add(satinAlButton);

        JButton urunuCikarButton = new JButton("Ürünü Çıkar");
        urunuCikarButton.setBackground(new Color(255, 69, 0));
        urunuCikarButton.setForeground(Color.WHITE);
        urunuCikarButton.setFont(new Font("Arial", Font.BOLD, 14));
        footerPanel.add(urunuCikarButton);

        add(footerPanel, BorderLayout.SOUTH);

        
        satinAlButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(ActionEvent e) {
                if (sepetModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Sepetiniz boş!");
                } else {
                    try (Connection conn = dbconnection.getConnection()) {
                        String sql = "INSERT INTO siparisler (urunAdi, adet, toplamTutar, siparisTarihi, musteriId) VALUES (?, ?, ?, GETDATE(), ?)";
                        PreparedStatement pstmt = conn.prepareStatement(sql);

                        for (int i = 0; i < sepetModel.getRowCount(); i++) {
                            String urunAdi = (String) sepetModel.getValueAt(i, 0);
                            int adet = (int) sepetModel.getValueAt(i, 2);
                            double toplamUrunTutar = (double) sepetModel.getValueAt(i, 1) * adet;

                            pstmt.setString(1, urunAdi);
                            pstmt.setInt(2, adet);
                            pstmt.setDouble(3, toplamUrunTutar);
                            pstmt.setInt(4, musteriId); 
                            pstmt.addBatch(); 
                        }

                        pstmt.executeBatch(); 
                        JOptionPane.showMessageDialog(null, "Satın alma işlemi başarıyla tamamlandı!");

                        
                        sepetModel.setRowCount(0);
                        toplamTutar = 0.0;
                        toplamTutarLabel.setText("Toplam Tutar: 0 TL");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Satın alma işlemi sırasında bir hata oluştu: " + ex.getMessage());
                    }
                }
            }
        });

        
        urunuCikarButton.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                int selectedRow = sepetTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Lütfen çıkarılacak bir ürün seçin!");
                } else {
                   
                    double fiyat = (double) sepetModel.getValueAt(selectedRow, 1);
                    int adet = (int) sepetModel.getValueAt(selectedRow, 2);

                    
                    toplamTutar -= fiyat * adet;
                    toplamTutarLabel.setText("Toplam Tutar: " + toplamTutar + " TL");

                 
                    sepetModel.removeRow(selectedRow);
                }
            }
        });
    }

  
    public void urunEkle(String urunAdi, double fiyat, int adet) {
        boolean urunVar = false;

        
        for (int i = 0; i < sepetModel.getRowCount(); i++) {
            String mevcutUrunAdi = (String) sepetModel.getValueAt(i, 0);
            if (mevcutUrunAdi.equals(urunAdi)) {
               
                int mevcutAdet = (int) sepetModel.getValueAt(i, 2);
                sepetModel.setValueAt(mevcutAdet + adet, i, 2);

                toplamTutar += fiyat * adet;
                toplamTutarLabel.setText("Toplam Tutar: " + toplamTutar + " TL");

                urunVar = true;
                break;
            }
        }

        
        if (!urunVar) {
            sepetModel.addRow(new Object[]{urunAdi, fiyat, adet});
            toplamTutar += fiyat * adet;
            toplamTutarLabel.setText("Toplam Tutar: " + toplamTutar + " TL");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SepetFormu sepetFormu = new SepetFormu(1); 
            sepetFormu.setVisible(true);

            
            sepetFormu.urunEkle("Laptop", 15000.0, 1);
            sepetFormu.urunEkle("Mouse", 200.0, 2);
            sepetFormu.urunEkle("Laptop", 15000.0, 1); 
        });
    }
}
