package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SiparislerFormu extends JFrame {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel siparisModel;

    public SiparislerFormu() {
        setTitle("AlYolunaBak - Siparişler");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

   
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(34, 45, 65));
        headerPanel.setPreferredSize(new Dimension(900, 80));
        JLabel titleLabel = new JLabel("Siparisler");
        titleLabel.setFont(new Font("Forte", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

      
        String[] columnNames = {"Sipariş ID", "Ürün Adı", "Adet", "Toplam Tutar", "Sipariş Tarihi", "Müşteri Ad Soyad"};
        siparisModel = new DefaultTableModel(columnNames, 0);
        JTable siparisTable = new JTable(siparisModel);

        
        siparisTable.setRowHeight(30);
        siparisTable.setFillsViewportHeight(true);
        siparisTable.setShowGrid(false);
        siparisTable.setIntercellSpacing(new Dimension(0, 0));
        siparisTable.setSelectionBackground(new Color(77, 148, 255));
        siparisTable.setSelectionForeground(Color.WHITE);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < siparisTable.getColumnCount(); i++) {
            siparisTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JTableHeader header = siparisTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setBackground(new Color(34, 45, 65));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(siparisTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(34, 45, 65), 2));
        add(scrollPane, BorderLayout.CENTER);

       
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton refreshButton = new JButton("Yenile");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(34, 167, 240));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new Dimension(100, 30));
        refreshButton.addActionListener(e -> loadSiparisler());
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

     
        loadSiparisler();
    }

    private void loadSiparisler() {
  
        siparisModel.setRowCount(0);

    
        try (Connection conn = dbconnection.getConnection()) {
            String sql = """
                         SELECT s.siparisId, s.urunAdi, s.adet, s.toplamTutar, s.siparisTarihi, 
                                CONCAT(k.ad, ' ', k.soyad) AS musteriAdSoyad
                         FROM siparisler s
                         INNER JOIN kullanicilar k ON s.musteriId = k.id;
                         """;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int siparisId = rs.getInt("siparisId");
                String urunAdi = rs.getString("urunAdi");
                int adet = rs.getInt("adet");
                double toplamTutar = rs.getDouble("toplamTutar");
                String siparisTarihi = rs.getString("siparisTarihi");
                String musteriAdSoyad = rs.getString("musteriAdSoyad");

                siparisModel.addRow(new Object[]{siparisId, urunAdi, adet, toplamTutar, siparisTarihi, musteriAdSoyad});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Siparişler yüklenirken bir hata oluştu. Detay: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SiparislerFormu siparislerFormu = new SiparislerFormu();
            siparislerFormu.setVisible(true);
        });
    }
}
