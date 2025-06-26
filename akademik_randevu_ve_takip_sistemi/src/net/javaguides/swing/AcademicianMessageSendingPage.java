package net.javaguides.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AcademicianMessageSendingPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField titleField;
    private JTextArea messageField;
    private JTable table;
    private DefaultTableModel tableModel;
    private int academician_id;
    private int student_id;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AcademicianMessageSendingPage frame = new AcademicianMessageSendingPage(1); // akademisyen id örnek
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AcademicianMessageSendingPage(int academician_id) {
        this.academician_id = academician_id;
        initComponents();
        loadStudentData();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 820, 420);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblStudentList = new JLabel("Öğrenci Listesi");
        lblStudentList.setFont(new Font("AppleGothic", Font.BOLD, 14));
        lblStudentList.setHorizontalAlignment(SwingConstants.CENTER);
        lblStudentList.setBounds(16, 7, 411, 20);
        contentPane.add(lblStudentList);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(16, 35, 411, 274);
        contentPane.add(scrollPane);

        tableModel = new DefaultTableModel(new String[]{"ID", "Ad Soyad", "Bölüm", "Sınıf"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        JLabel lblTitle = new JLabel("Mesajın Konusu:");
        lblTitle.setFont(new Font("AppleGothic", Font.PLAIN, 13));
        lblTitle.setBounds(439, 35, 355, 16);
        contentPane.add(lblTitle);

        titleField = new JTextField();
        titleField.setBounds(439, 50, 355, 26);
        contentPane.add(titleField);
        titleField.setColumns(10);

        JLabel lblMessage = new JLabel("Mesaj İçeriği:");
        lblMessage.setFont(new Font("AppleGothic", Font.PLAIN, 13));
        lblMessage.setBounds(439, 111, 355, 16);
        contentPane.add(lblMessage);

        messageField = new JTextArea();
        messageField.setLineWrap(true);
        messageField.setWrapStyleWord(true);

        JScrollPane messageScrollPane = new JScrollPane(messageField);
        messageScrollPane.setBounds(439, 130, 355, 179);
        contentPane.add(messageScrollPane);

        JButton btnSend = new JButton("Gönder");
        btnSend.setBounds(677, 345, 117, 29);
        contentPane.add(btnSend);
        
        JButton btnGeri = new JButton("Geri");
        btnGeri.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AcademicianHomePage academicianInbox =new AcademicianHomePage(academician_id);
        		academicianInbox.setVisible(true);
        		JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnGeri);
		        currentFrame.dispose();
        	}
        });
        btnGeri.setBounds(16, 345, 117, 29);
        contentPane.add(btnGeri);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(16, 23, 411, 12);
        contentPane.add(separator);

        btnSend.addActionListener(e -> sendMessage());
    }

    private void loadStudentData() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/swing_demo", "root", "gelistiriciOsman_42")) {

            String query = "SELECT student_id,student_number, student_name, student_surname, student_department, student_class FROM students";
            try (PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    student_id = rs.getInt("student_id");
                    String student_number = rs.getString("student_number");
                    String fullName = rs.getString("student_name") + " " + rs.getString("student_surname");
                    String department = rs.getString("student_department");
                    String studentClass = rs.getString("student_class");

                    tableModel.addRow(new Object[]{student_number, fullName, department, studentClass});
                }
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(this,
                    "Veritabanı bağlantı hatası: " + exception.getMessage(),
                    "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendMessage() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir öğrenci seçin.");
            return;
        }

       
        String title = titleField.getText().trim();
        String message = messageField.getText().trim();

        if (title.isEmpty() || message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen konu ve mesaj alanlarını doldurun.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/swing_demo", "root", "gelistiriciOsman_42")) {

            String query = "INSERT INTO studentInbox " +
                           "(studentInbox_student_id, studentInbox_academician_id, studentInbox_title, studentInbox_message, studentInbox_date) " +
                           "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, student_id);
                pstmt.setInt(2, academician_id);
                pstmt.setString(3, title);
                pstmt.setString(4, message);
                String formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
                pstmt.setString(5, formattedDate);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Mesaj başarıyla gönderildi.");
                    titleField.setText("");
                    messageField.setText("");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Mesaj gönderme hatası: " + ex.getMessage());
        }
    }
}