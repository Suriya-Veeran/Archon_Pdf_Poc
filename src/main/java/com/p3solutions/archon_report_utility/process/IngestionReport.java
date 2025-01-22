package com.p3solutions.archon_report_utility.process;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
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
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.JOB_STATUS;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.SUCCESS;
import static com.p3solutions.archon_report_utility.constants.report_constants.TableDataOptimizationReportConstants.LEFT_MARGIN_WIDTH;
import static com.p3solutions.archon_report_utility.constants.report_constants.TableDataOptimizationReportConstants.POINT_COLUMN_WIDTH;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.*;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.buildContentForColumn;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.buildContentForJobSummary;

public class IngestionReport extends ReportUtils {

    public IngestionReport(String outputPath) {
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

        Image doughNutChart = executableClass.createDoughNutChart();

        pieChart.scaleToFit(300, 300);
        doughNutChart.scaleToFit(250, 350);

        float[] columnWidths = {30f, 70f};

        Table chartTable = executableClass.setTable(buildTableInputBean(), columnWidths);

        chartTable.addCell(new Cell().add(pieChart)
                .setHorizontalAlignment(HorizontalAlignment.LEFT)
                .setBorder(Border.NO_BORDER));


        chartTable.addCell(new Cell().add(doughNutChart).setBorder(Border.NO_BORDER));

        chartTable.setMarginLeft(LEFT_MARGIN_WIDTH);

        executableClass.addTableIntoDocument(chartTable);

        executableClass.addHeader(
                buildHeaderInputBean(ReportNameConstants.INGESTION_REPORT.getReportName()));

        executableClass.setFooter(buildFooterInputBean());

        executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.documentClose();
    }
}
