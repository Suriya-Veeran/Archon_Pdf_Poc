package report.table.utils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import report.table.RowData;
import report.table.requestbean.ChartRequestBean;
import report.table.requestbean.ParametersBean;
import test.ChartType;

import static com.p3solutions.archon_report_utility.constants.ImageChartsConstants.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChartCreation {

  public static Image generateChart(ChartRequestBean chartRequestBean) throws IOException {

    Map<String, String> params = buildChartParams(chartRequestBean);

    String urlString = buildUrl(params);

    URL url = new URL(urlString);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.setRequestMethod("GET");
    urlConnection.setRequestProperty("Accept", "application/json");

    Path filePath = Paths.get("src/main/resources/test/" + chartRequestBean.getFileName() + ".png");
    int responseCode = urlConnection.getResponseCode();
    log.info("Response code: {}", responseCode);
    log.info("Chart URL: {}", url);

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
    } else {
      throw new IOException("Failed to fetch chart image. HTTP response code: " + responseCode);
    }
    log.info("Image saved to: {}", filePath.toAbsolutePath());

    File croppedFile =
        cropImage(
            filePath.toFile(),
            "cropped_" + chartRequestBean.getFileName() + ".png",
            chartRequestBean);

    return convertToImage(croppedFile);
  }

  private static Map<String, String> buildChartParams(ChartRequestBean chartRequestBean) {

    List<String> dataValues =
        chartRequestBean.getChartDataList().stream().map(RowData::getValue).toList();
    List<String> labels =
        chartRequestBean.getChartDataList().stream().map(RowData::getLabel).toList();
    List<String> colors =
        chartRequestBean.getChartDataList().stream().map(RowData::getColorHex).toList();

    Map<String, String> params = new HashMap<>();

    params.put(CHS, chartRequestBean.getParametersBean().getChartWidth()); // Chart size
    params.put(
        CHD,
        "t:"
            + dataValues.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","))); // Data values
    params.put(CHTT, chartRequestBean.getParametersBean().getChartTitle()); // Default title
    //    params.put("chl", String.join("|", labels)); // Labels

    if (!chartRequestBean.getLegends().isEmpty()) {
      params.put("chdl", String.join("|", chartRequestBean.getLegends())); // Legends
    }

    if (chartRequestBean.getMargins() != null && !chartRequestBean.getMargins().isEmpty()) {
      params.put("chma", chartRequestBean.getMargins()); // Margins
    }

    switch (chartRequestBean.getChartType()) {
      case BAR_VERTICAL_CHART -> {
        params.put(COLOR, String.join("|", colors)); // Colors
        params.put("cht", ChartType.BAR_VERTICAL_CHART.getType()); // Bar chart type
        params.put("chxt", chartRequestBean.getParametersBean().getChartAxis()); // X and Y axis
        //        params.put("chxl", "0:|" + String.join("|", labels)); // X Axis labels
        params.put(
            "chbh", chartRequestBean.getParametersBean().getChartBarSettings()); // Bar settings
      }
      case DOUGHNUT_CHART -> {
        params.put(COLOR, String.join(",", colors)); // Colors
        params.put("cht", ChartType.DOUGHNUT_CHART.getType()); // Doughnut chart type
        params.put(
            "chbr", chartRequestBean.getParametersBean().getChartBorderRadius()); // Border radius
        params.put(
            "chdlp",
            chartRequestBean.getParametersBean().getChartLegendPosition()); // Legend position
      }
      case PIE_CHART -> {
        params.put(COLOR, String.join(",", colors)); // Colors
        params.put("cht", ChartType.PIE_CHART.getType()); // Pie chart type
        params.put(
            "chbr", chartRequestBean.getParametersBean().getChartBorderRadius()); // Border radius
        params.put(
            "chdlp",
            chartRequestBean.getParametersBean().getChartLegendPosition()); // Legend position
      }
      default ->
          throw new IllegalArgumentException(
              "Unsupported chart type: " + chartRequestBean.getChartType());
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

  private static File cropImage(File file, String outputFileName, ChartRequestBean chartRequestBean)
      throws IOException {
    BufferedImage originalImage = ImageIO.read(file);
    BufferedImage croppedImage =
        originalImage.getSubimage(
            chartRequestBean.getCropX(),
            chartRequestBean.getCropY(),
            chartRequestBean.getCropWidth(),
            chartRequestBean.getCropHeight());
    File outputFile = new File("src/main/resources/test/"+outputFileName);
    ImageIO.write(croppedImage, "png", outputFile);
    log.info("Cropped image saved to: {}", outputFile.getAbsolutePath());
    return outputFile;
  }

  private static Image convertToImage(File file) throws IOException {
    ImageData imageData = ImageDataFactory.create(file.getAbsolutePath());
    Image image = new Image(imageData);
    image.scaleToFit(120, 120);
    return image;
  }

  public static ChartRequestBean buildChartRequestBean(
      String fileName,
      ChartType chartType,
      List<RowData> chartDataList,
      List<String> legends,
      String margins) {

    ParametersBean parametersBean =
        ParametersBean.builder()
            .chartTitle(chartType.getName())
            .chartWidth("700x300")
            .chartAxis("x,y")
            .chartBarSettings("40,10,20")
            .chartBorderRadius("8")
            .chartLegendPosition("b")
            .build();

    return switch (chartType) {
      case BAR_VERTICAL_CHART ->
          ChartRequestBean.builder()
              .fileName(fileName)
              .chartType(chartType)
              .chartDataList(chartDataList)
              .legends(legends)
              .margins(margins)
              .cropX(0)
              .cropY(10)
              .cropWidth(690)
              .cropHeight(290)
              .parametersBean(parametersBean)
              .build();
      case PIE_CHART ->
          ChartRequestBean.builder()
              .fileName(fileName)
              .chartType(chartType)
              .chartDataList(chartDataList)
              .legends(legends)
              .margins(margins)
              .cropX(100)
              .cropY(0)
              .cropWidth(500)
              .cropHeight(300)
              .parametersBean(parametersBean)
              .build();
      case DOUGHNUT_CHART ->
          ChartRequestBean.builder()
              .fileName(fileName)
              .chartType(chartType)
              .chartDataList(chartDataList)
              .legends(legends)
              .margins(margins)
              .cropX(100)
              .cropY(10)
              .cropWidth(500)
              .cropHeight(290)
              .parametersBean(parametersBean)
              .build();
      default -> throw new IllegalArgumentException("Unsupported chart type: " + chartType);
    };
  }
}
