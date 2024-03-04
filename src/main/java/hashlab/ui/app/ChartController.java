package hashlab.ui.app;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChartController {

    private int graphIndex;
    private List<ChartPanel> chartPanels = new ArrayList<>();

    public JPanel createChartPanel(String resultFilePath){

        Map<String, XYSeriesCollection> datasetMap = new HashMap<>();
        String line;
        String csvSplitBy = ",";

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(resultFilePath))){
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null){
                String[] data = line.split(csvSplitBy);
                String key = "Algorithm:" + data[0] + "; Function:" + data[1] + "; DataType:" + data[3] + "; Operation:" + data[6] + "; ChunkSize:" + data[5];
                double tableSize = Double.parseDouble(data[4]);
                double result = Double.parseDouble(data[7]);

                XYSeriesCollection dataset = datasetMap.getOrDefault(key, new XYSeriesCollection());
                XYSeries series = dataset.getSeriesCount() > 0 ? dataset.getSeries(0) : new XYSeries(key);
                series.add(tableSize, result);
                if (dataset.getSeriesCount() == 0) {
                    dataset.addSeries(series);
                    datasetMap.put(key, dataset);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        JPanel chartsContainer = new JPanel();
        chartsContainer.setLayout(new BorderLayout());

        datasetMap.forEach((key, value) -> createChart(key, value));

        if (!chartPanels.isEmpty()) {
            chartsContainer.add(chartPanels.get(0), BorderLayout.CENTER);
        }

        JButton previousButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        Box navigationButtons = Box.createHorizontalBox();
        navigationButtons.add(previousButton);
        navigationButtons.add(nextButton);

        previousButton.addActionListener(e -> navigateCharts(chartsContainer, -1));
        nextButton.addActionListener(e -> navigateCharts(chartsContainer, 1));

        chartsContainer.add(navigationButtons, BorderLayout.SOUTH);

        return chartsContainer;

    }

    private void createChart(String title, XYSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Table Size (Log Scale)",
                "Execution Time (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        LogarithmicAxis logAxis = new LogarithmicAxis("Table Size");
        plot.setDomainAxis(logAxis);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanels.add(chartPanel);
    }

    private void customizeChart(JFreeChart chart){

    }

    private void navigateCharts(JPanel chartsContainer, int direction) {
        if (!chartPanels.isEmpty()) {
            chartsContainer.remove(chartPanels.get(graphIndex));

            graphIndex += direction;
            if (graphIndex < 0) graphIndex = chartPanels.size() - 1;
            else if (graphIndex >= chartPanels.size()) graphIndex = 0;

            chartsContainer.add(chartPanels.get(graphIndex), BorderLayout.CENTER);

            chartsContainer.revalidate();
            chartsContainer.repaint();
        }
    }

}
