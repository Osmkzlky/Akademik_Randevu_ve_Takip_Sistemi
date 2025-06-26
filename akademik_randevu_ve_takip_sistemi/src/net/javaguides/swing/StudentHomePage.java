package net.javaguides.swing;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class StudentHomePage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private int student_id;
    private String student_name;
    private String student_surname;
    private String student_img;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentHomePage frame = new StudentHomePage(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
    public StudentHomePage(int student_id) {
    	setBackground(new Color(255, 255, 255));
        this.student_id = student_id;

     

        // Veritabanından akademisyen bilgilerini al ve göster
        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/swing_demo",
                "root",
                "gelistiriciOsman_42"
            );

            String query = "SELECT student_name, student_surname,student_url FROM students WHERE student_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, student_id);
            

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	student_name = rs.getString("student_name");
            	student_surname = rs.getString("student_surname");
            	student_img=rs.getString("student_url");
               }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents(); 
    }

	/**
	 * Create the frame.
	 */
    private void initComponents() {
    	setTitle("İstanbul Arel Üniversitesi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setFont(new Font("Arial", Font.PLAIN, 13));
		contentPane.setForeground(new Color(0, 87, 154));
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		JLabel lblNewLabel_1_1 = new JLabel();
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setBounds(6, 16, 488, 105);
		contentPane.add(lblNewLabel_1_1);

	

		if (student_img != null && !student_img.isEmpty()) {
		    try {
		        ImageIcon icon = new ImageIcon(new java.net.URL(student_img));
		        Image scaledImage = icon.getImage().getScaledInstance(105, 105, Image.SCALE_SMOOTH);
		        lblNewLabel_1_1.setIcon(new ImageIcon(scaledImage));
		    } catch (Exception e) {
		        System.out.println("Resim yüklenemedi: " + e.getMessage());
		    }
		}
		
		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/arelLogo.png"));

		Image scaledImage = originalIcon.getImage().getScaledInstance(105, 105, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel lblNewLabel_1 = new JLabel(scaledIcon);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(6, 16, 488, 105);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel(student_name + " " + student_surname );
		lblNewLabel.setFont(new Font("AppleGothic", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 135, 488, 29);
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 163, 488, 12);
		contentPane.add(separator);
		
		JButton btnNewButton = new JButton("Randevu Oluştur");
		btnNewButton.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
		btnNewButton.setForeground(new Color(0, 87, 154));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setOpaque(true);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton.setBackground(new Color(0, 87, 154));
				btnNewButton.setForeground(new Color(255, 255, 255));
			
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBackground(new Color(255, 255, 255));
				btnNewButton.setForeground(new Color(0, 87, 154));
			}
		});
		btnNewButton.setBorder(new LineBorder(new Color(0, 91, 156), 2, true));

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 CreateAppointment createAppointment = new CreateAppointment(student_id);
				 createAppointment.setVisible(true);
	                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton);
			        currentFrame.dispose();
			}
		});
		btnNewButton.setBounds(175, 202, 156, 35);
		contentPane.add(btnNewButton);
		
		JButton btnRandevularGster = new JButton("Randevuları Göster");
		
		btnRandevularGster.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
		btnRandevularGster.setForeground(new Color(0, 87, 154));
		btnRandevularGster.setBackground(new Color(255, 255, 255));
		btnRandevularGster.setContentAreaFilled(false);
		btnRandevularGster.setOpaque(true);
		btnRandevularGster.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRandevularGster.setBackground(new Color(0, 87, 154));
				btnRandevularGster.setForeground(new Color(255, 255, 255));
			
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnRandevularGster.setBackground(new Color(255, 255, 255));
				btnRandevularGster.setForeground(new Color(0, 87, 154));
			}
		});
		btnRandevularGster.setBorder(new LineBorder(new Color(0, 91, 156), 2, true));
		
		
		btnRandevularGster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowAppointment showAppointment = new ShowAppointment(student_id);
				showAppointment.setVisible(true);
				 JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnRandevularGster);
			        currentFrame.dispose();
			}
		});
		btnRandevularGster.setBounds(175, 249, 156, 35);
		contentPane.add(btnRandevularGster);
		
		JButton btnNewButton_1 = new JButton("Mesaj Gönder");

		btnNewButton_1.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
		btnNewButton_1.setForeground(new Color(0, 87, 154));
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setOpaque(true);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_1.setBackground(new Color(0, 87, 154));
				btnNewButton_1.setForeground(new Color(255, 255, 255));
			
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_1.setBackground(new Color(255, 255, 255));
				btnNewButton_1.setForeground(new Color(0, 87, 154));
			}
		});
		btnNewButton_1.setBorder(new LineBorder(new Color(0, 91, 156), 2, true));
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentMessageSendingPage studentSendMessage = new StudentMessageSendingPage(student_id);
				studentSendMessage.setVisible(true);
				 JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnRandevularGster);
			        currentFrame.dispose();
			}
		});
		btnNewButton_1.setBounds(175, 296, 156, 35);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Gelen Kutusu");
		
		btnNewButton_1_1.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
		btnNewButton_1_1.setForeground(new Color(0, 87, 154));
		btnNewButton_1_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1_1.setContentAreaFilled(false);
		btnNewButton_1_1.setOpaque(true);
		btnNewButton_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_1_1.setBackground(new Color(0, 87, 154));
				btnNewButton_1_1.setForeground(new Color(255, 255, 255));
			
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_1_1.setBackground(new Color(255, 255, 255));
				btnNewButton_1_1.setForeground(new Color(0, 87, 154));
			}
		});
		btnNewButton_1_1.setBorder(new LineBorder(new Color(0, 91, 156), 2, true));
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentInbox studentInbox = new StudentInbox(student_id);
				studentInbox.setVisible(true);
				 JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnRandevularGster);
			        currentFrame.dispose();
			}
		});
		btnNewButton_1_1.setBounds(175, 343, 156, 35);
		contentPane.add(btnNewButton_1_1);
		
		
	}
    
}



