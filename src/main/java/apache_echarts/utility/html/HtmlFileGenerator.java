package apache_echarts.utility.html;

import static apache_echarts.constants.FileNameConstants.GENERATED_CHART;
import static apache_echarts.constants.HtmlConstants.*;
import static apache_echarts.constants.PathConstants.HTML_FILES;

import apache_echarts.beans.chart.*;
import apache_echarts.beans.html_beans.HtmlCreationInfoBean;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import utility.unit_conversion.UnitConversion;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HtmlFileGenerator {
  public static File generateHtml(HtmlCreationInfoBean htmlCreationInfoBean) {

    ChartBasicInfo chartBasicInfo = htmlCreationInfoBean.getChartBasicInfo();
    TitleInfoBean titleInfoBean = htmlCreationInfoBean.getTitleInfoBean();
    ToolTipInfoBean toolTipInfoBean = htmlCreationInfoBean.getToolTipInfoBean();
    LegendInfoBean legendInfoBean = htmlCreationInfoBean.getLegendInfoBean();
    SeriesInfoBean seriesInfoBean = htmlCreationInfoBean.getSeriesInfoBean();
    AxisInfoBean xAxisInfoBean = htmlCreationInfoBean.getXaxisInfoBean();
    AxisInfoBean yAxisInfoBean = htmlCreationInfoBean.getYaxisInfoBean();

    String language = chartBasicInfo.getLanguage();
    String charSet = chartBasicInfo.getCharSet();

    StringBuilder htmlContent = new StringBuilder();
    htmlContent
        .append("<!DOCTYPE html>\n")
        .append("<html lang=\"")
        .append(language)
        .append("\">\n")
        .append("<head>\n")
        .append("    <meta charset=\"")
        .append(charSet)
        .append("\">\n")
        .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
        .append("    <title>")
        .append(chartBasicInfo.getTitle())
        .append("</title>\n")
        .append("    <style>\n")
        .append("        #chart { width: ")
        .append(chartBasicInfo.getChartWidth())
        .append("; height: ")
        .append(chartBasicInfo.getChartHeight())
        .append("; margin: 0 auto; }\n")
        .append("        .title { text-align: ")
        .append(getNonNullValue(titleInfoBean.getLeft(), "center"))
        .append("; position: ")
        .append(getNonNullValue(titleInfoBean.getBottom(), "relative"))
        .append("; font-size: ")
        .append(titleInfoBean.getTextStyle().getFontSize())
        .append("px; font-family: ")
        .append(titleInfoBean.getTextStyle().getFontFamily())
        .append("; font-weight: ")
        .append(titleInfoBean.getTextStyle().getFontWeight())
        .append("; color: ")
        .append(titleInfoBean.getTextStyle().getColor())
        .append("; }\n")
        .append("        .sub-title { font-size: ")
        .append(titleInfoBean.getSubTextStyle().getFontSize())
        .append("px; font-family: ")
        .append(titleInfoBean.getSubTextStyle().getFontFamily())
        .append("; font-weight: ")
        .append(titleInfoBean.getSubTextStyle().getFontWeight())
        .append("; color: ")
        .append(titleInfoBean.getSubTextStyle().getColor())
        .append("; }\n")
        .append("    </style>\n")
        .append("</head>\n")
        .append("<body>\n")
        .append("    <div id=\"chart\"></div>\n")
        .append(
            "    <script src=\"https://cdn.jsdelivr.net/npm/echarts@5.0.2/dist/echarts.min.js\"></script>\n")
        .append("    <script>\n");

    String chartType = chartBasicInfo.getChartType();
    htmlContent.append(
        generateJavaScript(
            chartType, titleInfoBean, toolTipInfoBean, legendInfoBean, seriesInfoBean, xAxisInfoBean, yAxisInfoBean));
    htmlContent.append("</script>\n");
    htmlContent.append("</body>\n");
    htmlContent.append("</html>");

    File outputFile = new File(HTML_FILES + File.separator + GENERATED_CHART+"_"+chartType+".html");
    try (FileWriter writer =
        new FileWriter(outputFile)) {
      writer.write(htmlContent.toString());
      log.info("HTML generated successfully.");
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return outputFile;  // Returning the generated HTML file
  }

  private static String generateJavaScript(
      String chartType,
      TitleInfoBean titleInfoBean,
      ToolTipInfoBean toolTipInfoBean,
      LegendInfoBean legendInfoBean,
      SeriesInfoBean seriesInfoBean,
      AxisInfoBean xaxisInfoBean,
      AxisInfoBean yaxisInfoBean) {
    StringBuilder jsContent = new StringBuilder();
    jsContent
        .append("    var chart = echarts.init(document.getElementById('chart'));\n\n")
        .append("    function formatValue(value) {\n")
        .append("        if (value >= 1024 * 1024) {\n")
        .append("            return (value / (1024 * 1024)).toFixed(2) + ' GB';\n")
        .append("        } else if (value >= 1024) {\n")
        .append("            return (value / 1024).toFixed(2) + ' MB';\n")
        .append("        }\n")
        .append("        return value + ' KB';\n")
        .append("    }\n\n")
        .append("    var option = {\n")
        .append("        title: {\n")
        .append("            text: '")
        .append(titleInfoBean.getText())
        .append("',\n")
        .append("            subtext: '")
        .append(titleInfoBean.getSubText())
        .append("',\n")
        .append("            left: '")
        .append(getNonNullValue(titleInfoBean.getLeft(), "center"))
        .append("',\n");

    // Handling empty or null 'bottom' value
    String bottom = getNonNullValue(titleInfoBean.getBottom(), "auto");
    jsContent.append("            bottom: '").append(bottom).append("',\n");

    jsContent
        .append("            textStyle: {\n")
        .append(FONT_SIZE)
        .append(titleInfoBean.getTextStyle().getFontSize())
        .append(",\n")
        .append(FONT_FAMILY_WITH_SPACE)
        .append(titleInfoBean.getTextStyle().getFontFamily())
        .append("',\n")
        .append(FONT_WEIGHT_WITH_SPACE)
        .append(titleInfoBean.getTextStyle().getFontWeight())
        .append("',\n")
        .append(COLOR_WITH_SPACE)
        .append(titleInfoBean.getTextStyle().getColor())
        .append("'\n")
        .append(NEXT_LINE_WITH_SPACE)
        .append(NEXT_LINE_WITH_MIN_SPACE)
        .append("        tooltip: {\n")
        .append("            trigger: '")
        .append(toolTipInfoBean.getTrigger())
        .append("',\n")
        .append("            formatter: '")
        .append(getNonNullValue(toolTipInfoBean.getFormatter(), "{b}: {c}"))
        .append("'\n")
        .append(NEXT_LINE_WITH_MIN_SPACE)
        .append("        legend: {\n")
        .append("            orient: '")
        .append(legendInfoBean.getOrient())
        .append("',\n")
        .append("            left: '")
        .append(legendInfoBean.getLeft())
        .append("',\n")
        .append("            bottom: '")
        .append(legendInfoBean.getBottom())
        .append("',\n")
        .append("            data: [");

    // Ensure legend data is formatted correctly as a list of strings
    String[] legendData = legendInfoBean.getData().toArray(new String[0]);
    for (int i = 0; i < legendData.length; i++) {
      jsContent.append("'").append(legendData[i]).append("'");
      if (i < legendData.length - 1) {
        jsContent.append(", ");
      }
    }

    jsContent.append("]\n").append(NEXT_LINE_WITH_MIN_SPACE);

    // Handling different chart types (Pie/Doughnut etc.)
    if (chartType.equalsIgnoreCase("pie")) {
      jsContent.append(generatePieChart(seriesInfoBean, false)); // Regular pie chart
    } else if (chartType.equalsIgnoreCase("doughnut")) {
      jsContent.append(generatePieChart(seriesInfoBean, true)); // Doughnut chart with inner radius
    } else if (chartType.equalsIgnoreCase("bar")) {
      jsContent.append(generateBarChart(seriesInfoBean, xaxisInfoBean, yaxisInfoBean));
    }
    else {
      throw new IllegalArgumentException("Unsupported chart type: " + chartType);
    }

    jsContent.append("    };\n").append("    chart.setOption(option);\n");
    return jsContent.toString();
  }

  private static String generateBarChart(SeriesInfoBean seriesInfoBean,
                                         AxisInfoBean xaxisInfoBean,
                                         AxisInfoBean yaxisInfoBean) {
    StringBuilder barChartData = new StringBuilder();

    // Add xAxis configuration
    barChartData.append("        xAxis: {")
            .append("            type: '")
            .append(xaxisInfoBean.getType())
            .append("',\n")
            .append("            data: [");

    // Add xAxis data
    for (String data : xaxisInfoBean.getData()) {
      barChartData.append("'").append(data).append("', ");
    }
    barChartData.delete(barChartData.length() - 2, barChartData.length()); // Remove trailing comma
    barChartData.append("],\n")
            .append("            axisLabel: {")
            .append("                fontSize: ")
            .append(xaxisInfoBean.getTextStyle().getFontSize())
            .append(",\n")
            .append(FONT_FAMILY_WITH_SPACE)
            .append(xaxisInfoBean.getTextStyle().getFontFamily())
            .append("',\n")
            .append(FONT_WEIGHT_WITH_SPACE)
            .append(xaxisInfoBean.getTextStyle().getFontWeight())
            .append("',\n")
            .append(COLOR_WITH_SPACE)
            .append(xaxisInfoBean.getTextStyle().getColor())
            .append("'\n")
            .append("            }\n")
            .append("        },\n");

    // Add yAxis configuration
    barChartData.append("        yAxis: {")
            .append("            type: '")
            .append(yaxisInfoBean.getType())
            .append("',\n")
            .append("            axisLabel: {")
            .append("                fontSize: ")
            .append(yaxisInfoBean.getTextStyle().getFontSize())
            .append(",\n")
            .append(FONT_FAMILY_WITH_SPACE)
            .append(yaxisInfoBean.getTextStyle().getFontFamily())
            .append("',\n")
            .append(FONT_WEIGHT_WITH_SPACE)
            .append(yaxisInfoBean.getTextStyle().getFontWeight())
            .append("',\n")
            .append(COLOR_WITH_SPACE)
            .append(yaxisInfoBean.getTextStyle().getColor())
            .append("'\n")
            .append("            }\n")
            .append("        },\n");

    // Add series configuration
    barChartData
        .append("        series: [{\n")
        .append("            name: '")
        .append(seriesInfoBean.getName())
        .append("',\n")
        .append("            type: 'bar',\n")
        .append("            data: [\n");

    for (DataInfoBean data : seriesInfoBean.getData()) {
      barChartData
          .append("                { value: ")
          .append(data.getValue())
          .append(", name: '")
          .append(data.getName())
          .append("', itemStyle: { color: '")
          .append(data.getItemStyle().getColor())
          .append("' },\n");
    }

    barChartData.deleteCharAt(barChartData.length() - 2); // Remove last comma
    barChartData
        .append("            ],\n")
        .append("            label: {\n")
        .append("                show: ")
        .append(seriesInfoBean.getLabel().isShow())
        .append(",\n")
        .append("                position: '")
        .append(seriesInfoBean.getLabel().getPosition())
        .append("',\n")
        .append("                formatter: '")
        .append(seriesInfoBean.getLabel().getFormatter())
        .append("',\n")
        .append(FONT_SIZE)
        .append(seriesInfoBean.getLabel().getFontSize())
        .append("\n")
        .append(NEXT_LINE_WITH_SPACE)
        .append("        }]\n");

    return barChartData.toString();
  }

  private static String generatePieChart(SeriesInfoBean seriesInfoBean, boolean isDoughnut) {
    StringBuilder pieChartData = new StringBuilder();
    pieChartData
        .append("        series: [{\n")
        .append("            name: '")
        .append(seriesInfoBean.getName())
        .append("',\n")
        .append("            type: 'pie',\n");

    // For Doughnut chart, set inner radius
    if (isDoughnut) {
      pieChartData.append("            radius: ['30%', '55%'],\n"); // Inner radius and outer radius
    } else {
      pieChartData.append("            radius: '50%',\n"); // Regular pie chart
    }

    pieChartData.append("            data: [\n");

    for (DataInfoBean data : seriesInfoBean.getData()) {
      pieChartData
          .append("                { value: ")
          .append(UnitConversion.convertToKb(data.getFormat(), data.getValue()))
          .append(", name: '")
          .append(data.getName())
          .append("', itemStyle: { color: '")
          .append(data.getItemStyle().getColor())
          .append("' } }");
      if (data != seriesInfoBean.getData().get(seriesInfoBean.getData().size() - 1)) {
        pieChartData.append(",\n");
      }
    }

    pieChartData.deleteCharAt(pieChartData.length() - 2); // Remove last comma
    pieChartData
        .append("            ],\n")
        .append("            label: {\n")
        .append("                show: ")
        .append(seriesInfoBean.getLabel().isShow())
        .append(",\n")
        .append("                position: '")
        .append(seriesInfoBean.getLabel().getPosition())
        .append("',\n")
        .append("                formatter: function (params) {\n")
        .append("                    return formatValue(params.value) + ' (' + params.percent + '%)';\n")
        .append("                },\n")
        .append(FONT_SIZE)
        .append(seriesInfoBean.getLabel().getFontSize())
        .append("\n")
        .append(NEXT_LINE_WITH_SPACE)
        .append("        }]\n");

    return pieChartData.toString();
  }

  // Helper method to handle null or empty values and provide default values
  private static String getNonNullValue(String value,
                                        String defaultValue) {
    return (value == null || value.isEmpty()) ? defaultValue : value;
  }
}
