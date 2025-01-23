package report.table;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.p3solutions.archon_report_utility.constants.ReportNameConstants;
import com.p3solutions.archon_report_utility.reports.ExecutableClass;
import com.p3solutions.archon_report_utility.utils.ReportUtils;
import report.table.requestbean.ChartRequestBean;
import report.table.utils.ChartCreation;
import test.ChartType;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.p3solutions.archon_report_utility.constants.ColorConstants.DIVIDER_LINE_HEXA_DECIMAL;
import static com.p3solutions.archon_report_utility.constants.ColorConstants.GREY_SILVER_HEXA_DECIMAL;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA_BOLD;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.*;

public class AllCharts extends ReportUtils {

  public AllCharts(String outputPath) {
    super(outputPath);
  }

  public static void main(String[] args) throws IOException {
    ExecutableClass executableClass =
        new ReportUtils("/home/p3/IdeaProjects/Pdf_POC/src/main/resources/sampleTest/Sample.pdf");
    executableClass.reportProcessInitiated();

    executableClass.addEmptyLines(1);

    executableClass.createDivider(buildDividerInputBean(800L, 0.5f, DIVIDER_LINE_HEXA_DECIMAL));

    List<RowData> dataList =
        Arrays.asList(
            new RowData("BAE1FF", "Category 1 ", "10"),
            new RowData("008FFF", "Category 2 ", "15"),
            new RowData("FF5733", "Category 3", "20"),
            new RowData("DAF7A6", "Category 4", "25"),
            new RowData("FFC300", "Category 5", "30"),
            new RowData("581845", "Category 6", "35"));

    addChartTableSection(executableClass, ChartType.BAR_VERTICAL_CHART, dataList);

    addChartTableSection(executableClass, ChartType.PIE_CHART, dataList);

    addChartTableSection(executableClass, ChartType.DOUGHNUT_CHART, dataList);

    executableClass.addHeader(
        buildHeaderInputBean(ReportNameConstants.INGESTION_REPORT.getReportName()));

    executableClass.setFooter(buildFooterInputBean());

    executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.documentClose();
  }

  private static void addChartTableSection(
      ExecutableClass executableClass, ChartType chartType, List<RowData> dataList)
      throws IOException {
    Image chart = ChartCreation.generateChart(generateChartRequest(chartType, dataList));

    Table labelTable = new Table(new float[] {3, 12, 5});

    labelTable.setMaxWidth(120);
    labelTable.setMaxHeight(120);
    labelTable.setBorder(Border.NO_BORDER);

    labelTable.addCell(
        new Cell()
            .add(new Paragraph("Color"))
            //            .setBold()
            .setFontSize(4)
            .setBorder(Border.NO_BORDER)
            .setFont(PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI)));
    labelTable.addCell(
        new Cell()
            .add(new Paragraph("Label"))
            //            .setBold()
            .setFontSize(4)
            .setBorder(Border.NO_BORDER)
            .setFont(PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI)));
    labelTable.addCell(
        new Cell()
            .add(new Paragraph("Value"))
            //            .setBold()
            .setFontSize(4)
            .setBorder(Border.NO_BORDER)
            .setFont(PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI)));

    for (RowData row : dataList) {
      addColorRowToTable(labelTable, row.colorHex, row.label, row.value);
    }

    Table layoutTable = new Table(new float[] {1, 5});

    Cell pieChartCell = new Cell().add(chart).setBorder(Border.NO_BORDER).setPaddingLeft(-30);
    layoutTable.addCell(pieChartCell);

    layoutTable.addCell(new Cell().add(labelTable).setBorder(Border.NO_BORDER));

    executableClass.addTableIntoDocument(layoutTable);

    executableClass.deleteTempFiles(new File("src/main/resources/test"));
  }

  private static DeviceRgb hexToRgb(String hex) {
    int r = Integer.parseInt(hex.substring(0, 2), 16);
    int g = Integer.parseInt(hex.substring(2, 4), 16);
    int b = Integer.parseInt(hex.substring(4, 6), 16);
    return new DeviceRgb(r, g, b);
  }

  private static void addColorRowToTable(Table table, String colorHex, String label, String value) {
    table.addCell(
        new Cell()
            .add(
                new Paragraph("")
                    //                    .setFontSize(4)
                    .setBackgroundColor(hexToRgb(colorHex))
                    .setWidth(4)
                    .setHeight(4))
            .setBorder(Border.NO_BORDER));

    table.addCell(
        new Cell()
            .add(new Paragraph(label))
            .setFontSize(5)
            .setTextAlignment(TextAlignment.LEFT)
            .setVerticalAlignment(VerticalAlignment.MIDDLE)
            .setPadding(2)
            .setBorder(Border.NO_BORDER)
            .setWordSpacing(1));

    table.addCell(
        new Cell()
            .add(new Paragraph(value))
            .setFontSize(5)
            .setTextAlignment(TextAlignment.CENTER)
            .setVerticalAlignment(VerticalAlignment.MIDDLE)
            .setPadding(2)
            .setBorder(Border.NO_BORDER)
            .setWordSpacing(1));
  }

  private static ChartRequestBean generateChartRequest(ChartType type, List<RowData> dataList) {
    return switch (type) {
      case BAR_VERTICAL_CHART ->
          ChartCreation.buildChartRequestBean(
              "barchart", ChartType.BAR_VERTICAL_CHART, dataList, List.of(""), "");
      case PIE_CHART ->
          ChartCreation.buildChartRequestBean(
              "pieChart", ChartType.PIE_CHART, dataList, List.of(""), "");
      case DOUGHNUT_CHART ->
          ChartCreation.buildChartRequestBean(
              "doughNutChart", ChartType.DOUGHNUT_CHART, dataList, List.of(""), "");
      default -> throw new IllegalArgumentException("Unsupported chart type: " + type);
    };
  }
}
