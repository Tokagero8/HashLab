package hashlab.ui.app;

import hashlab.utils.DataGenerator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class HistogramController {

    boolean[] allowedChars = new boolean[128];

    public JPanel createHistogramPanel(String generated) {
        Arrays.fill(allowedChars, true);

        double[] values = new double[generated.length()];
        for (int i = 0; i < generated.length(); i++) {
            values[i] = (double) generated.charAt(i);
        }

        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.FREQUENCY);
        dataset.addSeries("ASCII Values", values, 128, 0, 128);

        JFreeChart chart = createChart(dataset);
        return new ChartPanel(chart);
    }

    private JFreeChart createChart(HistogramDataset dataset) {
        JFreeChart chart = ChartFactory.createHistogram(
                "Histogram ASCII",
                "Value",
                "Frequency",
                dataset
        );

        customizeChart(chart);
        return chart;
    }

    private void customizeChart(JFreeChart chart) {
        NumberAxis xAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
        xAxis.setRange(0.0, 127.0);
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        XYBarRenderer renderer = (XYBarRenderer) chart.getXYPlot().getRenderer();
        renderer.setBarPainter(new StandardXYBarPainter());
        renderer.setSeriesPaint(0, new Color(79, 129, 189));
        renderer.setDrawBarOutline(false);

        chart.getXYPlot().setDomainGridlinesVisible(false);
        chart.getXYPlot().setRangeGridlinesVisible(false);

        Font titleFont = new Font("SansSerif", Font.BOLD, 16);
        chart.getTitle().setFont(titleFont);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 12);
        chart.getXYPlot().getDomainAxis().setLabelFont(labelFont);
        chart.getXYPlot().getRangeAxis().setLabelFont(labelFont);

        chart.addLegend(new org.jfree.chart.title.LegendTitle(chart.getXYPlot().getRenderer()));
    }
}
