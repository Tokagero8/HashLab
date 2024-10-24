package hashlab.ui.app;

import hashlab.tests.ResultDataConfig;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class ChartController {

    private Map<String, XYSeriesCollection> datasetMap = new HashMap<>();
    private JPanel mainPanel;
    private JPanel chartPanel;
    private JComboBox<String> algorithmComboBox;
    private JComboBox<String> operationComboBox;
    private String resultFilePath;
    private ResultDataConfig resultDataConfig;
    private JPanel filtersPanel;
    private Map<String, JComboBox<String>> filtersComboBoxes = new HashMap<>();

    public ChartController(String resultFilePath) {
        this.resultFilePath = resultFilePath;
        this.resultDataConfig = new ResultDataConfig();
        loadDataset();
        initializeUIComponents();
    }

    private void loadDataset() {
        String line;
        String csvSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(resultFilePath))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                ResultDataConfig.TestResult result = new ResultDataConfig.TestResult(
                        data[0],
                        data[1],
                        Integer.parseInt(data[2]),
                        data[3],
                        Integer.parseInt(data[4]),
                        Integer.parseInt(data[5]),
                        data[6],
                        Double.parseDouble(data[7])
                );
                resultDataConfig.addResult(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeUIComponents() {
        createFiltersPanel();
        createChartPanel();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(filtersPanel, BorderLayout.NORTH);
        mainPanel.add(chartPanel, BorderLayout.CENTER);
    }

    private void createFiltersPanel() {
        filtersPanel = new JPanel();
        filtersPanel.setLayout(new BoxLayout(filtersPanel, BoxLayout.Y_AXIS));

        addFilter("Algorithm", new ArrayList<>(resultDataConfig.getUniqueAlgorithms()));
        addFilter("Function", new ArrayList<>(resultDataConfig.getUniqueFunctions()));
        addFilter("Data Type", new ArrayList<>(resultDataConfig.getUniqueDataTypes()));
        addFilter("Operation", new ArrayList<>(resultDataConfig.getUniqueOperations()));

        JButton applyFiltersButton = new JButton("Apply Filters");
        applyFiltersButton.addActionListener(e -> applyFilters());
        filtersPanel.add(applyFiltersButton);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void addFilter(String label, List<String> options) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label + ":"));

        JComboBox<String> comboBox = new JComboBox<>(options.toArray(new String[0]));
        comboBox.insertItemAt("All", 0);
        comboBox.setSelectedIndex(0);
        panel.add(comboBox);

        filtersComboBoxes.put(label, comboBox);
        filtersPanel.add(panel);
    }

    private void applyFilters() {
        String selectedAlgorithm = (String) filtersComboBoxes.get("Algorithm").getSelectedItem();
        String selectedFunction = (String) filtersComboBoxes.get("Function").getSelectedItem();
        String selectedDataType = (String) filtersComboBoxes.get("Data Type").getSelectedItem();
        String selectedOperation = (String) filtersComboBoxes.get("Operation").getSelectedItem();


        Stream<ResultDataConfig.TestResult> filteredResults = resultDataConfig.getResults().stream()
                .filter(r -> "All".equals(selectedAlgorithm) || r.getAlgorithm().equals(selectedAlgorithm))
                .filter(r -> "All".equals(selectedFunction) || r.getFunction().equals(selectedFunction))
                .filter(r -> "All".equals(selectedDataType) || r.getDataType().equals(selectedDataType))
                .filter(r -> "All".equals(selectedOperation) || r.getOperation().equals(selectedOperation));


        XYSeriesCollection dataset = new XYSeriesCollection();
        Map<String, XYSeries> seriesMap = new HashMap<>();

        filteredResults.forEach(result -> {
            String key = String.format("%s, %s, %s, %s", result.getAlgorithm(), result.getFunction(), result.getDataType(), result.getOperation());
            XYSeries series = seriesMap.computeIfAbsent(key, k -> new XYSeries(k));
            series.add(result.getTableSize(), result.getResult());
        });

        seriesMap.values().forEach(dataset::addSeries);


        JFreeChart chart = ChartFactory.createXYLineChart(
                "Filtered Results",
                "Table Size",
                "Normalized Performance Score",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );


        chartPanel.removeAll();
        chartPanel.add(new ChartPanel(chart), BorderLayout.CENTER);
        chartPanel.validate();
        chartPanel.repaint();
    }

    public JPanel getFiltersPanel() {
        return filtersPanel;
    }

    private void createChartPanel() {
        chartPanel = new JPanel(new BorderLayout());
    }

    public JPanel getControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        Set<String> algorithms = new HashSet<>();
        Set<String> operations = new HashSet<>();


        datasetMap.keySet().forEach(key -> {
            String[] parts = key.split("_");
            algorithms.add(parts[0]);
            operations.add(parts[1]);
        });

        algorithmComboBox = new JComboBox<>(algorithms.toArray(new String[0]));
        operationComboBox = new JComboBox<>(operations.toArray(new String[0]));

        algorithmComboBox.addActionListener(e -> updateChart());
        operationComboBox.addActionListener(e -> updateChart());

        controlPanel.add(new JLabel("Select Algorithm:"));
        controlPanel.add(algorithmComboBox);
        controlPanel.add(new JLabel("Select Operation:"));
        controlPanel.add(operationComboBox);

        return controlPanel;
    }

    private void updateChart() {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        String selectedOperation = (String) operationComboBox.getSelectedItem();
        if (selectedAlgorithm == null || selectedOperation == null) return;

        XYSeriesCollection dataset = new XYSeriesCollection();
        String chartKey = selectedAlgorithm + "_" + selectedOperation;

        datasetMap.forEach((key, value) -> {
            if (key.startsWith(chartKey)) {
                dataset.addSeries(value.getSeries(0));
            }
        });

        JFreeChart chart = ChartFactory.createXYLineChart(
                selectedAlgorithm + " " + selectedOperation + " Operation Results",
                "Table Size (Log Scale)",
                "Execution Time (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel newChartPanel = new ChartPanel(chart);
        chartPanel.removeAll();
        chartPanel.add(newChartPanel, BorderLayout.CENTER);
        chartPanel.validate();
        chartPanel.repaint();
    }

    public JPanel getChartPanel() {
        return chartPanel;
    }
}
