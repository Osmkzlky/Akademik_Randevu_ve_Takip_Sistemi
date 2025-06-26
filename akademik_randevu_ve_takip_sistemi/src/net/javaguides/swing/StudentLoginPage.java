package net.javaguides.swing;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentLoginPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField txtrenciMailiniziGiriniz;
	private JTextField txtifreniz;
	private JButton btnNewButton;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentLoginPage frame = new StudentLoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StudentLoginPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("İstanbul Arel Üniversitesi Öğrenci Girişi");
		lblNewLabel.setFont(new Font("AppleGothic", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(8, 133, 588, 30);
		contentPane.add(lblNewLabel);
		
		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/arelLogo.png"));

		Image scaledImage = originalIcon.getImage().getScaledInstance(105, 105, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel lblNewLabel_1 = new JLabel(scaledIcon);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(-2, 16, 600, 105);
		contentPane.add(lblNewLabel_1);
		
		
		// Mail
		ImageIcon userIcon = new ImageIcon(getClass().getResource("/images/userIcon.png"));
		Image scaledUserImage = userIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon scaledUserIcon = new ImageIcon(scaledUserImage);

		JPanel emailPanel = new JPanel(new BorderLayout(5, 0));
		emailPanel.setBounds(180, 190, 252, 30);
		emailPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		emailPanel.setBackground(Color.WHITE); 

		JLabel emailIconLabel = new JLabel(scaledUserIcon);
		emailIconLabel.setBorder(new EmptyBorder(0, 5, 0, 5)); 
		emailPanel.add(emailIconLabel, BorderLayout.WEST);

		JTextField studentEmail = new JTextField("Öğrenci Mailinizi giriniz");
		studentEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				emailPanel.setBorder(new LineBorder(new Color(0, 89, 155), 2, true));
			
			}
			@Override
			public void focusLost(FocusEvent e) {
				emailPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
			}
		});
		studentEmail.setFont(new Font("Arial", studentEmail.getFont().getStyle(), studentEmail.getFont().getSize()));
		studentEmail.setBorder(null);
		studentEmail.setForeground(Color.BLACK);
		emailPanel.add(studentEmail, BorderLayout.CENTER);

		contentPane.add(emailPanel);

		// Şifre Paneli
		ImageIcon passwordIcon = new ImageIcon(getClass().getResource("/images/passwordIcon2.png"));
		Image scaledPasswordImage = passwordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon scaledPasswordIcon = new ImageIcon(scaledPasswordImage);

		JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
		passwordPanel.setBounds(180, 230, 252, 30);
		passwordPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		passwordPanel.setBackground(Color.WHITE);

		JLabel passwordIconLabel = new JLabel(scaledPasswordIcon);
		passwordIconLabel.setBorder(new EmptyBorder(0, 5, 0, 5));
		passwordPanel.add(passwordIconLabel, BorderLayout.WEST);

		JTextField studentPassword = new JTextField("Şifreniz");
		studentPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordPanel.setBorder(new LineBorder(new Color(0, 89, 155), 2, true));
			}
			@Override
			public void focusLost(FocusEvent e) {
				passwordPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
			}
			
		});
		studentPassword.setFont(new Font("Arial", studentPassword.getFont().getStyle(), studentPassword.getFont().getSize()));
		studentPassword.setBorder(null);
		studentPassword.setForeground(Color.BLACK);
		passwordPanel.add(studentPassword, BorderLayout.CENTER);

		contentPane.add(passwordPanel);
		
		btnNewButton = new JButton("Giriş Yap");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				 btnNewButton.setBackground(new Color(255, 255, 255));
				 btnNewButton.setForeground(new Color(0, 89, 155));
			
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBackground(new Color(0, 89, 155));
				 btnNewButton.setForeground(new Color(255, 255, 255));
			}
		});
		btnNewButton.setBorder(new LineBorder(new Color(0, 91, 156), 2, true));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
		        if (studentEmail.getText().isEmpty() || 
		            studentPassword.getText().isEmpty() || 
		            studentEmail.getText().equals("Öğrenci Mailinizi giriniz") || 
		            studentPassword.getText().equals("Şifreniz")) {
		            
		            JOptionPane.showMessageDialog(null, 
		                "Lütfen Boş Alanları Doldurunuz", 
		                "Öğrenci Kayıt", 
		                JOptionPane.WARNING_MESSAGE);
		            return;
		        }
				
				
				try {
				    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo", "root", "gelistiriciOsman_42");

				    String query ="SELECT student_id,student_email, student_password FROM students WHERE student_email = ? AND student_password = ?";
				    
				    PreparedStatement pstmt =connection.prepareStatement(query);
				    pstmt.setString(1, studentEmail.getText());
				    pstmt.setString(2, studentPassword.getText());

				    ResultSet rs = pstmt.executeQuery();
				    
				   
				    
	

				    if (rs.next()) {
				    	int student_id = rs.getInt("student_id");
		                JOptionPane.showMessageDialog(null, 
		                    "Giriş Başarılı!", 
		                    "Başarılı", 
		                    JOptionPane.INFORMATION_MESSAGE);
		                
		                StudentHomePage studentHomePage = new StudentHomePage(student_id);
		                studentHomePage.setVisible(true);
		                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton);
				        currentFrame.dispose();
		               
		                
		            } else {
		                JOptionPane.showMessageDialog(null, 
		                    "Email veya şifre hatalı!", 
		                    "Giriş Hatası", 
		                    JOptionPane.ERROR_MESSAGE);
		            }


				    connection.close();

				}  catch (SQLException exception) {
		            JOptionPane.showMessageDialog(null, 
		                    "Veritabanı bağlantı hatası: " + exception.getMessage(), 
		                    "Hata", 
		                    JOptionPane.ERROR_MESSAGE);
		                exception.printStackTrace();
				}
				
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton.setBackground(new Color(0, 89, 155));
		 btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setOpaque(true);                          
		btnNewButton.setContentAreaFilled(true);               
		btnNewButton.setBounds(180, 277, 252, 30);
		contentPane.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(99, 161, 406, 12);
		contentPane.add(separator);
		
		JButton btnNewButton_1 = new JButton("Şifremi Unuttum");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			 @Override
			    public void mouseEntered(MouseEvent e) {
				 btnNewButton_1.setText("<html><u>Şifremi Unuttum</u></html>");
			    }

			    @Override
			    public void mouseExited(MouseEvent e) {
			    	btnNewButton_1.setText("Şifremi Unuttum");
			    }
		});
		btnNewButton_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ForgetPasswordPage forgetPasswordPage = new ForgetPasswordPage();
				forgetPasswordPage.setVisible(true);
				   JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton_1);
			        currentFrame.dispose();
			}
		});
		btnNewButton_1.setForeground(new Color(0, 91, 156));
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setBounds(180, 319, 252, 18);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Akademisyen Girişi");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AcademicianLoginPage academicPage = new AcademicianLoginPage();
				   academicPage.setVisible(true);
				   JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton_2);
			        currentFrame.dispose();
				
			}
		});
		btnNewButton_2.setBounds(180, 349, 252, 29);
		contentPane.add(btnNewButton_2);
	
		
	
	}
}
