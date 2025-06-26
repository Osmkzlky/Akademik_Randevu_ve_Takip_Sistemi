package net.javaguides.swing;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AcademicianInbox extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField titleField;
    private JTextArea textArea;
    private JTextArea messageField;
    private JTable table;
    private DefaultTableModel tableModel;
    private int academician_id;
    private int student_id;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AcademicianInbox frame = new AcademicianInbox(1); // örnek id
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AcademicianInbox(int academician_id) {
        this.academician_id = academician_id;
        initComponents();
        loadMessageData();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 580);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblOgrenciListesi = new JLabel("Öğrenci Mesajları");
        lblOgrenciListesi.setHorizontalAlignment(SwingConstants.CENTER);
        lblOgrenciListesi.setFont(new Font("AppleGothic", lblOgrenciListesi.getFont().getStyle(), lblOgrenciListesi.getFont().getSize()));
        lblOgrenciListesi.setBounds(20, 10, 468, 20);
        contentPane.add(lblOgrenciListesi);

        tableModel = new DefaultTableModel(new String[]{"Tarih", "Ad Soyad","Konu","İçerik"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBounds(20, 40, 468, 220);
        contentPane.add(tableScroll);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
               
                int row =table.getSelectedRow();
                String title = (String) tableModel.getValueAt(row, 2);
                String message = (String) tableModel.getValueAt(row, 3);

                textField.setText(title);
                textArea.setText(message);
            }
        });

        JLabel lblMesajKonusu = new JLabel("Mesaj Konusu:");
        lblMesajKonusu.setBounds(500, 40, 300, 16);
        contentPane.add(lblMesajKonusu);

        textField = new JTextField();
        textField.setEditable(false);
        textField.setBounds(500, 60, 320, 26);
        contentPane.add(textField);

        JLabel lblMesajIcerigi = new JLabel("Mesaj İçeriği:");
        lblMesajIcerigi.setBounds(500, 100, 300, 16);
        contentPane.add(lblMesajIcerigi);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane msgScroll = new JScrollPane(textArea);
        msgScroll.setBounds(500, 120, 320, 140);
        contentPane.add(msgScroll);

        JLabel lblCevapKonusu = new JLabel("Cevap Konusu:");
        lblCevapKonusu.setBounds(20, 280, 300, 16);
        contentPane.add(lblCevapKonusu);

        titleField = new JTextField();
        titleField.setBounds(20, 300, 800, 26);
        contentPane.add(titleField);

        JLabel lblCevapIcerigi = new JLabel("Cevap İçeriği:");
        lblCevapIcerigi.setBounds(20, 340, 300, 16);
        contentPane.add(lblCevapIcerigi);

        messageField = new JTextArea();
        messageField.setLineWrap(true);
        messageField.setWrapStyleWord(true);
        JScrollPane replyScroll = new JScrollPane(messageField);
        replyScroll.setBounds(20, 360, 800, 120);
        contentPane.add(replyScroll);

        JButton btnSend = new JButton("Gönder");
        
        btnSend.setBounds(700, 492, 120, 30);
        btnSend.addActionListener(e -> sendMessage());
        contentPane.add(btnSend);

        JButton btnBack = new JButton("Geri");
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AcademicianHomePage academicianInbox =new AcademicianHomePage(academician_id);
        		academicianInbox.setVisible(true);
        		JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnBack);
		        currentFrame.dispose();
        	}
        });
        btnBack.setBounds(20, 490, 120, 30);
        contentPane.add(btnBack);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(20, 25, 468, 12);
        contentPane.add(separator);
    }

    private void loadMessageData() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/swing_demo", "root", "gelistiriciOsman_42")) {

            String query = "SELECT a.academicianInbox_student_id, a.academicianInbox_title, a.academicianInbox_message, a.academicianInbox_date, " +
                           "s.student_name, s.student_surname " +
                           "FROM academicianInbox a JOIN students s ON a.academicianInbox_student_id = s.student_id " +
                           "WHERE a.academicianInbox_academician_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, academician_id);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    student_id = rs.getInt("academicianInbox_student_id");
                    String fullName = rs.getString("student_name") + " " + rs.getString("student_surname");
                    String date = rs.getString("academicianInbox_date");
                    String studentMessage_title = rs.getString("academicianInbox_title");
                    String studentMessage_message = rs.getString("academicianInbox_message");
                    tableModel.addRow(new Object[]{date, fullName,studentMessage_title,studentMessage_message});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veri yükleme hatası: " + e.getMessage());
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