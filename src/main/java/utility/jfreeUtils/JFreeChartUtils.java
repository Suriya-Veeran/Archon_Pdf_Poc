package utility.jfreeUtils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import report.table.RowData;
import report.table.enums.ChartType;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JFreeChartUtils {
    public static File saveChartAsImage(JFreeChart chart) throws IOException {
        File chartFile = new File("temp_chart.png");
        ChartUtils.saveChartAsPNG(chartFile, chart, 400, 300);
        return chartFile;
    }


    public static JFreeChart generateJFreeChart(ChartType chartType, List<RowData> dataList) {
        return switch (chartType) {
            case BAR_VERTICAL_CHART -> createBarChart(dataList);
            case PIE_CHART -> createPieChart(dataList);
            case DOUGHNUT_CHART -> createDoughnutChart(dataList);
            default -> throw new IllegalArgumentException("Unsupported chart type: " + chartType);
        };
    }

    public static JFreeChart createBarChart(List<RowData> dataList) {

        CategoryDataset dataset = createCategoryDataset(dataList);

        JFreeChart chart =
                ChartFactory.createBarChart(
                        "Bar Chart", // Title
                        "Category", // X-axis label
                        "Value", // Y-axis label
                        dataset, // Dataset
                        PlotOrientation.VERTICAL,
                        true, // Show legend
                        true, // Use tooltips
                        false // Use URLs
                );
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlinePaint(Color.WHITE);
        plot.getDomainAxis().setTickLabelsVisible(false);
        return chart;
    }

    public static JFreeChart createPieChart(List<RowData> dataList) {
        // Create dataset for pie chart
        DefaultPieDataset dataset = createDefaultPieDataSet(dataList);

        // Create the chart
        JFreeChart chart =
                ChartFactory.createPieChart(
                        "Pie Chart", // Title
                        dataset, // Dataset
                        true, // Show legend
                        true, // Use tooltips
                        false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlinePaint(Color.WHITE);
        plot.setDefaultSectionPaint(Color.WHITE);
        return chart;
    }

    public static JFreeChart createDoughnutChart(List<RowData> dataList) {
        DefaultPieDataset dataset = createDefaultPieDataSet(dataList);

        JFreeChart chart =
                ChartFactory.createRingChart(
                        "Doughnut Chart", // Title
                        dataset, // Dataset
                        true, // Show legend
                        true, // Use tooltips
                        false);

        RingPlot plot = (RingPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlinePaint(Color.WHITE);

        return chart;
    }

    public static CategoryDataset createCategoryDataset(List<RowData> dataList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (RowData row : dataList) {
            dataset.addValue(
                    Integer.parseInt(row.getValue()), // Y value
                    row.getLabel(), // Series key
                    row.getLabel() // Category (X-axis value)
            );
        }

        return dataset;
    }

    public static DefaultPieDataset createDefaultPieDataSet(List<RowData> dataList) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (RowData row : dataList) {
            dataset.setValue(row.getLabel(), Integer.parseInt(row.getValue()));
        }

        return dataset;
    }

}
