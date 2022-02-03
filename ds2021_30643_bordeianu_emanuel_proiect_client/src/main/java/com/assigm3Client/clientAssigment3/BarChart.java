package com.assigm3Client.clientAssigment3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;


public class BarChart {

    public void displayBarChartClientHourlyHistoricalEnergCons(DefaultCategoryDataset dataset, String titlu, int width,
															   int height) {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.setValue(100, "", "job 1");
//        dataset.setValue(200, "", "job 2");
		JFreeChart chart = ChartFactory.createBarChart(titlu, "Timestamp", "EnergyConsumed",
				dataset, PlotOrientation.VERTICAL, false, true, false);
		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setPaint(Color.blue);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRangeGridlinePaint(Color.GREEN);
		ChartFrame frame1 = new ChartFrame("BarChartClientHourlyHistoricalEnergCons", chart);
		frame1.setVisible(true);
		frame1.setSize(width, height);

    }

}
