package com.assigm3Client.clientAssigment3;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LineChartt extends JFrame{

	public LineChartt(String chartTitle, String xAxisLabel, String yAxisLabel,
					  XYSeriesCollection datasettt, XYSeries[] xySeries, Integer lungime,
					  XYPlot plot, XYLineAndShapeRenderer renderer){
		super("XY Line Chart with JFreechart");

		JPanel chartPanel = createChartPanel(chartTitle, xAxisLabel, yAxisLabel, datasettt, xySeries, lungime,
				plot, renderer);
		//chartPanel.add(BorderLayout.CENTER);
		add(chartPanel, BorderLayout.CENTER);

		setSize(640, 480);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private JPanel createChartPanel(String chartTitle, String xAxisLabel, String yAxisLabel,
									XYSeriesCollection datasettt, XYSeries[] xySeries, Integer lungime,
									XYPlot plot, XYLineAndShapeRenderer renderer) {
		//chartTitle = "Objects Movement Chart";
		//xAxisLabel = "X";
		//yAxisLabel = "Y";

		//XYSeriesCollection datasettt = new XYSeriesCollection();
		//Integer lungime = 3;
		//XYSeries[] xySeries = new XYSeries[lungime];
		XYDataset dataset = createDataset(datasettt, xySeries,  lungime);

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset,
				PlotOrientation.VERTICAL, false, true, false);
		//ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
		plot = chart.getXYPlot();

		customizeChart(chart, plot, renderer, lungime);

		return new ChartPanel(chart);
	}

	private XYDataset createDataset(XYSeriesCollection dataset, XYSeries[] xySeries, Integer lungime) {
		//dataset = new XYSeriesCollection();

//		for(int i = 0; i<lungime; i++){
//			xySeries[i] = new XYSeries("Object " + (i+1));
//		}

//		XYSeries series1 = new XYSeries("Object 1");
//		XYSeries series2 = new XYSeries("Object 2");
//		XYSeries series3 = new XYSeries("Object 3");

//		xySeries[0].add(1.0, 2.0);
//		xySeries[0].add(2.0, 3.0);
//		xySeries[0].add(3.0, 2.5);
//		xySeries[0].add(3.5, 2.8);
//		xySeries[0].add(4.2, 6.0);
//
//		xySeries[1].add(2.0, 1.0);
//		xySeries[1].add(2.5, 2.4);
//		xySeries[1].add(3.2, 1.2);
//		xySeries[1].add(3.9, 2.8);
//		xySeries[1].add(4.6, 3.0);
//
//		xySeries[2].add(1.2, 4.0);
//		xySeries[2].add(2.5, 4.4);
//		xySeries[2].add(3.8, 4.2);
//		xySeries[2].add(4.3, 3.8);
//		xySeries[2].add(4.5, 4.0);
//
//		for(int i =0 ; i<lungime; i++){
//			dataset.addSeries(xySeries[i]);
//		}

//		dataset.addSeries(series1);
//		dataset.addSeries(series2);
//		dataset.addSeries(series3);

		return dataset;
	}

	private void customizeChart(JFreeChart chart, XYPlot plot, XYLineAndShapeRenderer renderer, Integer lungime) {
		//XYPlot plot = chart.getXYPlot();
		//XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		Random randomm = new Random();
		// sets paint color for each series
		for(int i = 0; i<lungime; i++){
			renderer.setSeriesPaint(i, new Color(randomm.nextInt(255), randomm.nextInt(255),
					randomm.nextInt(255)));
			renderer.setSeriesStroke(i, new BasicStroke(lungime-i));
		}
//		renderer.setSeriesPaint(0, Color.RED);
//		renderer.setSeriesPaint(1, Color.GREEN);
//		renderer.setSeriesPaint(2, Color.YELLOW);

		// sets thickness for series (using strokes)
//		renderer.setSeriesStroke(0, new BasicStroke(4.0f));
//		renderer.setSeriesStroke(1, new BasicStroke(3.0f));
//		renderer.setSeriesStroke(2, new BasicStroke(2.0f));

		// sets paint color for plot outlines
		plot.setOutlinePaint(Color.BLUE);
		plot.setOutlineStroke(new BasicStroke(2.0f));

		// sets renderer for lines
		plot.setRenderer(renderer);

		// sets plot background
		plot.setBackgroundPaint(Color.GRAY);

		// sets paint color for the grid lines
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

	}

	public static void main(String[] args) {
		XYSeriesCollection datasettt = new XYSeriesCollection();
		Integer lungime = 3;
		XYSeries[] xySeries = new XYSeries[lungime];
		//XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		new LineChartt("TitluChart", "XAxis", "YAxis",
				datasettt, xySeries, lungime, renderer.getPlot(), renderer).setVisible(true);
	}


}
