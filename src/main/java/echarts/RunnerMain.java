package echarts;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class RunnerMain {
  public static void main(String[] args) {
    try {
      String title = "Sales Data";
      String seriesName = "Products";
      String dataJson = generateDataset("doughnut");

      File htmlFile = ChartGeneratorHtml.generateChartHtml(title, seriesName, dataJson, "doughnut");
      String outputPath = "chart.png";
      HeadlessScreenshot.takeScreenshot(htmlFile.toURI().toString(), outputPath );
      log.info("Chart saved to: {}", outputPath);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private static String generateDataset(String chartType) {
    return switch (chartType.toLowerCase()) {
        case "pie", "doughnut", "bar" -> """
                    [
                        { "value": 1048, "name": "Search Engine" },
                        { "value": 735, "name": "Direct" },
                        { "value": 580, "name": "Email" },
                        { "value": 484, "name": "Union Ads" },
                        { "value": 300, "name": "Video Ads" }
                    ]
                """;
        case "gauge" -> """
                    [
                        { "value": 75, "name": "Completion" }
                    ]
                """;
        default -> throw new IllegalArgumentException("Unsupported chart type: " + chartType);
    };
  }
}
