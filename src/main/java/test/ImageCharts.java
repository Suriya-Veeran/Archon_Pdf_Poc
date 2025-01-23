package test;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.p3solutions.archon_report_utility.constants.ImageChartsConstants.*;

@Slf4j
public class ImageCharts {

  private static void generateChart(
      String fileName,
      ChartType chartType,
      List<ChartData> chartDataList,
      List<String> legends,
      String margins)
      throws IOException {

    List<Integer> dataValues = chartDataList.stream().map(ChartData::getValue).toList();
    List<String> labels = chartDataList.stream().map(ChartData::getLabel).toList();
    List<String> colors = chartDataList.stream().map(ChartData::getColor).toList();

    Map<String, String> params =
        buildChartParams(chartType, dataValues, labels, colors, legends, margins);

    String urlString = buildUrl(params);

    URL url = new URL(urlString);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.setRequestMethod("GET");
    urlConnection.setRequestProperty("Accept", "application/json");

    Path filePath = Paths.get("src/main/resources/test/" + fileName + ".png");
    int responseCode = urlConnection.getResponseCode();
    log.info("response code {}", responseCode);
    log.info("url {}", url);

    if (responseCode == HttpURLConnection.HTTP_OK) {
      Files.createDirectories(filePath.getParent());

      try (BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
          FileOutputStream fos = new FileOutputStream(filePath.toFile())) {

        byte[] buffer = new byte[1024];
        int count;
        while ((count = bis.read(buffer)) != -1) {
          fos.write(buffer, 0, count);
        }
      }
    }
    log.info("Image saved to: {}", filePath.toAbsolutePath());
  }

  private static Map<String, String> buildChartParams(
      ChartType chartType,
      List<Integer> dataValues,
      List<String> labels,
      List<String> colors,
      List<String> legends,
      String margins) {
    Map<String, String> params = new HashMap<>();

    params.put(CHS, "700x300"); // Chart size
    params.put(COLOR, String.join(",", colors)); // Colors
    params.put(
        CHD,
        "t:"
            + dataValues.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","))); // Data values
    params.put(CHTT, "Chart Title"); // Default Title
    params.put("chl", String.join("|", labels)); // labels

    if (!legends.isEmpty()) {
      params.put("chdl", String.join("|", legends)); // Legends
    }

    if (margins != null && !margins.isEmpty()) {
      params.put("chma", margins); // Margins
    }

    switch (chartType) {
      case BAR_VERTICAL_CHART:
        params.put("cht", ChartType.BAR_VERTICAL_CHART.getType()); // Bar chart type
        params.put("chxt", "x,y"); // X and Y axis
        params.put("chxl", "0:|" + String.join("|", labels)); // X Axis labels
        params.put("chbh", "40,10,20"); // Bar settings
        break;

      case DOUGHNUT_CHART:
        params.put("cht", ChartType.DOUGHNUT_CHART.getType()); // Doughnut chart type
        params.put("chbr", "20"); // Border radius
        params.put("chdlp", "r"); // Legend position
        break;

      case PIE_CHART:
        params.put("cht", ChartType.PIE_CHART.getType()); // Pie chart type
        params.put("chbr", "20"); // Border radius
        params.put("chdlp", "b"); // Legend position
        break;

      default:
        throw new IllegalArgumentException("Unsupported chart type: " + chartType);
    }

    return params;
  }

  private static String buildUrl(Map<String, String> params) {
    String queryString =
        params.entrySet().stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));
    return BASE_URI + "?" + queryString;
  }

  public static void main(String[] args) throws IOException {
    List<ChartData> chartDataList =
        Arrays.asList(
            new ChartData(10, "Label1", "008FFF"),
            new ChartData(20, "Label2", "264653"),
            new ChartData(30, "Label3", "2A9D8F"),
            new ChartData(40, "Label4", "E9C46A"),
            new ChartData(50, "Label5", "E9C46A"));

    List<String> legends = Arrays.asList("Series1", "Series2", "Series3", "Series4", "Series5");

    String margins = "10,20,10,20";

    generateChart(
        "barchart_with_legend_and_margin",
        ChartType.BAR_VERTICAL_CHART,
        chartDataList,
        legends,
        margins);

    generateChart(
        "pie_with_legend_and_margin", ChartType.PIE_CHART, chartDataList, legends, margins);

    generateChart(
        "doughnut_with_legend_and_margin",
        ChartType.DOUGHNUT_CHART,
        chartDataList,
        legends,
        margins);
  }

  public static List<ChartData> createChartData(int size) {
    List<ChartData> chartDataList = new ArrayList<>();
    Random random = new Random();

    for (int i = 1; i <= size; i++) {
      //      int value = i * 1;
      String label = "Label" + i;
      String color = String.format("%06X", random.nextInt(0xFFFFFF + 1));

      chartDataList.add(new ChartData(i, label, color)); // Add to the list
    }
    return chartDataList;
  }

  public static List<String> createLegends(int size) {
    return IntStream.rangeClosed(1, size).mapToObj(i -> "Series" + i).toList();
  }

  public static String createMargins(int size) {
    String baseMargins = "10,20";
    List<String> marginParts = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      marginParts.add(baseMargins);
    }
    return String.join(",", marginParts);
  }
}
