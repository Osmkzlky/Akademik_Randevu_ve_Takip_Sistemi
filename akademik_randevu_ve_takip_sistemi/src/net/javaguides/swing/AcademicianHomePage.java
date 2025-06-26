package net.javaguides.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class AcademicianHomePage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int academician_id;
    private String academician_name;
    private String academician_surname;
    private String academician_title;

    

    /**
     * Launch the application for test purposes.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AcademicianHomePage frame = new AcademicianHomePage(1); // Test ID
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Constructor with academician ID.
     */
    public AcademicianHomePage(int academician_id) {
        this.academician_id = academician_id;

     

        // Veritabanından akademisyen bilgilerini al ve göster
        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/swing_demo",
                "root",
                "gelistiriciOsman_42"
            );

            String query = "SELECT academician_name, academician_surname,academician_title FROM academicians WHERE academician_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, academician_id);
            

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                academician_name = rs.getString("academician_name");
                academician_surname = rs.getString("academician_surname");
                academician_title = rs.getString("academician_title");
               }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents(); 
    }

    /**
     * GUI komponentlerini oluşturur.
     */
    private void initComponents() {
    	setTitle("İstanbul Arel Üniverisitesi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 552, 461);
        contentPane = new JPanel();
        
        setContentPane(contentPane);
        contentPane.setLayout(null);
        

		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/arelLogo.png"));

		Image scaledImage = originalIcon.getImage().getScaledInstance(105, 105, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel lblNewLabel_1 = new JLabel(scaledIcon);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(6, 16, 540, 105);
		contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel = new JLabel(academician_title +" "+ academician_name +" "+ academician_surname);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(6, 133, 540, 16);
        contentPane.add(lblNewLabel);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(6, 152, 540, 12);
        contentPane.add(separator);
        
        JButton btnMsaitSaatler = new JButton("Müsait Saatler");
        btnMsaitSaatler.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
        btnMsaitSaatler.setForeground(Color.WHITE);
        btnMsaitSaatler.setBackground(new Color(0, 87, 154));
        btnMsaitSaatler.setContentAreaFilled(false);
        btnMsaitSaatler.setOpaque(true);
        btnMsaitSaatler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnMsaitSaatler.setBackground(new Color(255, 255, 255));
				btnMsaitSaatler.setForeground(new Color(0, 87, 154));
			
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			
		        btnMsaitSaatler.setForeground(Color.WHITE);
		        btnMsaitSaatler.setBackground(new Color(0, 87, 154));
			}
		});
  	  btnMsaitSaatler.setBorder(new LineBorder(new Color(0, 87, 154), 2));
        btnMsaitSaatler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AcademicianCalenderPage academicianCalenderPage = new AcademicianCalenderPage(academician_id, AcademicianHomePage.this);
                academicianCalenderPage.setVisible(true);
                setVisible(false); 
            }
        });
        btnMsaitSaatler.setBounds(154, 176, 237, 35);
        contentPane.add(btnMsaitSaatler);
        
        JButton btnNewButton_1_1 = new JButton("Randevular");
        btnNewButton_1_1.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
        btnNewButton_1_1.setForeground(Color.WHITE);
        btnNewButton_1_1.setBackground(new Color(0, 87, 154));
        btnNewButton_1_1.setContentAreaFilled(false);
        btnNewButton_1_1.setOpaque(true);
        btnNewButton_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_1_1.setBackground(new Color(255, 255, 255));
				btnNewButton_1_1.setForeground(new Color(0, 87, 154));
			
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			
				btnNewButton_1_1.setForeground(Color.WHITE);
				btnNewButton_1_1.setBackground(new Color(0, 87, 154));
			}
		});
        btnNewButton_1_1.setBorder(new LineBorder(new Color(0, 87, 154), 2));
        btnNewButton_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AppointmentList appointmetnList =new AppointmentList(academician_id);
        		appointmetnList.setVisible(true);
        		JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton_1_1);
		        currentFrame.dispose();
        	}
        });
        btnNewButton_1_1.setBounds(154, 223, 237, 35);
        contentPane.add(btnNewButton_1_1);
        
        JButton btnNewButton_1_1_1 = new JButton("Mesaj Gönder");
        btnNewButton_1_1_1.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
        btnNewButton_1_1_1.setForeground(Color.WHITE);
        btnNewButton_1_1_1.setBackground(new Color(0, 87, 154));
        btnNewButton_1_1_1.setContentAreaFilled(false);
        btnNewButton_1_1_1.setOpaque(true);
        btnNewButton_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_1_1_1.setBackground(new Color(255, 255, 255));
				btnNewButton_1_1_1.setForeground(new Color(0, 87, 154));
			
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			
				btnNewButton_1_1_1.setForeground(Color.WHITE);
				btnNewButton_1_1_1.setBackground(new Color(0, 87, 154));
			}
		});
        btnNewButton_1_1_1.setBorder(new LineBorder(new Color(0, 87, 154), 2));
        btnNewButton_1_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AcademicianMessageSendingPage academicianMessageSendingPage =new AcademicianMessageSendingPage(academician_id);
        		academicianMessageSendingPage.setVisible(true);
        		JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton_1_1_1);
		        currentFrame.dispose();
        	}
        });
        btnNewButton_1_1_1.setBounds(154, 270, 237, 35);
        contentPane.add(btnNewButton_1_1_1);
        
        JButton btnNewButton_1_1_1_1 = new JButton("Gelen Kutusu");
        btnNewButton_1_1_1_1.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
        btnNewButton_1_1_1_1.setForeground(Color.WHITE);
        btnNewButton_1_1_1_1.setBackground(new Color(0, 87, 154));
        btnNewButton_1_1_1_1.setContentAreaFilled(false);
        btnNewButton_1_1_1_1.setOpaque(true);
        btnNewButton_1_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_1_1_1_1.setBackground(new Color(255, 255, 255));
				btnNewButton_1_1_1_1.setForeground(new Color(0, 87, 154));
			
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			
				btnNewButton_1_1_1_1.setForeground(Color.WHITE);
				btnNewButton_1_1_1_1.setBackground(new Color(0, 87, 154));
			}
		});
        btnNewButton_1_1_1_1.setBorder(new LineBorder(new Color(0, 87, 154), 2));
        btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AcademicianInbox academicianInbox =new AcademicianInbox(academician_id);
        		academicianInbox.setVisible(true);
        		JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton_1_1_1_1);
		        currentFrame.dispose();
        	}
        });
        btnNewButton_1_1_1_1.setBounds(154, 317, 237, 35);
        contentPane.add(btnNewButton_1_1_1_1);
    }
}