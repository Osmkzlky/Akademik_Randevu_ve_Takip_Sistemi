package net.javaguides.swing;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.sql.*;
import java.util.HashMap;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentMessageSendingPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField titleField;
    private int student_id;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private HashMap<String, Integer> nameToIdMap = new HashMap<>(); // İsimden ID'ye eşleme

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                StudentMessageSendingPage frame = new StudentMessageSendingPage(1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public StudentMessageSendingPage(int student_id) {
        this.student_id = student_id;

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/swing_demo", "root", "gelistiriciOsman_42")) {

            String query = "SELECT academician_id, academician_name, academician_surname, academician_title FROM academicians";
            try (PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("academician_id");
                    String fullName = rs.getString("academician_title") + " " +
                                      rs.getString("academician_name") + " " +
                                      rs.getString("academician_surname");
                    listModel.addElement(fullName);
                    nameToIdMap.put(fullName, id);
                }
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(this,
                "Veritabanı bağlantı hatası: " + exception.getMessage(),
                "Hata", JOptionPane.ERROR_MESSAGE);
        }

        initComponents();
    }

    private void initComponents() {
    	setTitle("İstanbul Arel Üniversitesi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 384);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JList<String> list = new JList<>(listModel);
        list.setBounds(16, 34, 411, 249);
        contentPane.add(list);

        titleField = new JTextField();
        titleField.setBounds(439, 50, 355, 26);
        contentPane.add(titleField);
        titleField.setColumns(10);

        JLabel lblTitle = new JLabel("Mesajın Konusu:");
        lblTitle.setFont(new Font("AppleGothic", Font.PLAIN, 13));
        lblTitle.setBounds(439, 35, 355, 16);
        contentPane.add(lblTitle);

        JLabel lblMessage = new JLabel("Mesaj İçeriği:");
        lblMessage.setFont(new Font("AppleGothic", lblMessage.getFont().getStyle(), lblMessage.getFont().getSize()));
        lblMessage.setBounds(439, 111, 355, 16);
        contentPane.add(lblMessage);

        JTextArea messageField = new JTextArea();
        messageField.setLineWrap(true);                     
        messageField.setWrapStyleWord(true);               

        JScrollPane scrollPane = new JScrollPane(messageField);
        scrollPane.setBounds(439, 130, 355, 153);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        contentPane.add(scrollPane);

        JLabel lblAkademisyenListesi = new JLabel("Akademisyen Listesi");
        lblAkademisyenListesi.setFont(new Font("AppleGothic", lblAkademisyenListesi.getFont().getStyle(), lblAkademisyenListesi.getFont().getSize()));
        lblAkademisyenListesi.setHorizontalAlignment(SwingConstants.CENTER);
        lblAkademisyenListesi.setBounds(16, 6, 411, 16);
        contentPane.add(lblAkademisyenListesi);

        JSeparator separator = new JSeparator();
        separator.setBounds(16, 19, 411, 12);
        contentPane.add(separator);

        JButton btnSend = new JButton("Gönder");
        btnSend.addActionListener((ActionListener) e -> {
            String selectedName = list.getSelectedValue();
            if (selectedName == null || !nameToIdMap.containsKey(selectedName)) {
                JOptionPane.showMessageDialog(this, "Lütfen bir akademisyen seçin.");
                return;
            }

            int academician_id = nameToIdMap.get(selectedName);
            String title = titleField.getText().trim();
            String message = messageField.getText().trim();

            if (title.isEmpty() || message.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen konu ve mesaj alanlarını doldurun.");
                return;
            }

            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swing_demo",
                    "root",
                    "gelistiriciOsman_42")) {

                String query = "INSERT INTO academicianInbox " +
                               "(academicianInbox_student_id, academicianInbox_academician_id, academicianInbox_title, academicianInbox_message, academicianInbox_date)" +
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
        });

        btnSend.setBounds(677, 308, 117, 29);
        contentPane.add(btnSend);
        
        JButton btnGeri = new JButton("Geri");
        btnGeri.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		StudentHomePage studentHome = new StudentHomePage(student_id);
        		studentHome.setVisible(true);
        		 JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnGeri);
			        currentFrame.dispose();
        	}
        });
        btnGeri.setBounds(6, 308, 117, 29);
        contentPane.add(btnGeri);
    }
}