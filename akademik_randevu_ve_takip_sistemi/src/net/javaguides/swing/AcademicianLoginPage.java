package net.javaguides.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AcademicianLoginPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AcademicianLoginPage frame = new AcademicianLoginPage();
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
	public AcademicianLoginPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/arelLogo.png"));

		Image scaledImage = originalIcon.getImage().getScaledInstance(105, 105, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel lblNewLabel = new JLabel(scaledIcon);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(8, 16, 586, 105);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 =new JLabel("İstanbul Arel Üniversitesi Akademisyen Girişi");
		lblNewLabel_1.setFont(new Font("AppleGothic", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(8, 133, 588, 30);
		contentPane.add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(99, 161, 406, 12);
		contentPane.add(separator);
		
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

				JTextField academicEmail = new JTextField("Mailinizi giriniz");
				academicEmail.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						emailPanel.setBorder(new LineBorder(new Color(0, 89, 155), 2, true));
					
					}
					@Override
					public void focusLost(FocusEvent e) {
						emailPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
					}
				});
				academicEmail.setFont(new Font("Arial", academicEmail.getFont().getStyle(), academicEmail.getFont().getSize()));
				academicEmail.setBorder(null);
				academicEmail.setForeground(Color.BLACK);
				emailPanel.add(academicEmail, BorderLayout.CENTER);

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

				JTextField academicPassword = new JTextField("Şifreniz");
				academicPassword.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						passwordPanel.setBorder(new LineBorder(new Color(0, 89, 155), 2, true));
					}
					@Override
					public void focusLost(FocusEvent e) {
						passwordPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
					}
					
				});
				academicPassword.setFont(new Font("Arial", academicPassword.getFont().getStyle(), academicPassword.getFont().getSize()));
				academicPassword.setBorder(null);
				academicPassword.setForeground(Color.BLACK);
				passwordPanel.add(academicPassword, BorderLayout.CENTER);

				contentPane.add(passwordPanel);
				
				
				JButton btnNewButton = new JButton("Giriş Yap");
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
				
				        if (academicEmail.getText().isEmpty() || 
				            academicPassword.getText().isEmpty() || 
				            academicEmail.getText().equals("Mailinizi giriniz") || 
				            academicPassword.getText().equals("Şifreniz")) {
				            
				            JOptionPane.showMessageDialog(null, 
				                "Lütfen Boş Alanları Doldurunuz", 
				                "İstanbul Arel Üniversitesi", 
				                JOptionPane.WARNING_MESSAGE);
				            return;
				        }
						
						
						try {
						    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo", "root", "gelistiriciOsman_42");
						    
						    String query = "SELECT academician_id, academician_email, academician_password " +
			                           "FROM academicians WHERE academician_email = ? AND academician_password = ?";

			          
						    PreparedStatement pstmt =connection.prepareStatement(query);
						    pstmt.setString(1, academicEmail.getText());
						    pstmt.setString(2, academicPassword.getText());

						    ResultSet rs = pstmt.executeQuery();
						    
						   
						    
			

						    if (rs.next()) {
						    	 int academician_id = rs.getInt("academician_id");
				                JOptionPane.showMessageDialog(null, 
				                    "Giriş Başarılı!", 
				                    "İstanbul Arel Üniversitesi", 
				                    JOptionPane.INFORMATION_MESSAGE);
				                
				              
				                
				                AcademicianHomePage academicHomePage = new AcademicianHomePage(academician_id);
				                academicHomePage.setVisible(true);
								   JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton);
							        currentFrame.dispose();
				              
				                
				            } else {
				                JOptionPane.showMessageDialog(null, 
				                    "Email veya şifre hatalı!", 
				                    "İstanbul Arel Üniversitesi", 
				                    JOptionPane.ERROR_MESSAGE);
				            }


						    connection.close();

						}  catch (SQLException exception) {
				            JOptionPane.showMessageDialog(null, 
				                    "Veritabanı bağlantı hatası: " + exception.getMessage(), 
				                    "İstanbul Arel Üniversitesi", 
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
				
				
		

		
		
		
	}
}
