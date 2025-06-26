package net.javaguides.swing;

import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ShowAppointment extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int student_id;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ShowAppointment frame = new ShowAppointment(1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ShowAppointment(int student_id) {
        this.student_id = student_id;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Randevu Listesi");
        lblNewLabel.setFont(new Font("AppleGothic", Font.PLAIN, 15));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(6, 7, 880, 20);
        contentPane.add(lblNewLabel);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{
        		 "ID","Ad Soyad","Konu", "Gün", "Saat", "Akademisyen Durumu", "Durum", 
        	}, 0) {
        	    @Override
        	    public boolean isCellEditable(int row, int column) {
        	        return column == 6; // Sadece "Durum" sütunu düzenlenebilir
        	    }
        	};

        JTable table = new JTable(tableModel);

        // "Durum" sütunu için sadece "Pasif" seçeneği olan ComboBox
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Aktif","Pasif"});
        TableColumn durumColumn = table.getColumnModel().getColumn(6);
        durumColumn.setCellEditor(new DefaultCellEditor(comboBox));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 61, 866, 351);
        contentPane.add(scrollPane);
        
        JButton btnNewButton = new JButton("Kaydet");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/swing_demo",
                        "root",
                        "gelistiriciOsman_42")) {

                    String updateQuery = "UPDATE appointments SET appointment_active = ? WHERE appointment_id = ?";
                    PreparedStatement pstmt = connection.prepareStatement(updateQuery);

                    for (int i = 0; i < table.getRowCount(); i++) {
                        String durum = (String) table.getValueAt(i, 5);
                        int appointmentId = (int) table.getValueAt(i, 0);

                        if (durum.equals("Pasif")) {
                            pstmt.setBoolean(1, false);
                           
                        }
                        else {
                        	 pstmt.setBoolean(1, true);
                            
                        }
                        pstmt.setInt(2, appointmentId);
                        pstmt.addBatch();
                    }

                    pstmt.executeBatch();
                    JOptionPane.showMessageDialog(null, "Randevular güncellendi.");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
                }
            }
        });
        btnNewButton.setBounds(396, 414, 117, 29);
        contentPane.add(btnNewButton);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(6, 25, 880, 12);
        contentPane.add(separator);
        
        JLabel lblNewLabel_1 = new JLabel("ⓘ Aktif olan randevuları \"Pasif\" yaparak iptal edebilirsin!");
        lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        lblNewLabel_1.setBounds(16, 33, 844, 16);
        contentPane.add(lblNewLabel_1);
        
        JButton btnNewButton_1 = new JButton("< Geri");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 StudentHomePage studentHomePage = new StudentHomePage(student_id);
        		 studentHomePage.setVisible(true);
	                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton);
			        currentFrame.dispose();
        	}
        });
        btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        btnNewButton_1.setBounds(6, 5, 117, 20);
        contentPane.add(btnNewButton_1);

        loadAppointments(tableModel);
    }

    private void loadAppointments(DefaultTableModel tableModel) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/swing_demo",
                "root",
                "gelistiriciOsman_42")) {
        	String query = "SELECT a.appointment_id, a.appointment_day, a.appointment_time, a.appointment_active, a.appointment_approval, a.appointment_title, " +
                    "ac.academician_name, ac.academician_surname, ac.academician_title " +
                    "FROM appointments a " +
                    "JOIN academicians ac ON a.academician_id = ac.academician_id " +
                    "WHERE a.student_id = ?";
        	try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, student_id);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                	int appointment_id = rs.getInt("appointment_id");
                	String appointment_title = rs.getString("appointment_title");
                    String fullName = rs.getString("academician_title") + " " +
                            rs.getString("academician_name") + " " +
                            rs.getString("academician_surname");
                    String day = rs.getString("appointment_day");
                    String time = rs.getString("appointment_time");
                    boolean active = rs.getBoolean("appointment_active");
                    int approval = rs.getInt("appointment_approval");

                    String activeStatus = active ? "Aktif" : "Pasif";
                    String approvalStatus = switch (approval) {
                        case 1 -> "Onaylandı";
                        case 0 -> "Reddedildi";
                        default -> "Cevap Bekliyor";
                    };

                    tableModel.addRow(new Object[]{appointment_id,fullName,appointment_title, day, time, approvalStatus, activeStatus});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + e.getMessage());
        }
    }
}