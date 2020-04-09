package core;

import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
public class ProgressChart {
	
	private Color[] colors = { Color.BLUE , Color.RED, Color.GREEN, Color.CYAN, Color.GRAY, 
			Color.BLACK,  Color.DARK_GRAY, Color.GRAY, Color.MAGENTA, Color.MAGENTA, 
			Color.ORANGE, Color.PINK, Color.YELLOW};
	
	public ChartPanel createChartPannel(CategoryDataset dataset, String title, int seriesCount, String series) {
		JFreeChart chart;
		String yLabel = "";
		if(series.compareTo("playtime") == 0) {
			yLabel = "seconds";
		}else {
			yLabel = "count";
		}
		
		chart = ChartFactory.createBarChart(title, 
				"date", 
				yLabel, 
				dataset,
				PlotOrientation.VERTICAL, 
				true, 
				true, 
				false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setDomainGridlinesVisible(true);
		plot.setRangeCrosshairVisible(true);
		plot.setRangeCrosshairPaint(Color.GREEN);
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		
		for(int x = 0; x < seriesCount; x++) {
			int red = colors[x].getRed();
			int blue = colors[x].getBlue();
			int green = colors[x].getGreen();
			
			GradientPaint gp = new GradientPaint(0.0f, 0.0f, colors[x], 
					0.0f, 0.0f, new Color(red, green, blue));
			renderer.setSeriesPaint(x, gp);
			
			//it is not recommended to have THIS MANY datapoints on display ever
			if(x + 1 > colors.length) {
				seriesCount -= x;
				x = 0;
			}
		}
		
		renderer.setLegendItemToolTipGenerator(
				new StandardCategorySeriesLabelGenerator("Tooltip: {0}"));
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(
				CategoryLabelPositions.createDownRotationLabelPositions(
						Math.PI / 6.0));
		
		ChartPanel panel = new ChartPanel(chart);
		return panel;
	}

}
