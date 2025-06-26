package net.javaguides.swing;
import javax.swing.*;
import java.awt.EventQueue;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class IzınAlma extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField formattedTelefon;
	private String başlangıçTarih,bitişTarih;
	private JSpinner tarihSpinner1;
    private JSpinner tarihSpinner2;
    private JSpinner spinner;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IzınAlma frame = new IzınAlma();
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
	public IzınAlma() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Telefon numarası");
		lblNewLabel_2.setBounds(46, 198, 61, 16);
		contentPane.add(lblNewLabel_2);
		
		MaskFormatter mf = null;
		try {
			mf = new MaskFormatter("(0###) ### - ## - ##");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		formattedTelefon = new JFormattedTextField(mf);
		formattedTelefon.setBounds(46, 215, 206, 26);
		
		
		contentPane.add(formattedTelefon);
		
		SpinnerDateModel model1 = new SpinnerDateModel();
        tarihSpinner1 = new JSpinner(model1);
        JSpinner.DateEditor editor1 = new JSpinner.DateEditor(tarihSpinner1, "dd/MM/yyyy");
        tarihSpinner1.setEditor(editor1);
        tarihSpinner1.setBounds(46, 146, 137, 26);
        contentPane.add(tarihSpinner1);
        
        
        SpinnerDateModel model2 = new SpinnerDateModel();
        tarihSpinner2 = new JSpinner(model2);
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(tarihSpinner2, "dd/MM/yyyy");
        tarihSpinner2.setEditor(editor2);
        tarihSpinner2.setBounds(279, 146, 137, 26);
		contentPane.add(tarihSpinner2);
		
		 SpinnerDateModel model3 = new SpinnerDateModel();
		spinner = new JSpinner(model3);
		spinner.setBounds(46, 57, 237, 26);
		contentPane.add(spinner);
	}

}
