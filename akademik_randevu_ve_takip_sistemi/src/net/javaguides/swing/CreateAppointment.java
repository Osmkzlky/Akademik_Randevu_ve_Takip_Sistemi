package net.javaguides.swing;

import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateAppointment extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> list;
    private JLabel lblSelectedName;
    private JLabel lblImage;
    private ArrayList<String> listName = new ArrayList<>();
    private ArrayList<String> listImg = new ArrayList<>();
    private int academician_id;
    private JSeparator separator;
    private JSeparator separator_1;
    private int student_id;
    private JButton btnGeri;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CreateAppointment frame = new CreateAppointment(1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
	
    public CreateAppointment(int student_id) {
        this.student_id = student_id;
        initComponents(); 
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 572, 477);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
    	ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/arelLogo.png"));

		Image scaledImage = originalIcon.getImage().getScaledInstance(105, 105, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel lblTitle = new JLabel("Lütfen Görüşme Talebinde Bulunmak İstediğiniz Akademisyeni Seçiniz");
        lblTitle.setFont(new Font("AppleGothic", Font.PLAIN, 15));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(6, 10, 560, 16);
        contentPane.add(lblTitle);

        JLabel lblListTitle = new JLabel("Akademisyen Listesi");
        lblListTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblListTitle.setBounds(6, 210, 550, 16);
        contentPane.add(lblListTitle);

        list = new JList<>(listModel);
        list.setBorder(new LineBorder(Color.LIGHT_GRAY));
        list.setBounds(139, 240, 304, 142);
        contentPane.add(list);

        lblSelectedName = new JLabel("");
        lblSelectedName.setFont(new Font("AppleGothic", Font.PLAIN, 15));
        lblSelectedName.setBorder(new EmptyBorder(0, 0, 0, 0));
        lblSelectedName.setHorizontalAlignment(SwingConstants.CENTER);
        lblSelectedName.setBounds(139, 171, 304, 16);
        contentPane.add(lblSelectedName);

        lblImage = new JLabel();
        lblImage.setBounds(205, 41, 167, 118);
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setBorder(new LineBorder(Color.LIGHT_GRAY));
        contentPane.add(lblImage);
        
        JButton btnNewButton = new JButton("İleri");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		  DatePage datePage = new DatePage(academician_id,student_id);
        		  datePage.setVisible(true);
	                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton);
			        currentFrame.dispose();
        	}
        });
        btnNewButton.setBounds(399, 401, 167, 29);
        contentPane.add(btnNewButton);
        
        separator = new JSeparator();
        separator.setBounds(118, 225, 342, 12);
        contentPane.add(separator);
        
        separator_1 = new JSeparator();
        separator_1.setBounds(6, 25, 560, 12);
        contentPane.add(separator_1);
        
        btnGeri = new JButton("Geri");
        btnGeri.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 StudentHomePage studentHomePage = new StudentHomePage(student_id);
        		 studentHomePage.setVisible(true);
	                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnGeri);
			        currentFrame.dispose();
        	}
        });
        btnGeri.setBounds(6, 401, 167, 29);
        contentPane.add(btnGeri);
        
        		JLabel lblNewLabel_1 = new JLabel(scaledIcon);
        		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        		lblNewLabel_1.setBounds(6, 48, 560, 105);
        		contentPane.add(lblNewLabel_1);

        // Verileri veritabanından yükle
        loadAcademicians();

        // Seçim olayı: isim ve resim güncelle
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < listName.size()) {
                    lblSelectedName.setText(listName.get(selectedIndex));
                    updateImage(listImg.get(selectedIndex));
                    academician_id=selectedIndex+1;
                    
                }
            }
        });
    }

    private void loadAcademicians() {
    	
        try (Connection connection = DriverManager.getConnection(
                     "jdbc:mysql://localhost:3306/swing_demo", "root", "gelistiriciOsman_42")) {

            String query = "SELECT academician_id , academician_name, academician_surname, academician_url, academician_title FROM academicians";
            try (PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                	int id = rs.getInt("academician_id");
                    String fullName = rs.getString("academician_title") + " " +
                                      rs.getString("academician_name") + " " +
                                      rs.getString("academician_surname");
                    String imageUrl = rs.getString("academician_url");

                    listName.add(fullName);
                    listImg.add(imageUrl);
                    listModel.addElement(fullName);
                }
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(this,
                "Veritabanı bağlantı hatası: " + exception.getMessage(),
                "Hata", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
        }
    }

    private void updateImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(new java.net.URL(imageUrl));
                Image scaledImage = icon.getImage().getScaledInstance(167, 118, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(scaledImage));
              
            } catch (Exception e) {
                lblImage.setIcon(null);
                lblImage.setText("Resim yüklenemedi");
            }
        } else {
           
        }
    }
}