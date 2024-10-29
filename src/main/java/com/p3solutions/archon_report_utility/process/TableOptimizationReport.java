package com.p3solutions.archon_report_utility.process;

import static com.p3solutions.archon_report_utility.constants.ColorConstants.*;
import static com.p3solutions.archon_report_utility.constants.ColorConstants.GREY_SILVER_HEXA_DECIMAL;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA_BOLD;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.HEADING_FONT_SIZE;
import static com.p3solutions.archon_report_utility.constants.HeaderConstants.JOB_SUMMARY;
import static com.p3solutions.archon_report_utility.constants.HeaderConstants.OBJECTIVE_HEADER;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.JOB_STATUS;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.SUCCESS;
import static com.p3solutions.archon_report_utility.constants.report_constants.TableDataOptimizationReport.*;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.*;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.*;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.p3solutions.archon_report_utility.constants.ReportNameConstants;
import com.p3solutions.archon_report_utility.reports.ExecutableClass;
import com.p3solutions.archon_report_utility.utils.ReportUtils;
import java.io.IOException;

public class TableOptimizationReport extends ReportUtils {

  public TableOptimizationReport(String outputPath) {
    super(outputPath);
  }

  public void generateReport() throws IOException {

    float[] pointColumnWidths = new float[] {450L, 450L, 450L};

    ExecutableClass executableClass = new ReportUtils(outputPath);
    executableClass.reportProcessInitiated();

    executableClass.addEmptyLines(1);

    Table table = executableClass.setTable(buildTableInputBean(), pointColumnWidths);

    table.setMarginLeft(-18);

    executableClass.setCellTemplateContent(buildColumnInputBean(), table, buildContentForColumn("Table Data Optimization Report"));

    executableClass.addTableIntoDocument(table);

    executableClass.addEmptyLines(1);

    executableClass.createDivider(buildDividerInputBean(800L, 0.5f, DIVIDER_LINE_HEXA_DECIMAL));

    executableClass.createDivider(buildDividerInputBean(760L, 1, ASH_HEXA_DECIMAL));

    executableClass.createHeaderText(
        JOB_SUMMARY, BLACK_HEXA_DECIMAL, TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);

    executableClass.createHalfDivider(buildDividerInputBean(735L, 1, GREY_SILVER_HEXA_DECIMAL));

    Table statusTable = executableClass.setTable(buildTableInputBean());

    executableClass.createJobStatusTable(JOB_STATUS, SUCCESS, statusTable);

    statusTable.setMarginLeft(-18);

    executableClass.addTableIntoDocument(statusTable);

    executableClass.addEmptyLines(1);

    Table summaryTable = executableClass.setTable(buildTableInputBean(), pointColumnWidths);

    summaryTable.setMarginLeft(-18);

    executableClass.setCellTemplateContent(
        buildColumnInputBeanForJobSummary(), summaryTable, buildContentForJobSummaryForTableData());

    executableClass.addTableIntoDocument(summaryTable);

    executableClass.createHalfDivider(buildDividerInputBean(550L, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.createHeaderText(
        OBJECTIVE_HEADER,
        BLACK_HEXA_DECIMAL,
        TextAlignment.LEFT,
        HEADING_FONT_SIZE,
        HELVETICA_BOLD);

    String text =
        """
                This report shows the Storage optimisation achieved after the process run. Table data optimisation jobs identify scope of optimizing the way this data is stored using all the daat and metadata information available for that table. This involves techniques like merging data sets , compressing data,cleaning up fragmented storage etc.
                """;

    Paragraph objectiveParagraph =
        executableClass.createParagraph(
            text,
            hexaDecimalToRGB(SMOKY_BLACK_HEXA_DECIMAL),
            TextAlignment.LEFT,
            VerticalAlignment.TOP,
            8,
            HELVETICA);

    objectiveParagraph.setMarginLeft(-18);

    executableClass.addParagraphIntoDocument(objectiveParagraph);

    executableClass.createHalfDivider(buildDividerInputBean(500, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.addEmptyLines(1);

    executableClass.createHeaderText(
        OPTIMIZATION_STATISTICS,
        BLACK_HEXA_DECIMAL,
        TextAlignment.LEFT,
        HEADING_FONT_SIZE,
        HELVETICA_BOLD);

    executableClass.createHalfDivider(buildDividerInputBean(475, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.addEmptyLines(1);

    Table optimizationSettingTable =
        executableClass.setTable(buildTableInputBean(), pointColumnWidths);

    optimizationSettingTable =
        executableClass.setOptimizationStatistics(
            buildOptimizationStatisticsBean(buildHeaderValues()), optimizationSettingTable);

    executableClass.addTableIntoDocument(optimizationSettingTable);

    executableClass.addHeader(
        buildHeaderInputBean(ReportNameConstants.TABLE_OPTIMIZATION_REPORT.getReportName()));

    executableClass.setFooter(buildFooterInputBean());

    executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.documentClose();
  }
}
