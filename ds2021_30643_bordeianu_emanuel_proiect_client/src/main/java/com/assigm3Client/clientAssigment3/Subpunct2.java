package com.assigm3Client.clientAssigment3;

import java.awt.EventQueue;
import javax.swing.JFrame;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.boot.SpringApplication;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class Subpunct2 {

	private JFrame frame;
	private MeasureValue measureValue;
	private Long idClient;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//new Subpunct2(idClient);
//					Subpunct2 window = new Subpunct2();
//					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Subpunct2(Long idClient) {
		initialize(idClient);
		frame.setVisible(true);
	}

	
	private void initialize(Long idClient) {
		//measureValue = SpringApplication.run(ClientAssigment3Application.class).getBean(MeasureValue.class);
		ConfigurableApplicationContext ctx = SpringApplication.run(ClientAssigment3Application.class);
		measureValue = ctx.getBean(MeasureValue.class);

		frame = new JFrame();
		frame.setBounds(100, 100, 414, 217);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btn_Get_Averaged_Energy_Consumption = new JButton("Get_Averaged_Energy_Consumption");
		btn_Get_Averaged_Energy_Consumption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Double[] averageMeasurementsOverWeek = measureValue.getAverageEnergConsSubpunct2(idClient);
				System.out.println("Subpunct2 din meniu");
				DefaultCategoryDataset datasetSubpunct2 = new DefaultCategoryDataset();
				for(int i =0; i<24; i++) {
					datasetSubpunct2.setValue(averageMeasurementsOverWeek[i], "", String.valueOf(i));
					System.out.println("averageMeasurementsOverWeek subpunct2["+i+"] = " + averageMeasurementsOverWeek[i]);
				}
				
//				BarChart barChartSubpunct2 = new BarChart();
//				barChartSubpunct2.displayBarChartClientHourlyHistoricalEnergCons(datasetSubpunct2, "SUBPUNCT_2",
//						600, 500);
				
				
				XYSeriesCollection datasettt = new XYSeriesCollection();
				XYSeries[] xySeries = new XYSeries[1];
				xySeries[0] = new XYSeries("Object " + 1);
				XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

				//System.out.println("Subpunct1_din_meniu");
				
				for(int j = 0; j<24; j++) {
					double aux = averageMeasurementsOverWeek[j];
					xySeries[0].add(j, aux);
					System.out.println("averageMeasurementsOverWeek["+j+"] = " + averageMeasurementsOverWeek[j]);
				}
				datasettt.addSeries(xySeries[0]);
				

				new LineChartt("Subpunct2", "XAxis", "YAxis",
						datasettt, xySeries, 1, renderer.getPlot(), renderer).setVisible(true);
				
			}
		});
		btn_Get_Averaged_Energy_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Get_Averaged_Energy_Consumption.setBounds(34, 40, 344, 111);
		frame.getContentPane().add(btn_Get_Averaged_Energy_Consumption);


		ctx.close();
	}
	
}

