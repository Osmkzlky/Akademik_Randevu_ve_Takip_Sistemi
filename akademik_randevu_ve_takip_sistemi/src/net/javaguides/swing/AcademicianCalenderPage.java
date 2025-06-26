package net.javaguides.swing;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.awt.Font;

public class AcademicianCalenderPage extends JFrame {
    private JPanel contentPane;
    private JPanel panelRight;
    private JLabel lblSelection;
    private ArrayList<JCheckBox> checkboxList = new ArrayList<>();
    private String currentDay = "";
    private Map<String, List<Integer>> dayToSelectedIndexes = new HashMap<>();
    private int academician_id;
    private JFrame previousPage;

    private final String[] morningTimes = {
        "9.00 - 9.30", "9.30 - 10.00", "10.00 - 10.30", "10.30 - 11.00",
        "11.00 - 11.30", "11.30 - 12.00", "12.00 - 12.30"
    };

    private final String[] afternoonTimes = {
        "13.30 - 14.00", "14.00 - 14.30", "14.30 - 15.00", "15.00 - 15.30",
        "15.30 - 16.00", "16.00 - 16.30", "16.30 - 17.00"
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AcademicianHomePage homePage = new AcademicianHomePage(1);   
                AcademicianCalenderPage frame = new AcademicianCalenderPage(1, homePage);
                frame.setVisible(true);
                homePage.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public AcademicianCalenderPage(int academician_id,JFrame previousPage) {
        this.academician_id = academician_id;
        this.previousPage = previousPage;

        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/swing_demo", "root", "gelistiriciOsman_42");

            PreparedStatement pst = connection.prepareStatement(
                "SELECT calender_monday, calender_tuesday, calender_wednesday, calender_thursday, calender_friday FROM calender WHERE academician_id = ?");
            pst.setInt(1, academician_id);  

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dayToSelectedIndexes.put("Pazartesi", stringToList(rs.getString("calender_monday")));
                dayToSelectedIndexes.put("Salı", stringToList(rs.getString("calender_tuesday")));
                dayToSelectedIndexes.put("Çarşamba", stringToList(rs.getString("calender_wednesday")));
                dayToSelectedIndexes.put("Perşembe", stringToList(rs.getString("calender_thursday")));
                dayToSelectedIndexes.put("Cuma", stringToList(rs.getString("calender_friday")));
            }

            rs.close();
            pst.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veri çekilirken hata: " + ex.getMessage());
        }

    
        initComponents(); 
     
    }


    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 703, 560);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

      

        panelRight = new JPanel();
        panelRight.setBounds(16, 123, 681, 287);
        panelRight.setLayout(null);
        panelRight.setVisible(false);
        contentPane.add(panelRight);

        JLabel lblNewLabel = new JLabel("Öğleden Önce");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(139, 28, 135, 16);
        panelRight.add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(139, 44, 135, 12);
        panelRight.add(separator);

        JLabel lblledenSonra = new JLabel("Öğleden Sonra");
        lblledenSonra.setHorizontalAlignment(SwingConstants.CENTER);
        lblledenSonra.setBounds(356, 28, 135, 16);
        panelRight.add(lblledenSonra);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(356, 44, 135, 12);
        panelRight.add(separator_1);

        JButton btnKaydet = new JButton("Günü Kaydet");
        btnKaydet.setVisible(false);
        btnKaydet.setBounds(194, 240, 252, 29);
        panelRight.add(btnKaydet);
        btnKaydet.addActionListener(e -> {
            if (currentDay.isEmpty()) return;

            List<Integer> selectedIndexes = new ArrayList<>();
            for (int i = 0; i < checkboxList.size(); i++) {
                if (checkboxList.get(i).isSelected()) {
                    selectedIndexes.add(i);
                }
            }

            dayToSelectedIndexes.put(currentDay, selectedIndexes);

           
            System.out.println("[" + currentDay + "] -> " + selectedIndexes);
        });

        JLabel lblNewLabel_2 = new JLabel("Lütfen Müsait Olduğunuz Saatleri İşaretleyiniz");
        lblNewLabel_2.setFont(new Font("AppleGothic", Font.PLAIN, 15));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(16, 6, 681, 29);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_1 =  new JLabel("ⓘ Lütfen Akademik Takvimize Uygun Olarak Doldurunuz");
     
        lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(16, 510, 681, 16);
        contentPane.add(lblNewLabel_1);
        
        
        JPanel panelLeft = new JPanel();
        panelLeft.setBounds(16, 53, 681, 58);
        panelLeft.setLayout(null);
        contentPane.add(panelLeft);
        
        JButton btnNewButton = new JButton("Kaydet");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
            		
        	       try {
                       Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo", "root", "gelistiriciOsman_42");
                       String monday = listToString(dayToSelectedIndexes.get("Pazartesi"));
                       String tuesday = listToString(dayToSelectedIndexes.get("Salı"));
                       String wednesday = listToString(dayToSelectedIndexes.get("Çarşamba"));
                       String thursday = listToString(dayToSelectedIndexes.get("Perşembe"));
                       String friday = listToString(dayToSelectedIndexes.get("Cuma"));
                       

                       String query = "UPDATE calender SET calender_monday = ?, calender_tuesday = ?, calender_wednesday = ?, calender_thursday = ?, calender_friday = ? WHERE academician_id = ?";
                       
                       PreparedStatement pst = connection.prepareStatement(query);
                       pst.setString(1, monday);
                       pst.setString(2, tuesday);
                       pst.setString(3, wednesday);
                       pst.setString(4, thursday);
                       pst.setString(5, friday);
                       pst.setInt(6, academician_id);
                       
                       int result = pst.executeUpdate();
                       if (result > 0) {
                           JOptionPane.showMessageDialog(btnNewButton, "Veriler başarıyla kaydedildi.");
                       } else {
                           JOptionPane.showMessageDialog(btnNewButton, "Kayıt başarısız.");
                       }

                       connection.close();
                   }  catch (Exception ex) {
                       ex.printStackTrace();
                       JOptionPane.showMessageDialog(btnNewButton, "Hata: " + ex.getMessage());
                   }
        		
        	}
        });
        btnNewButton.setBounds(46, 438, 609, 29);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("< Geri");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 if (previousPage != null) {
        	            previousPage.setVisible(true);
        	        }
        	        dispose(); 
			        
        	}
        });
        btnNewButton_1.setBounds(16, 11, 117, 20);
        contentPane.add(btnNewButton_1);
        
        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(16, 29, 681, 12);
        contentPane.add(separator_2);

        String[] days = {"Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma"};
        int x = 22;

        for (String day : days) {
            JButton btnDay = new JButton(day);
            btnDay.setBounds(x, 19, 117, 29);
            btnDay.addActionListener(e -> {
                currentDay = day;
                renderCheckboxes();
                btnKaydet.setVisible(true);
                panelRight.setVisible(true);
                
            });
            panelLeft.add(btnDay);
            x += 129;
        }
   
       

        
    }

    private void renderCheckboxes() {
        for (JCheckBox cb : checkboxList) {
            panelRight.remove(cb);
        }
        checkboxList.clear();

        for (int i = 0; i < morningTimes.length; i++) {
            JCheckBox cb = new JCheckBox(morningTimes[i]);
            cb.setBounds(139, 56 + (i * 24), 141, 23);
            panelRight.add(cb);
            checkboxList.add(cb);
        }

        for (int i = 0; i < afternoonTimes.length; i++) {
            JCheckBox cb = new JCheckBox(afternoonTimes[i]);
            cb.setBounds(356, 52 + (i * 24), 141, 23);
            panelRight.add(cb);
            checkboxList.add(cb);
        }

        // Önceden seçili olanları geri yükle
        List<Integer> previouslySelected = dayToSelectedIndexes.get(currentDay);
        if (previouslySelected != null) {
            for (int index : previouslySelected) {
                if (index >= 0 && index < checkboxList.size()) {
                    checkboxList.get(index).setSelected(true);
                }
            }
        }

        panelRight.revalidate();
        panelRight.repaint();
    }
    private String listToString(List<Integer> list) {
        if (list == null || list.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private List<Integer> stringToList(String data) {
        List<Integer> list = new ArrayList<>();
        if (data == null || data.isEmpty()) return list;

        for (String part : data.split(",")) {
            try {
                list.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {}
        }
        return list;
    }
    
    
}
