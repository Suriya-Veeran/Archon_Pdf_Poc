package jfree.charts;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class ChartGenerator {

  public static void main(String[] args) {
    try {
      // Example usage of the reusable methods
      generateChart("bar", "bar_chart.png", 800, 600, null);
      generateChart("pie", "pie_chart.png", 800, 600, null);
      generateChart("doughnut", "doughnut_chart.png", 800, 600, null);

      log.info("Charts generated and saved successfully.");
    } catch (IOException e) {
      log.error("Error generating chart: {}", e.getMessage());
    }
  }

  /**
   * Generates a chart based on the specified type and saves it as an image.
   *
   * @param chartType The type of chart to generate (bar, pie, doughnut)
   * @param path The file path to save the chart image
   * @param width Width of the chart image
   * @param height Height of the chart image
   * @param customizations Map of customizations like colors, labels, etc.
   * @throws IOException If there's an error while saving the image
   */
  public static void generateChart(
      String chartType, String path, int width, int height, Map<String, Color> customizations)
      throws IOException {
    JFreeChart chart = createChart(chartType, customizations);
    saveChartAsImage(chart, path, width, height);
  }

  /**
   * Creates a chart based on the specified chart type.
   *
   * @param chartType The type of chart to create
   * @param customizations Map of customizations to apply (e.g., colors)
   * @return The JFreeChart object
   */
  public static JFreeChart createChart(String chartType, Map<String, Color> customizations) {
      return switch (chartType.toLowerCase()) {
          case "bar" -> createBarChart(customizations);
          case "pie" -> createPieChart(customizations);
          case "doughnut" -> createDoughnutChart(customizations);
          default -> throw new IllegalArgumentException("Unsupported chart type: " + chartType);
      };
  }

  /**
   * Creates a reusable bar chart with customization support.
   *
   * @param customizations Map of customizations to apply (e.g., colors)
   * @return The JFreeChart object
   */
  public static JFreeChart createBarChart(Map<String, Color> customizations) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    dataset.addValue(10, "Category A", "January");
    dataset.addValue(15, "Category A", "February");
    dataset.addValue(20, "Category A", "March");

    JFreeChart barChart =
        ChartFactory.createBarChart(
            "Bar Chart Example", // Chart title
            "Month", // X-axis label
            "Value", // Y-axis label
            dataset // Dataset
            );

    // Apply customizations
    applyBarChartCustomizations(barChart, customizations);
    return barChart;
  }

  /**
   * Creates a reusable pie chart with customization support.
   *
   * @param customizations Map of customizations to apply (e.g., colors)
   * @return The JFreeChart object
   */
  public static JFreeChart createPieChart(Map<String, Color> customizations) {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Category A", 40);
    dataset.setValue("Category B", 30);
    dataset.setValue("Category C", 30);

    JFreeChart pieChart =
        ChartFactory.createPieChart(
            "Pie Chart Example", // Chart title
            dataset, // Dataset
            true, // Include legend
            true,
            false);

    // Apply customizations
    applyPieChartCustomizations(pieChart, customizations);
    return pieChart;
  }

  /**
   * Creates a reusable doughnut chart with customization support.
   *
   * @param customizations Map of customizations to apply (e.g., colors)
   * @return The JFreeChart object
   */
  public static JFreeChart createDoughnutChart(Map<String, Color> customizations) {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Category X", 50);
    dataset.setValue("Category Y", 30);
    dataset.setValue("Category Z", 20);

    JFreeChart doughnutChart =
        ChartFactory.createRingChart(
            "Doughnut Chart Example", // Chart title
            dataset, // Dataset
            true, // Include legend
            true,
            false);

    // Apply customizations
    applyDoughnutChartCustomizations(doughnutChart, customizations);
    return doughnutChart;
  }

  /**
   * Saves a chart as an image.
   *
   * @param chart The JFreeChart object to save
   * @param path The file path to save the chart image
   * @param width Width of the chart image
   * @param height Height of the chart image
   * @throws IOException If there's an error while saving the image
   */
  public static void saveChartAsImage(JFreeChart chart, String path, int width, int height)
      throws IOException {
    File outputFile = new File(path);
    ChartUtils.saveChartAsPNG(outputFile, chart, width, height);
    log.info("Saved chart as: {}", path);
  }

  // Helper Methods for Customizing Charts

  private static void applyBarChartCustomizations(
      JFreeChart chart, Map<String, Color> customizations) {
    CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(Color.WHITE);
    plot.setOutlinePaint(Color.WHITE);

    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    if (customizations != null && customizations.containsKey("barColor")) {
      renderer.setSeriesPaint(0, customizations.get("barColor"));
    } else {
      renderer.setSeriesPaint(0, Color.BLUE);
    }
  }

  private static void applyPieChartCustomizations(
      JFreeChart chart, Map<String, Color> customizations) {
    PiePlot plot = (PiePlot) chart.getPlot();
    plot.setBackgroundPaint(Color.WHITE);
    plot.setOutlinePaint(Color.WHITE);

    if (customizations != null) {
      customizations.forEach(
          (key, color) -> {
            if (key.startsWith("section")) {
              plot.setSectionPaint(key, color);
            }
          });
    }
  }

  private static void applyDoughnutChartCustomizations(
      JFreeChart chart, Map<String, Color> customizations) {
    RingPlot plot = (RingPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.WHITE);
    plot.setOutlinePaint(Color.WHITE);
    plot.setSectionDepth(0.35);

    if (customizations != null) {
      customizations.forEach(
          (key, color) -> {
            if (key.startsWith("section")) {
              plot.setSectionPaint(key, color);
            }
          });
    }
  }
}
