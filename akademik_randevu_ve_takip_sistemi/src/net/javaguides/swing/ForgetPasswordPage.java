package net.javaguides.swing;

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
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.Icon;

public class ForgetPasswordPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgetPasswordPage frame = new ForgetPasswordPage();
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
	public ForgetPasswordPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/forgetPasswordImage.jpg"));

		Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel lblNewLabel = new JLabel(scaledIcon);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 6, 436, 155);
		contentPane.add(lblNewLabel);
		

		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 176, 428, 12);
		contentPane.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Şifre Yenileme");
		lblNewLabel_1.setFont(new Font("AppleGothic", Font.BOLD, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(6, 155, 428, 33);
		contentPane.add(lblNewLabel_1);
		
				ImageIcon userIcon = new ImageIcon(getClass().getResource("/images/userIcon.png"));
				Image scaledUserImage = userIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon scaledUserIcon = new ImageIcon(scaledUserImage);

				JPanel studentNumberPanel = new JPanel(new BorderLayout(5, 0));
				studentNumberPanel.setBounds(100, 230, 252, 30);
				studentNumberPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
				studentNumberPanel.setBackground(Color.WHITE); 

				JLabel emailIconLabel = new JLabel(scaledUserIcon);
				emailIconLabel.setBorder(new EmptyBorder(0, 5, 0, 5)); 
				studentNumberPanel.add(emailIconLabel, BorderLayout.WEST);

				JTextField studentNumberField = new JTextField("Öğrenci Numaranızı Giriniz");
				studentNumberField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						studentNumberPanel.setBorder(new LineBorder(new Color(0, 89, 155), 2, true));
					
					}
					@Override
					public void focusLost(FocusEvent e) {
						studentNumberPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
					}
				});
				studentNumberField.setFont(new Font("Arial", studentNumberField.getFont().getStyle(), studentNumberField.getFont().getSize()));
				studentNumberField.setBorder(null);
				studentNumberField.setForeground(Color.BLACK);
				studentNumberPanel.add(studentNumberField, BorderLayout.CENTER);

				contentPane.add(studentNumberPanel);

				ImageIcon passwordIcon = new ImageIcon(getClass().getResource("/images/birthday.png"));
				Image scaledPasswordImage = passwordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon scaledPasswordIcon = new ImageIcon(scaledPasswordImage);

				JPanel studentBirthdayPanel = new JPanel(new BorderLayout(5, 0));
				studentBirthdayPanel.setBounds(100, 270, 252, 30);
				studentBirthdayPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
				studentBirthdayPanel.setBackground(Color.WHITE);

				JLabel passwordIconLabel = new JLabel(scaledPasswordIcon);
				passwordIconLabel.setBorder(new EmptyBorder(0, 5, 0, 5));
				studentBirthdayPanel.add(passwordIconLabel, BorderLayout.WEST);
				
				MaskFormatter dateFormatter = null;
				try {
				    dateFormatter = new MaskFormatter("##/##/####"); 
				    dateFormatter.setPlaceholderCharacter('_');
				} catch (ParseException e) {
				    e.printStackTrace();
				   
				}

				JFormattedTextField studentBirthdayField = new JFormattedTextField(dateFormatter);
				studentBirthdayField.setColumns(10);
				studentBirthdayField.setText("01/01/2001");
				

				studentBirthdayField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						studentBirthdayPanel.setBorder(new LineBorder(new Color(0, 89, 155), 2, true));
					}
					@Override
					public void focusLost(FocusEvent e) {
						studentBirthdayPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
					}
					
				});
				studentBirthdayField.setFont(new Font("Arial", studentBirthdayField.getFont().getStyle(), studentBirthdayField.getFont().getSize()));
				studentBirthdayField.setBorder(null);
				studentBirthdayField.setForeground(Color.BLACK);
				studentBirthdayPanel.add(studentBirthdayField, BorderLayout.CENTER);

				contentPane.add(studentBirthdayPanel);
				
				
				
				ImageIcon userIcon1 = new ImageIcon(getClass().getResource("/images/mail.png"));
				Image scaledUserImage1 = userIcon1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon scaledUserIcon1 = new ImageIcon(scaledUserImage1);

				JPanel emailPanel = new JPanel(new BorderLayout(5, 0));
				emailPanel.setBounds(100, 310, 252, 30);
				emailPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
				emailPanel.setBackground(Color.WHITE); 

				JLabel emailIconLabel1 = new JLabel(scaledUserIcon1);
				emailIconLabel1.setBorder(new EmptyBorder(0, 5, 0, 5)); 
				emailPanel.add(emailIconLabel1, BorderLayout.WEST);

				JTextField studentEmailField = new JTextField("Mailinizi giriniz");
				studentEmailField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						emailPanel.setBorder(new LineBorder(new Color(0, 89, 155), 2, true));
					
					}
					@Override
					public void focusLost(FocusEvent e) {
						emailPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
					}
				});
				studentEmailField.setFont(new Font("Arial", studentEmailField.getFont().getStyle(), studentEmailField.getFont().getSize()));
				studentEmailField.setBorder(null);
				studentEmailField.setForeground(Color.BLACK);
				emailPanel.add(studentEmailField, BorderLayout.CENTER);

				contentPane.add(emailPanel);

				// Şifre Paneli
				ImageIcon passwordIcon1 = new ImageIcon(getClass().getResource("/images/passwordIcon2.png"));
				Image scaledPasswordImage1 = passwordIcon1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon scaledPasswordIcon1 = new ImageIcon(scaledPasswordImage1);

				JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
				passwordPanel.setBounds(100, 390, 252, 30);
				passwordPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
				passwordPanel.setBackground(Color.WHITE);

				JLabel passwordIconLabel1 = new JLabel(scaledPasswordIcon1);
				passwordIconLabel1.setBorder(new EmptyBorder(0, 5, 0, 5));
				passwordPanel.add(passwordIconLabel1, BorderLayout.WEST);

				JTextField studentPasswordField = new JTextField("Yeni Şifre Oluşturunuz");
				studentPasswordField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						passwordPanel.setBorder(new LineBorder(new Color(0, 89, 155), 2, true));
					}
					@Override
					public void focusLost(FocusEvent e) {
						passwordPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
					}
					
				});
				studentPasswordField.setFont(new Font("Arial", studentPasswordField.getFont().getStyle(), studentPasswordField.getFont().getSize()));
				studentPasswordField.setBorder(null);
				studentPasswordField.setForeground(Color.BLACK);
				passwordPanel.add(studentPasswordField, BorderLayout.CENTER);

				contentPane.add(passwordPanel);
				
			
				ImageIcon passwordIcon2 = new ImageIcon(getClass().getResource("/images/passwordIcon2.png"));
				Image scaledPasswordImage2 = passwordIcon2.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon scaledPasswordIcon2 = new ImageIcon(scaledPasswordImage2);

				JPanel passwordAgainPanel = new JPanel(new BorderLayout(5, 0));
				passwordAgainPanel.setBounds(100, 430, 252, 30);
				passwordAgainPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
				passwordAgainPanel.setBackground(Color.WHITE);

				JLabel passwordIconLabel2 = new JLabel(scaledPasswordIcon2);
				passwordIconLabel2.setBorder(new EmptyBorder(0, 5, 0, 5));
				passwordAgainPanel.add(passwordIconLabel2, BorderLayout.WEST);

				JTextField studentPasswordAgainField = new JTextField("Şifrenizi Tekrar Giriniz");
				studentPasswordAgainField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						passwordAgainPanel.setBorder(new LineBorder(new Color(0, 89, 155), 2, true));
					}
					@Override
					public void focusLost(FocusEvent e) {
						passwordAgainPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
					}
					
				});
				studentPasswordAgainField.setFont(new Font("Arial", studentPasswordAgainField.getFont().getStyle(), studentPasswordAgainField.getFont().getSize()));
				studentPasswordAgainField.setBorder(null);
				studentPasswordAgainField.setForeground(Color.BLACK);
				passwordAgainPanel.add(studentPasswordAgainField, BorderLayout.CENTER);

				contentPane.add(passwordAgainPanel);
				
				
				JButton btnNewButton = new JButton("Giriş Yap");
				btnNewButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						btnNewButton.setBackground(new Color(220, 230, 245)); 
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
						
						if (studentPasswordField.getText().length() < 6) {
						    JOptionPane.showMessageDialog(null,
						        "Şifre en az 6 karakter uzunluğunda olmalıdır.",
						        "İstanbul Arel Üniversitesi",
						        JOptionPane.ERROR_MESSAGE);
						    return;
						}
				
						
						try {
						    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo", "root", "gelistiriciOsman_42");

						    String query ="SELECT student_number, student_birthday, student_email FROM students WHERE student_number = ? AND student_birthday = ? AND student_email = ?";
						    
						    PreparedStatement pstmt =connection.prepareStatement(query);
						    pstmt.setString(1, studentNumberField.getText());
						    pstmt.setString(2, studentBirthdayField.getText());
						    pstmt.setString(3, studentEmailField.getText());

						    ResultSet rs = pstmt.executeQuery();
						    
						  
			

						    if (rs.next()) {
						    	 if (studentPasswordField.getText().equals(studentPasswordAgainField.getText())) {
						          
						             String updateQuery = "UPDATE students SET student_password = ? WHERE student_number = ?";
						             PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
						             updateStmt.setString(1, studentPasswordField.getText()); 
						             updateStmt.setString(2, studentNumberField.getText());

						             int updatedRows = updateStmt.executeUpdate();
						             
						             

						             if (updatedRows > 0) {
						                 JOptionPane.showMessageDialog(null, 
						                     "Şifre başarıyla güncellendi.", 
						                     "İstanbul Arel Üniversitesi", 
						                     JOptionPane.INFORMATION_MESSAGE);
						                 StudentLoginPage studentLoginPage = new StudentLoginPage();
						                 studentLoginPage.setVisible(true);
										   JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton);
									        currentFrame.dispose();
						             } else {
						                 JOptionPane.showMessageDialog(null, 
						                     "Şifre güncellenemedi.", 
						                     "İstanbul Arel Üniversitesi", 
						                     JOptionPane.ERROR_MESSAGE);
						             }

						         } else {
						             JOptionPane.showMessageDialog(null, 
						                 "Şifreler Eşleşmiyor", 
						                 "İstanbul Arel Üniversitesi", 
						                 JOptionPane.ERROR_MESSAGE);
						         }
						     } else {
				                JOptionPane.showMessageDialog(null, 
				                    "Bu Bilgilere Ait Öğrenci Bulunamadı!", 
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
				btnNewButton.setBounds(100, 480, 252, 30);
				contentPane.add(btnNewButton);
				
				JLabel lblNewLabel_2 = new JLabel("Kişisel Bilgiler");
				lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel_2.setBounds(6, 200, 428, 16);
				contentPane.add(lblNewLabel_2);
				
				JSeparator separator_1 = new JSeparator();
				separator_1.setBounds(79, 216, 302, 12);
				contentPane.add(separator_1);
				
				JLabel lblNewLabel_2_1 = new JLabel("Sistem Bilgileri");
				lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel_2_1.setBounds(6, 355, 428, 16);
				contentPane.add(lblNewLabel_2_1);
				
				JSeparator separator_1_1 = new JSeparator();
				separator_1_1.setBounds(79, 370, 297, 12);
				contentPane.add(separator_1_1);
				

				
				
				
	}
}
