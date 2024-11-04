package com.p3solutions.archon_report_utility.process;

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
import static com.p3solutions.archon_report_utility.constants.HeaderConstants.JOB_SUMMARY;
import static com.p3solutions.archon_report_utility.constants.HeaderConstants.OBJECTIVE_HEADER;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.JOB_STATUS;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.SUCCESS;
import static com.p3solutions.archon_report_utility.constants.report_constants.TableDataOptimizationReportConstants.*;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.*;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.*;

public class TableOptimizationReport extends ReportUtils {

    public TableOptimizationReport(String outputPath) {
        super(outputPath);
    }

    public void generateReport() throws IOException {


        ExecutableClass executableClass = new ReportUtils(outputPath);
        executableClass.reportProcessInitiated();

        executableClass.addEmptyLines(1);

        Table table = executableClass.setTable(buildTableInputBean(), POINT_COLUMN_WIDTH);

        table.setMarginLeft(LEFT_MARGIN_WIDTH);

        executableClass.setCellTemplateContent(buildColumnInputBean(),
                table,
                buildContentForColumn(ReportNameConstants.TABLE_OPTIMIZATION_REPORT.getReportName()));

        executableClass.addTableIntoDocument(table);

        executableClass.addEmptyLines(1);

        executableClass.createDivider(buildDividerInputBean(800L, 0.5f, DIVIDER_LINE_HEXA_DECIMAL));

        executableClass.createDivider(buildDividerInputBean(760L, 1, ASH_HEXA_DECIMAL));

        executableClass.addEmptyLines(1);

        executableClass.createHeaderText(JOB_SUMMARY,
                BLACK_HEXA_DECIMAL,
                TextAlignment.LEFT,
                HEADING_FONT_SIZE,
                HELVETICA_BOLD);

        executableClass.createHalfDivider(buildDividerInputBean(730L, 1, GREY_SILVER_HEXA_DECIMAL));

        Table statusTable = executableClass.setTable(buildTableInputBean());

        executableClass.createJobStatusTable(JOB_STATUS, SUCCESS, statusTable);

        statusTable.setMarginLeft(LEFT_MARGIN_WIDTH);

        Table summaryTable = executableClass.setTable(buildTableInputBean(), POINT_COLUMN_WIDTH);

        summaryTable.setMarginLeft(LEFT_MARGIN_WIDTH);

        executableClass.setCellTemplateContent(buildColumnInputBeanForJobSummary(),
                summaryTable,
                buildContentForJobSummaryForTableData());

        executableClass.addTableIntoDocument(summaryTable);

        executableClass.createHalfDivider(buildDividerInputBean(550L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.createHeaderText(OBJECTIVE_HEADER,
                BLACK_HEXA_DECIMAL,
                TextAlignment.LEFT,
                HEADING_FONT_SIZE,
                HELVETICA_BOLD);


        Paragraph objectiveParagraph = executableClass.createParagraph(
                ReportNameConstants.TABLE_OPTIMIZATION_REPORT.getDescription(),
                hexaDecimalToRGB(SMOKY_BLACK_HEXA_DECIMAL),
                TextAlignment.LEFT,
                VerticalAlignment.TOP,
                DESC_FONT_SIZE,
                HELVETICA);

        objectiveParagraph.setMarginLeft(LEFT_MARGIN_WIDTH);

        executableClass.addParagraphIntoDocument(objectiveParagraph);

        executableClass.addEmptyLines(1);

        executableClass.createHeaderText(OPTIMIZATION_STATISTICS,
                BLACK_HEXA_DECIMAL,
                TextAlignment.LEFT,
                HEADING_FONT_SIZE,
                HELVETICA_BOLD);

        executableClass.createHalfDivider(buildDividerInputBean(475, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.addEmptyLines(1);

        Table optimizationSettingTable = executableClass.setTable(buildTableInputBean(), POINT_COLUMN_WIDTH);

        optimizationSettingTable = executableClass.setOptimizationStatistics
                (buildOptimizationStatisticsBean(buildHeaderValues()),
                        optimizationSettingTable);

        executableClass.addTableIntoDocument(optimizationSettingTable);

        executableClass.addHeader(buildHeaderInputBean(ReportNameConstants.TABLE_OPTIMIZATION_REPORT.getReportName()));

        executableClass.setFooter(buildFooterInputBean());

        executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.documentClose();
    }
}
