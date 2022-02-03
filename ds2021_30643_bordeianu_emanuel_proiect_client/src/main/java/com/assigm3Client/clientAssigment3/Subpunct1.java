package com.assigm3Client.clientAssigment3;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.toedter.calendar.JDateChooser;

public class Subpunct1 {

	private JFrame frame;
	private JTextField textField_select_nr_zile;
	private JLabel label1_nimic_selectat_la_calendar;
	private JLabel label1_nimic_selectat_la_nr_zile;
	private JDateChooser dateChooser;
	private MeasureValue measureValue;
	private Long idClient;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//new Subpunct1(idClient);
					//Subpunct1 window = new Subpunct1();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Subpunct1(Long idClient) {
		initialize(idClient);
		frame.setVisible(true);
	}

	private void initialize(Long idClient) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ClientAssigment3Application.class);
		measureValue = ctx.getBean(MeasureValue.class);
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 496, 330);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label1_Selectare_data_incepere = new JLabel("Selectare_data_incepere");
		label1_Selectare_data_incepere.setHorizontalAlignment(SwingConstants.CENTER);
		label1_Selectare_data_incepere.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label1_Selectare_data_incepere.setBounds(24, 23, 206, 30);
		frame.getContentPane().add(label1_Selectare_data_incepere);
		
		textField_select_nr_zile = new JTextField();
		textField_select_nr_zile.setBounds(24, 135, 206, 30);
		frame.getContentPane().add(textField_select_nr_zile);
		textField_select_nr_zile.setColumns(10);
		
		JLabel label1_Selectare_numar_zile = new JLabel("Selectare_numar_zile");
		label1_Selectare_numar_zile.setHorizontalAlignment(SwingConstants.CENTER);
		label1_Selectare_numar_zile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label1_Selectare_numar_zile.setBounds(24, 105, 206, 30);
		frame.getContentPane().add(label1_Selectare_numar_zile);
		
		label1_nimic_selectat_la_calendar = new JLabel("Nimic_selectat_la_calendar");
		label1_nimic_selectat_la_calendar.setHorizontalAlignment(SwingConstants.CENTER);
		label1_nimic_selectat_la_calendar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label1_nimic_selectat_la_calendar.setBounds(236, 63, 206, 32);
		frame.getContentPane().add(label1_nimic_selectat_la_calendar);
		
		label1_nimic_selectat_la_nr_zile = new JLabel("Nimic_selectat_la_nr_ore");
		label1_nimic_selectat_la_nr_zile.setHorizontalAlignment(SwingConstants.CENTER);
		label1_nimic_selectat_la_nr_zile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label1_nimic_selectat_la_nr_zile.setBounds(240, 135, 191, 30);
		frame.getContentPane().add(label1_nimic_selectat_la_nr_zile);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(24, 63, 206, 32);
		frame.getContentPane().add(dateChooser);
		
		JButton btnSubmitCalendarAndNrZile = new JButton("SubmitCalendarAndNrZile");
		btnSubmitCalendarAndNrZile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateChooser.setDate(dateChooser.getDate());
				System.out.println("dateChooser = " + dateChooser.getDate());
				
				textField_select_nr_zile.setText(textField_select_nr_zile.getText());
				if( (dateChooser.getDate() == null) || (textField_select_nr_zile.getText().equals("") ) ) {
					JOptionPane.showMessageDialog(null, "Numarul de zile sau data nu a fost ales/aleasa");
				}
				else {
					System.out.println("textField_select_nr_zile = " + textField_select_nr_zile.getText());
					Double[][] measurementsDTODouble = measureValue.getHourlyEnergConsSubpunct1(idClient, dateChooser.getDate(), 
													Integer.parseInt(textField_select_nr_zile.getText()));
					label1_nimic_selectat_la_nr_zile.setText("Ceva selectat la nr ore");
					
					XYSeriesCollection datasettt = new XYSeriesCollection();
					XYSeries[] xySeries = new XYSeries[Integer.parseInt(textField_select_nr_zile.getText())];
					//XYPlot plot = chart.getXYPlot();
					XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

					System.out.println("Subpunct1_din_meniu");
					for(int i = 0; i<Integer.parseInt(textField_select_nr_zile.getText()); i++) {
						xySeries[i] = new XYSeries("Object " + (i+1));
						for(int j = 0; j<24; j++) {
							double aux = measurementsDTODouble[j][i];
							xySeries[i].add(j, aux);
							System.out.println("measurementsDTODouble["+j+"]["+i+"] = " + measurementsDTODouble[j][i]);
							//datasetSubpunct1.setValue(measurementsDTODouble[j][i], "", "ora = "+ String.valueOf(j));
						}
						datasettt.addSeries(xySeries[i]);
					}

					new LineChartt("Subpunct1", "XAxis", "YAxis",
							datasettt, xySeries, Integer.parseInt(textField_select_nr_zile.getText()),
							renderer.getPlot(), renderer).setVisible(true);
				}

				
			}
		});
		btnSubmitCalendarAndNrZile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSubmitCalendarAndNrZile.setBounds(107, 201, 248, 49);
		frame.getContentPane().add(btnSubmitCalendarAndNrZile);


		ctx.close();
		//measureValue = SpringApplication.exit (0).getBean(MeasureValue.class);
	}
	
	
}

