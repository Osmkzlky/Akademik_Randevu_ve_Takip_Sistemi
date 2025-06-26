package net.javaguides.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.LineBorder;


public class DatePage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private ArrayList<JRadioButton> radioButtonList = new ArrayList<>();
    private ButtonGroup buttonGroup = new ButtonGroup();
    private String calender_monday;
    private String calender_tuesday;
    private String calender_wednesday;
    private String calender_thursday;
    private String calender_friday;
    private JSpinner tarihSpinner;
    private int academician_id;
    private int student_id;

    private final String[] times = {
        "9.00 - 9.30", "9.30 - 10.00", "10.00 - 10.30", "10.30 - 11.00",
        "11.00 - 11.30", "11.30 - 12.00", "12.00 - 12.30", "13.30 - 14.00", 
        "14.00 - 14.30", "14.30 - 15.00", "15.00 - 15.30",
        "15.30 - 16.00", "16.00 - 16.30", "16.30 - 17.00"
    };
    private JButton btnNewButton;
    private JLabel lblNewLabel_2;
    private JButton btnNewButton_1;
    private JTextField textField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DatePage frame = new DatePage(1,1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public DatePage(int academician_id,int student_id) {
        this.academician_id = academician_id;
        this.student_id=student_id;

     

        // Veritabanından akademisyen bilgilerini al ve göster
        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/swing_demo",
                "root",
                "gelistiriciOsman_42"
            );

            String query = "SELECT calender_monday, calender_tuesday, calender_wednesday, calender_thursday,calender_friday FROM calender WHERE academician_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, academician_id);
            

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	 calender_monday = rs.getString("calender_monday");
                 calender_tuesday = rs.getString("calender_tuesday");
                 calender_wednesday = rs.getString("calender_wednesday");
                 calender_thursday = rs.getString("calender_thursday");
                 calender_friday = rs.getString("calender_friday");
               }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents(); 
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 546, 527);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
      
        tarihSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(tarihSpinner, "dd/MM/yyyy");
        tarihSpinner.setEditor(editor);
        tarihSpinner.setBounds(227, 34, 104, 26);
        contentPane.add(tarihSpinner);

     

        lblNewLabel = new JLabel("Lütfen Tarih Seçiniz");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(6, 16, 534, 16);
        contentPane.add(lblNewLabel);

        lblNewLabel_1 = new JLabel("Müsait olan bir zaman aralığı seçiniz");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(6, 92, 534, 16);
        contentPane.add(lblNewLabel_1);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(6, 110, 540, 12);
        contentPane.add(separator);
        
        
        JLabel lblNewLabel_3 = new JLabel("Görüşme Konusu:");
        lblNewLabel_3.setBounds(6, 368, 131, 16);
        contentPane.add(lblNewLabel_3);
        
        textField = new JTextField();
        textField.setBounds(0, 388, 540, 26);
        contentPane.add(textField);
        textField.setColumns(10);
        
        btnNewButton = new JButton("Görüşme Talep Et");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date selectedDate = (Date) tarihSpinner.getValue();

                String selectedTime = null;
                for (JRadioButton rb : radioButtonList) {
                    if (rb.isSelected()) {
                        selectedTime = rb.getText();
                        break;
                    }
                }

                if (selectedTime == null) {
                    JOptionPane.showMessageDialog(null, "Lütfen bir saat seçiniz.");
                    return;
                }

                String formattedDate = new java.text.SimpleDateFormat("dd/MM/yyyy").format(selectedDate);
                if( textField.getText().isEmpty()) {
                	 JOptionPane.showMessageDialog(btnNewButton, "Lütfen görüşme konusu yazınız");
                	 return;
                }
               

                try (Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/swing_demo", "root", "gelistiriciOsman_42")) {

                	  String query = "INSERT INTO appointments (student_id ,academician_id, appointment_day, appointment_time,appointment_title) VALUES (?, ?, ?,?,?)";
                           try (PreparedStatement pst = connection.prepareStatement(query)) {
                        pst.setInt(1, student_id);
                        pst.setInt(2, academician_id);
                        pst.setString(3, formattedDate);
                        pst.setString(4, selectedTime);
                        pst.setString(5, textField.getText());
                       

                        int result = pst.executeUpdate();
                        if (result > 0) {
                            JOptionPane.showMessageDialog(btnNewButton, "Randevu talebi İletildi. Randevunun durumunu Randevu Göster sayfasından takip edebilirsiniz");
    
                            StudentHomePage studentHomePage = new StudentHomePage(student_id);
                            studentHomePage.setVisible(true);
           	                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton);
           			        currentFrame.dispose();
                            
                        } else {
                            JOptionPane.showMessageDialog(btnNewButton, "Güncellenecek kayıt bulunamadı. Kayıt eklemek ister misiniz?");
                           
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(btnNewButton, "Veritabanı hatası: " + ex.getMessage());
                }
            }
        });
        btnNewButton.setBounds(191, 426, 178, 29);
        contentPane.add(btnNewButton);
        
        lblNewLabel_2 = new JLabel("ⓘ Seçebileceğiniz zaman aralıkları koyu renk ile gösterilmektedir");
        lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        lblNewLabel_2.setBounds(6, 477, 534, 16);
        contentPane.add(lblNewLabel_2);
        
        btnNewButton_1 = new JButton("< Geri");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 CreateAppointment createAppointment = new CreateAppointment(student_id);
        		 createAppointment.setVisible(true);
	                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnNewButton_1);
			        currentFrame.dispose();
        	}
        });
        btnNewButton_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
        btnNewButton_1.setBackground(Color.WHITE);
        btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        btnNewButton_1.setBounds(6, 4, 75, 20);
        contentPane.add(btnNewButton_1);
       

        tarihSpinner.addChangeListener(e -> {
            Date startDate = (Date) tarihSpinner.getValue();
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
   
            

            renderCheckboxes(startDate);
        });

        renderCheckboxes(new Date());
    }

    private void renderCheckboxes(Date date) {
        // Önceki radio button'ları kaldır
        for (JRadioButton rb : radioButtonList) {
            contentPane.remove(rb);
            buttonGroup.remove(rb);
        }
        radioButtonList.clear();

        // Gün bilgisi
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        Set<Integer> availableIndexes = new HashSet<>();

        if (dayOfWeek == Calendar.MONDAY && calender_monday != null) {
            for (String s : calender_monday.split(",")) availableIndexes.add(Integer.parseInt(s));
        } else if (dayOfWeek == Calendar.TUESDAY && calender_tuesday != null) {
            for (String s : calender_tuesday.split(",")) availableIndexes.add(Integer.parseInt(s));
        } else if (dayOfWeek == Calendar.WEDNESDAY && calender_wednesday != null) {
            for (String s : calender_wednesday.split(",")) availableIndexes.add(Integer.parseInt(s));
        } else if (dayOfWeek == Calendar.THURSDAY && calender_thursday != null) {
            for (String s : calender_thursday.split(",")) availableIndexes.add(Integer.parseInt(s));
        } else if (dayOfWeek == Calendar.FRIDAY && calender_friday != null) {
            for (String s : calender_friday.split(",")) availableIndexes.add(Integer.parseInt(s));
        }

        boolean isToday = isSameDay(new Date(), date);
        Calendar now = Calendar.getInstance();

        for (int i = 0; i < times.length; i++) {
            JRadioButton rb = new JRadioButton(times[i]);

           
            if (i < 7) {
                rb.setBounds(80, 130 + i * 30, 200, 23);
            } else {
                rb.setBounds(330, 130 + (i - 7) * 30, 200, 23);
            }

            // Bugünün geçmiş saatleri pasif
            if (isToday) {
                Calendar saatCal = Calendar.getInstance();
                saatCal.setTime(date);

                try {
                    String[] saatAralik = times[i].split(" - ")[0].split("\\.");
                    int saat = Integer.parseInt(saatAralik[0]);
                    int dakika = Integer.parseInt(saatAralik[1]);
                    saatCal.set(Calendar.HOUR_OF_DAY, saat);
                    saatCal.set(Calendar.MINUTE, dakika);
                    saatCal.set(Calendar.SECOND, 0);

                    if (saatCal.before(now)) {
                        rb.setEnabled(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (!availableIndexes.contains(i)) {
                rb.setEnabled(false);
            }

            contentPane.add(rb);
            radioButtonList.add(rb);
            buttonGroup.add(rb); // gruba ekle
        }

        contentPane.revalidate();
        contentPane.repaint();
    }
  
    private boolean isSameDay(Date d1, Date d2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(d1);
        cal2.setTime(d2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}