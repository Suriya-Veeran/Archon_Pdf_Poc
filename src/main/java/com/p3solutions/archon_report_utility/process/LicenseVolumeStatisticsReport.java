package com.p3solutions.archon_report_utility.process;

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

import java.io.IOException;

import static com.p3solutions.archon_report_utility.constants.ColorConstants.*;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA_BOLD;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.DESC_FONT_SIZE;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.HEADING_FONT_SIZE;
import static com.p3solutions.archon_report_utility.constants.HeaderConstants.*;
import static com.p3solutions.archon_report_utility.constants.report_constants.TableDataOptimizationReportConstants.LEFT_MARGIN_WIDTH;
import static com.p3solutions.archon_report_utility.constants.report_constants.TableDataOptimizationReportConstants.POINT_COLUMN_WIDTH;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.*;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.buildDividerInputBean;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.buildContentForColumn;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.buildContentForJobSummary;

public class LicenseVolumeStatisticsReport extends ReportUtils {
  public LicenseVolumeStatisticsReport(String outputPath) {
    super(outputPath);
  }

  public void generateReport() throws IOException {
    ExecutableClass executableClass = new ReportUtils(outputPath);
    executableClass.reportProcessInitiated();

    executableClass.addEmptyLines(1);

    Table table = executableClass.setTable(buildTableInputBean(), POINT_COLUMN_WIDTH);

    table.setMarginLeft(LEFT_MARGIN_WIDTH);

    executableClass.setCellTemplateContent(
        buildColumnInputBean(),
        table,
        buildContentForColumn(
            ReportNameConstants.LICENSE_VOLUME_STATISTICS_REPORT.getReportName()));

    executableClass.addTableIntoDocument(table);

    executableClass.addEmptyLines(1);

    executableClass.createDivider(buildDividerInputBean(800L, 0.5f, DIVIDER_LINE_HEXA_DECIMAL));

    executableClass.createDivider(buildDividerInputBean(760L, 1, ASH_HEXA_DECIMAL));

    executableClass.addEmptyLines(1);

    executableClass.createHeaderText(
        JOB_SUMMARY, BLACK_HEXA_DECIMAL, TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);

    executableClass.createHalfDivider(buildDividerInputBean(730L, 1, GREY_SILVER_HEXA_DECIMAL));

    Table summaryTable = executableClass.setTable(buildTableInputBean(), POINT_COLUMN_WIDTH);

    summaryTable.setMarginLeft(LEFT_MARGIN_WIDTH);

    executableClass.setCellTemplateContent(
        buildColumnInputBeanForJobSummary(),
        summaryTable,
        buildContentForJobSummary(ReportNameConstants.LICENSE_VOLUME_STATISTICS_REPORT));

    executableClass.addTableIntoDocument(summaryTable);

    executableClass.createHeaderText(
        OBJECTIVE_HEADER,
        BLACK_HEXA_DECIMAL,
        TextAlignment.LEFT,
        HEADING_FONT_SIZE,
        HELVETICA_BOLD);

    executableClass.createHalfDivider(buildDividerInputBean(640L, 1, GREY_SILVER_HEXA_DECIMAL));

    Paragraph objectiveParagraph =
        executableClass.createParagraph(
            ReportNameConstants.LICENSE_VOLUME_STATISTICS_REPORT.getDescription(),
            hexaDecimalToRGB(SMOKY_BLACK_HEXA_DECIMAL),
            TextAlignment.LEFT,
            VerticalAlignment.TOP,
            DESC_FONT_SIZE,
            HELVETICA);

    objectiveParagraph.setMarginLeft(LEFT_MARGIN_WIDTH);

    executableClass.addParagraphIntoDocument(objectiveParagraph);

    executableClass.addEmptyLines(1);

    executableClass.createHeaderText(
            LICENSE_VOLUME_METRICS,
            BLACK_HEXA_DECIMAL,
            TextAlignment.LEFT,
            HEADING_FONT_SIZE,
            HELVETICA_BOLD);

    executableClass.createHalfDivider(buildDividerInputBean(550L, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.addEmptyLines(1);

    Image doughNutImage = executableClass.createChart("doughnut", "Table");
    doughNutImage.scaleToFit(300, 300);

    Image pieChartImage = executableClass.createChart("pie", "Pie Chart");
    pieChartImage.scaleToFit(300, 300);

    Table chartTable = new Table(2);
    chartTable.setMarginLeft(LEFT_MARGIN_WIDTH);

    Cell cell1 = new Cell().add(doughNutImage).setBorder(Border.NO_BORDER).setPaddingLeft(-30);
    Cell cell2 = new Cell().add(pieChartImage).setBorder(Border.NO_BORDER);

    chartTable.addCell(cell1);
    chartTable.addCell(cell2);

    executableClass.addTableIntoDocument(chartTable);

    executableClass.addHeader(
        buildHeaderInputBean(ReportNameConstants.LICENSE_VOLUME_STATISTICS_REPORT.getReportName()));

    executableClass.setFooter(buildFooterInputBean());

    executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.documentClose();

    executableClass.deleteTempImages("src/main/resources/snapFiles");
  }
}
