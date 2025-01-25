package echarts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChartGeneratorHtml {
  public static File generateChartHtml(
      String title, String seriesName, String dataJson, String chartType) throws IOException {
    String htmlContent = buildHtmlContent(title, seriesName, dataJson, chartType);

    File htmlFile =
        new File("src/main/resources/echartOutputFolder" + File.separator + "chart.html");
    try (FileWriter writer = new FileWriter(htmlFile)) {
      writer.write(htmlContent);
    }
    return htmlFile;
  }

  private static String buildHtmlContent(
      String title, String seriesName, String dataJson, String chartType) {
    StringBuilder htmlContent = new StringBuilder();
    htmlContent
        .append("<!DOCTYPE html>")
        .append("<html>")
        .append("<head>")
        .append("<meta charset='utf-8'>")
        .append("<title>Dynamic EChart</title>")
        .append(
            "<script src='https://cdnjs.cloudflare.com/ajax/libs/echarts/5.4.2/echarts.min.js'></script>")
        .append("<style>#chart { width: 100%; height: 400px; border: 1px solid #ccc; }</style>")
        .append("</head>")
        .append("<body>")
        .append("<div id='chart'></div>")
        .append("<script>")
        .append("window.onload = function() {")
        .append("var chartDom = document.getElementById('chart');")
        .append("var myChart = echarts.init(chartDom);")
        .append("var option = {")
        .append("title: { text: '")
        .append(title)
        .append("', left: 'center' },")
        .append("tooltip: { trigger: 'item' },")
        .append("legend: { orient: 'vertical', left: 'left' },")
        .append(
            generateChartScript(chartType, seriesName, dataJson)) // Inject chart-specific script
        .append("};")
        .append("myChart.setOption(option);")
        .append("};")
        .append("</script>")
        .append("</body>")
        .append("</html>");
    return htmlContent.toString();
  }

  private static String generateChartScript(String chartType, String seriesName, String dataJson) {
    StringBuilder chartScript = new StringBuilder();

    switch (chartType.toLowerCase()) {
      case "pie":
        chartScript.append("series: [")
                .append("{ name: '")
                .append(seriesName)
                .append("', type: 'pie', radius: ['50%'], center: ['50%', '50%'], data: ")
                .append(dataJson)
                .append(",")
                .append("label: {")
                .append("show: true,")
                .append("formatter: '{b}: {c} ({d}%)'")
                .append("},")
                .append("tooltip: {")
                .append("trigger: 'item',")  // Trigger tooltip on item hover
                .append("formatter: '{b}<br/>Value: {c}<br/>Percentage: {d}%'") // Tooltip content
                .append("}")
                .append(" }]"); // Added tooltip
        break;

      case "doughnut":
        chartScript.append("series: [")
                .append("{ name: '")
                .append(seriesName)
                .append("', type: 'pie', radius: ['40%', '70%'], center: ['50%', '50%'], data: ")
                .append(dataJson)
                .append(" }]"); // Doughnut chart with inner radius for the hole
        break;

      case "bar":
        JSONArray jsonArray = new JSONArray(dataJson);
        StringBuilder categories = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject obj = jsonArray.getJSONObject(i);
          categories.append("'").append(obj.getString("name")).append("',");
          values.append(obj.getInt("value")).append(",");
        }

        categories.deleteCharAt(categories.length() - 1); // Remove trailing comma
        values.deleteCharAt(values.length() - 1); // Remove trailing comma

        chartScript
            .append("xAxis: { type: 'category', data: [")
            .append(categories)
            .append("] },")
            .append("yAxis: { type: 'value' },")
            .append("series: [{ name: '")
            .append(seriesName)
            .append("', type: 'bar', data: [")
            .append(values)
            .append("] }]");
        break;

      case "gauge":
        chartScript.append("series: [")
                .append("{ name: '")
                .append(seriesName)
                .append("', type: 'gauge', detail: { formatter: '{value}%' }, data: ")
                .append(dataJson)
                .append(" }]");
        break;

      default:
        throw new IllegalArgumentException("Unsupported chart type: " + chartType);
    }

    return chartScript.toString();
  }
}
