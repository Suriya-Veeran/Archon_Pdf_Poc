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
import static com.p3solutions.archon_report_utility.constants.HeaderConstants.*;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.*;
import static com.p3solutions.archon_report_utility.constants.report_constants.MaterializedViewConstants.LEFT_MARGIN;
import static com.p3solutions.archon_report_utility.constants.report_constants.MaterializedViewConstants.POINT_COLUMN_WIDTH;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.*;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.*;

/**
 * Class responsible for generating the Materialized View Refresh Report.
 * This report provides detailed information about the status and updates
 * of materialized views in the system.
 */

public class MaterializedViewReport extends ReportUtils {

    public MaterializedViewReport(String outputPath) {
        super(outputPath);
    }

    public void generateReport() throws IOException {


        ExecutableClass executableClass = new ReportUtils(outputPath);
        executableClass.reportProcessInitiated();

        executableClass.addEmptyLines(1);

        Table table = executableClass.setTable(buildTableInputBean(), POINT_COLUMN_WIDTH);

        table.setMarginLeft(LEFT_MARGIN);

        executableClass.setCellTemplateContent(buildColumnInputBean(),
                table,
                buildContentForColumn(ReportNameConstants.MATERIALIZED_VIEW_REFRESH_REPORT.getReportName()));

        executableClass.addTableIntoDocument(table);

        executableClass.addEmptyLines(1);

        executableClass.createDivider(buildDividerInputBean(800L, 0.5f, DIVIDER_LINE_HEXA_DECIMAL));

        executableClass.createDivider(buildDividerInputBean(762L, 1, ASH_HEXA_DECIMAL));

        executableClass.addEmptyLines(1);

        executableClass.createHeaderText(
                JOB_SUMMARY, BLACK_HEXA_DECIMAL, TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);

        executableClass.createHalfDivider(buildDividerInputBean(730L, 1, GREY_SILVER_HEXA_DECIMAL));

        Table statusTable = executableClass.setTable(buildTableInputBean());

        executableClass.createJobStatusTable(JOB_STATUS, FAILURE, statusTable);

        Table errorTable = executableClass.setTable(buildTableInputBean());

        executableClass.createJobStatusFailureTable(ERROR_MESSAGE_HEADER, FAILURE, errorTable);

        executableClass.addEmptyLines(1);

        Table summaryTable = executableClass.setTable(buildTableInputBean(), POINT_COLUMN_WIDTH);

        summaryTable.setMarginLeft(LEFT_MARGIN);

        executableClass.setCellTemplateContent(
                buildColumnInputBeanForJobSummary(), summaryTable, buildContentForJobColumn());

        executableClass.addTableIntoDocument(summaryTable);

        executableClass.createHalfDivider(buildDividerInputBean(570L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.addEmptyLines(1);

        executableClass.createHeaderText(
                OBJECTIVE_HEADER,
                BLACK_HEXA_DECIMAL,
                TextAlignment.LEFT,
                HEADING_FONT_SIZE,
                HELVETICA_BOLD);


        Paragraph objectiveParagraph =
                executableClass.createParagraph(
                        ReportNameConstants.MATERIALIZED_VIEW_REFRESH_REPORT.getDescription(),
                        hexaDecimalToRGB(SMOKY_BLACK_HEXA_DECIMAL),
                        TextAlignment.LEFT,
                        VerticalAlignment.TOP,
                        DESC_FONT_SIZE,
                        HELVETICA);

        objectiveParagraph.setMarginLeft(LEFT_MARGIN);

        objectiveParagraph.setMarginRight(LEFT_MARGIN);

        executableClass.addParagraphIntoDocument(objectiveParagraph);

        executableClass.createHalfDivider(buildDividerInputBean(485L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.addEmptyLines(1);

        executableClass.createHeaderText(
                ADDITIONAL_DETAILS,
                BLACK_HEXA_DECIMAL,
                TextAlignment.LEFT,
                HEADING_FONT_SIZE,
                HELVETICA_BOLD);

        Table additionalTable = executableClass.setTable(buildTableInputBean(), POINT_COLUMN_WIDTH);

        additionalTable.setMarginLeft(LEFT_MARGIN);

        executableClass.setCellTemplateContent(
                buildAdditionalInputBean(), additionalTable, buildContentForAdditionalInputColumn());

        executableClass.addTableIntoDocument(additionalTable);

        executableClass.addHeader(
                buildHeaderInputBean(ReportNameConstants.MATERIALIZED_VIEW_REFRESH_REPORT.getReportName()));

        executableClass.setFooter(buildFooterInputBean());

        executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.documentClose();
    }
}
