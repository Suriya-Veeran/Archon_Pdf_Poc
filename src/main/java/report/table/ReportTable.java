package report.table;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.p3solutions.archon_report_utility.constants.ReportNameConstants;
import com.p3solutions.archon_report_utility.reports.ExecutableClass;
import com.p3solutions.archon_report_utility.utils.ReportUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.p3solutions.archon_report_utility.constants.ChartTableHeaderConstants.*;
import static com.p3solutions.archon_report_utility.constants.ColorConstants.*;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA_BOLD;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.DESC_FONT_SIZE;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.HEADING_FONT_SIZE;
import static com.p3solutions.archon_report_utility.constants.HeaderConstants.*;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.JOB_STATUS;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.SUCCESS;
import static com.p3solutions.archon_report_utility.constants.report_constants.TableDataOptimizationReportConstants.LEFT_MARGIN_WIDTH;
import static com.p3solutions.archon_report_utility.constants.report_constants.TableDataOptimizationReportConstants.POINT_COLUMN_WIDTH;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.*;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.buildContentForColumn;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.buildContentForJobSummary;

public class ReportTable extends ReportUtils {

  public ReportTable(String outputPath) {
    super(outputPath);
  }

  public static void main(String[] args) throws IOException {

    ExecutableClass executableClass =
        new ReportUtils("/home/p3/IdeaProjects/Pdf_POC/src/main/resources/sampleTest/Test.pdf");
    executableClass.reportProcessInitiated();

    executableClass.addEmptyLines(1);

    Table table = executableClass.setTable(buildTableInputBean(), POINT_COLUMN_WIDTH);

    table.setMarginLeft(LEFT_MARGIN_WIDTH);

    executableClass.setCellTemplateContent(
        buildColumnInputBean(),
        table,
        buildContentForColumn(ReportNameConstants.INGESTION_REPORT.getReportName()));

    executableClass.addTableIntoDocument(table);

    executableClass.addEmptyLines(1);

    executableClass.createDivider(buildDividerInputBean(800L, 0.5f, DIVIDER_LINE_HEXA_DECIMAL));

    executableClass.createDivider(buildDividerInputBean(760L, 1, ASH_HEXA_DECIMAL));

    executableClass.addEmptyLines(1);

    executableClass.createHeaderText(
        JOB_SUMMARY, BLACK_HEXA_DECIMAL, TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);

    executableClass.createHalfDivider(buildDividerInputBean(730L, 1, GREY_SILVER_HEXA_DECIMAL));

    Table statusTable = executableClass.setTable(buildTableInputBean());

    executableClass.createJobStatusTable(JOB_STATUS, SUCCESS, statusTable);

    statusTable.setMarginLeft(LEFT_MARGIN_WIDTH);

    Table summaryTable = executableClass.setTable(buildTableInputBean(), POINT_COLUMN_WIDTH);

    summaryTable.setMarginLeft(LEFT_MARGIN_WIDTH);

    executableClass.setCellTemplateContent(
        buildColumnInputBeanForJobSummary(),
        summaryTable,
        buildContentForJobSummary(ReportNameConstants.INGESTION_REPORT));

    executableClass.addTableIntoDocument(summaryTable);

    executableClass.createHalfDivider(buildDividerInputBean(500L, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.createHeaderText(
        OBJECTIVE_HEADER,
        BLACK_HEXA_DECIMAL,
        TextAlignment.LEFT,
        HEADING_FONT_SIZE,
        HELVETICA_BOLD);

    executableClass.createHalfDivider(buildDividerInputBean(480L, 1, GREY_SILVER_HEXA_DECIMAL));

    Paragraph objectiveParagraph =
        executableClass.createParagraph(
            ReportNameConstants.INGESTION_REPORT.getDescription(),
            hexaDecimalToRGB(SMOKY_BLACK_HEXA_DECIMAL),
            TextAlignment.LEFT,
            VerticalAlignment.TOP,
            DESC_FONT_SIZE,
            HELVETICA);

    objectiveParagraph.setMarginLeft(LEFT_MARGIN_WIDTH);

    executableClass.addParagraphIntoDocument(objectiveParagraph);

    executableClass.addEmptyLines(1);

    executableClass.createHeaderText(
        SESSION_METRICS, BLACK_HEXA_DECIMAL, TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);

    executableClass.createHalfDivider(buildDividerInputBean(405, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.addEmptyLines(1);

    Image pieChart = executableClass.createPieChart();

    Table labelTable = new Table(new float[] {2, 10, 3});

    labelTable.addCell(new Cell().add(new Paragraph(COLOR_HEADER)));
    labelTable.addCell(new Cell().add(new Paragraph(LABEL)));
    labelTable.addCell(new Cell().add(new Paragraph(VALUE)));

    List<RowData> dataList =
        Arrays.asList(
            new RowData("BAE1FF", "Category 1 ", "10"),
            new RowData("008FFF", "Category 2 ", "15"),
            new RowData("FF5733", "Category 3", "20"),
            new RowData("DAF7A6", "Category 4", "25"),
            new RowData("FFC300", "Category 5", "30"),
            new RowData("581845", "Category 6", "35"));

    for (RowData row : dataList) {
      addColorRowToTable(labelTable, row.colorHex, row.label, row.value);
    }

    Table layoutTable = new Table(new float[] {1, 5});

    Cell pieChartCell = new Cell().add(pieChart).setBorder(Border.NO_BORDER).setPaddingLeft(-108);

    layoutTable.addCell(pieChartCell);

    layoutTable.addCell(new Cell().add(labelTable).setBorder(Border.NO_BORDER));

    executableClass.addParagraphIntoDocument(new Paragraph().add(layoutTable));

    layoutTable.setHorizontalAlignment(HorizontalAlignment.LEFT);
    layoutTable.setFixedPosition(36, 700, 523);

    Image doughNutChart = executableClass.createDoughNutChart();

    Table doughNutLabelTable = new Table(new float[] {2, 10, 3});

    doughNutLabelTable.addCell(new Cell().add(new Paragraph(COLOR_HEADER)));
    doughNutLabelTable.addCell(new Cell().add(new Paragraph(LABEL)));
    doughNutLabelTable.addCell(new Cell().add(new Paragraph(VALUE)));

    List<RowData> doughNutDataList =
        Arrays.asList(
            new RowData("008FFF", "Category A", "5"),
            new RowData("264653", "Category B", "10"),
            new RowData("2A9D8F", "Category C", "15"),
            new RowData("E9C46A", "Category D", "20"));

    for (RowData row : doughNutDataList) {
      addColorRowToTable(doughNutLabelTable, row.colorHex, row.label, row.value);
    }

    Table doughNutLayoutTable = new Table(new float[] {1, 5});

    Cell doughNutChartCell =
        new Cell().add(doughNutChart).setBorder(Border.NO_BORDER).setPaddingLeft(-108);

    doughNutLayoutTable.addCell(doughNutChartCell);
    doughNutLayoutTable.addCell(new Cell().add(doughNutLabelTable).setBorder(Border.NO_BORDER));

    executableClass.addParagraphIntoDocument(new Paragraph().add(doughNutLayoutTable));

    doughNutLayoutTable.setHorizontalAlignment(HorizontalAlignment.LEFT);

    executableClass.addHeader(
        buildHeaderInputBean(ReportNameConstants.INGESTION_REPORT.getReportName()));

    executableClass.setFooter(buildFooterInputBean());

    executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.documentClose();
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
                    .setFontSize(8)
                    .setBackgroundColor(hexToRgb(colorHex))
                    .setWidth(20)
                    .setHeight(20)));

    table.addCell(
        new Cell()
            .add(new Paragraph(label))
            .setFontSize(8)
            .setTextAlignment(TextAlignment.LEFT)
            .setVerticalAlignment(VerticalAlignment.MIDDLE)
            .setPadding(2)
            .setWordSpacing(1));

    table.addCell(
        new Cell()
            .add(new Paragraph(value))
            .setFontSize(8)
            .setTextAlignment(TextAlignment.CENTER)
            .setVerticalAlignment(VerticalAlignment.MIDDLE)
            .setPadding(2)
            .setWordSpacing(1));
  }
}
