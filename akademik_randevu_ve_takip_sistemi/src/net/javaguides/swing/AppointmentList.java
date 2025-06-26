package net.javaguides.swing;

import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class AppointmentList extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int academician_id;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnSave;
    private JLabel lblNewLabel;
    private JSeparator separator;
    private JLabel lblRandevular;
    private JButton btnNewButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AppointmentList frame = new AppointmentList(1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AppointmentList(int academician_id) {
        this.academician_id = academician_id;
        initComponents();
        loadAppointmentData();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 737, 495);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Tablo modeli ve sadece "Onay" sütunu düzenlenebilir
        tableModel = new DefaultTableModel(new String[]{
                "Randevu ID", "Öğrenci Adı Soyadı", "Öğrenci No","Konu", "Gün", "Saat", "Onay"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        table = new JTable(tableModel);

  
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Cevap Bekliyor","Onaylandı", "Reddedildi"});
        TableColumn onayColumn = table.getColumnModel().getColumn(6);
        onayColumn.setCellEditor(new DefaultCellEditor(comboBox));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 67, 670, 329);
        contentPane.add(scrollPane);

        // Kaydet butonu
        btnSave = new JButton("Kaydet");
        btnSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try (Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/swing_demo",
                        "root",
                        "gelistiriciOsman_42")) {

                    String updateQuery = "UPDATE appointments SET appointment_approval = ? WHERE appointment_id = ?";
                    PreparedStatement pstmt = connection.prepareStatement(updateQuery);

                    for (int i = 0; i < table.getRowCount(); i++) {
                        String durum = (String) table.getValueAt(i, 5);
                        int appointmentId = (int) table.getValueAt(i, 0);
                        int stateValue;
                        
                        if( durum == "Onaylandı" ) {
                        	stateValue = 1;
                        }
                        else if(durum == "Reddedildi") {
                        	stateValue = 0;
                        }
                        else {
                        	stateValue = -1;
                        }

                            pstmt.setInt(1, stateValue);
                            pstmt.setInt(2, appointmentId);
                            pstmt.addBatch();
                        
                    }

                    pstmt.executeBatch();
                    JOptionPane.showMessageDialog(null, "Randevular başarıyla güncellendi.");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Kaydetme hatası: " + ex.getMessage());
                }
        	}
        });
        btnSave.setBounds(304, 408, 117, 29);
        contentPane.add(btnSave);
        
        lblNewLabel = new JLabel("Randevu Listesi");
        lblNewLabel.setFont(new Font("AppleGothic", Font.PLAIN, 15));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(30, 10, 670, 16);
        contentPane.add(lblNewLabel);
        
        separator = new JSeparator();
        separator.setBounds(30, 25, 670, 12);
        contentPane.add(separator);
        
        lblRandevular = new JLabel("ⓘ Lütfen bekleyen randevuları cevaplayınız");
        lblRandevular.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        lblRandevular.setHorizontalAlignment(SwingConstants.LEFT);
        lblRandevular.setBounds(30, 39, 670, 16);
        contentPane.add(lblRandevular);
        
        btnNewButton = new JButton("< Geri");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AcademicianHomePage academicianHomePage =new AcademicianHomePage(academician_id);
        		academicianHomePage.setVisible(true);
        		JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton);
		        currentFrame.dispose();
        	}
        });
        btnNewButton.setBounds(30, 7, 117, 20);
        contentPane.add(btnNewButton);

       
    }

    private void loadAppointmentData() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/swing_demo",
                "root",
                "gelistiriciOsman_42")) {

        	String query = "SELECT appointment_id, student_id, appointment_day, appointment_time, appointment_approval, appointment_active, appointment_title " +
                    "FROM appointments WHERE academician_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, academician_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("appointment_id");
                int studentId = rs.getInt("student_id");
                String appointment_title = rs.getString("appointment_title");
                String day = rs.getString("appointment_day");
                String time = rs.getString("appointment_time");
                int approval = rs.getInt("appointment_approval");
                boolean active = rs.getBoolean("appointment_active");

                if (!active) continue;

                String approvalStatus = switch (approval) {
                    case 1 -> "Onaylandı";
                    case 0 -> "Reddedildi";
                    default -> "Bekliyor";
                };

                String fullName = "", studentNumber = "";

                String studentQuery = "SELECT student_name, student_surname, student_number FROM students WHERE student_id = ?";
                PreparedStatement pstmt2 = connection.prepareStatement(studentQuery);
                pstmt2.setInt(1, studentId);
                ResultSet rs2 = pstmt2.executeQuery();
                if (rs2.next()) {
                    fullName = rs2.getString("student_name") + " " + rs2.getString("student_surname");
                    studentNumber = rs2.getString("student_number");
                }

                tableModel.addRow(new Object[]{
                        appointmentId,
                        fullName,
                        studentNumber,
                        appointment_title,
                        day,
                        time,
                        approvalStatus
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veri yükleme hatası: " + e.getMessage());
        }
    }

}