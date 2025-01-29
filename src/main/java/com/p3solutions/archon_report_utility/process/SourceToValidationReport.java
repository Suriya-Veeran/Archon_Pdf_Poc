package com.p3solutions.archon_report_utility.process;

import apache_echarts.beans.chart.DataInfoBean;
import apache_echarts.beans.html_beans.HtmlCreationInfoBean;
import apache_echarts.enums.FormatTypes;
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
import java.util.ArrayList;
import java.util.List;

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
import static com.p3solutions.archon_report_utility.utils.BeanUtils.buildDividerInputBean;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.buildContentForColumn;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.buildContentForJobSummary;
import static utility.ChartBeanUtils.*;
import static utility.ChartBeanUtils.createSeriesInfoBean;

public class SourceToValidationReport extends ReportUtils {
    public SourceToValidationReport(String outputPath) {
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
                buildContentForColumn(ReportNameConstants.SOURCE_TO_VALIDATION_REPORT.getReportName()));

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
                buildContentForJobSummary(ReportNameConstants.SOURCE_TO_VALIDATION_REPORT));

        executableClass.addTableIntoDocument(summaryTable);

        executableClass.createHeaderText(
                OBJECTIVE_HEADER,
                BLACK_HEXA_DECIMAL,
                TextAlignment.LEFT,
                HEADING_FONT_SIZE,
                HELVETICA_BOLD);

        executableClass.createHalfDivider(buildDividerInputBean(480L, 1, GREY_SILVER_HEXA_DECIMAL));

        Paragraph objectiveParagraph =
                executableClass.createParagraph(
                        ReportNameConstants.SOURCE_TO_VALIDATION_REPORT.getDescription(),
                        hexaDecimalToRGB(SMOKY_BLACK_HEXA_DECIMAL),
                        TextAlignment.LEFT,
                        VerticalAlignment.TOP,
                        DESC_FONT_SIZE,
                        HELVETICA);

        objectiveParagraph.setMarginLeft(LEFT_MARGIN_WIDTH);

        executableClass.addParagraphIntoDocument(objectiveParagraph);

        executableClass.createHeaderText(
                SESSION_METRICS, BLACK_HEXA_DECIMAL, TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);

        executableClass.createHalfDivider(buildDividerInputBean(400L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.addEmptyLines(1);

        List<String> pieData = new ArrayList<>();
        pieData.add("Success");
        pieData.add("Failed");

        List<DataInfoBean> dataInfoBeanList = new ArrayList<>();
        dataInfoBeanList.add(buildDataInfoBean("Success", 1, FormatTypes.MB, "#4169E1"));
        dataInfoBeanList.add(buildDataInfoBean("Failed", 10, FormatTypes.KB, "#FF0000"));

        HtmlCreationInfoBean htmlCreationInfoBean =
                createChartConfig(
                        createChartBasicInfo("500px", "400px", "pie"),
                        createTitleConfig("Table", 16, "Arial", "bold", "#333"),
                        createLegendInfoBean(pieData),
                        createSeriesInfoBean(dataInfoBeanList));

        Image tablePieImage = executableClass.createChartUsingBean("pie", htmlCreationInfoBean);
        tablePieImage.scaleToFit(300, 300);

        List<String> filesData = new ArrayList<>();
        filesData.add("Success");
        filesData.add("Failed");

        List<DataInfoBean> filesDataInfoBeanList = new ArrayList<>();
        filesDataInfoBeanList.add(buildDataInfoBean("Success", 5, FormatTypes.MB, "#4169E1"));
        filesDataInfoBeanList.add(buildDataInfoBean("Failed", 1, FormatTypes.MB, "#FF0000"));

        HtmlCreationInfoBean filesHtmlCreationInfoBean =
                createChartConfig(
                        createChartBasicInfo("500px", "400px", "pie"),
                        createTitleConfig("Files", 16, "Arial", "bold", "#333"),
                        createLegendInfoBean(filesData),
                        createSeriesInfoBean(filesDataInfoBeanList));

        Image filePieImage = executableClass.createChartUsingBean("pie", filesHtmlCreationInfoBean);
        filePieImage.scaleToFit(300, 300);

        Table chartTable = new Table(2);
        chartTable.setMarginLeft(LEFT_MARGIN_WIDTH);

        Cell cell1 = new Cell().add(tablePieImage).setBorder(Border.NO_BORDER).setPaddingLeft(-30);
        Cell cell2 = new Cell().add(filePieImage).setBorder(Border.NO_BORDER);

        chartTable.addCell(cell1);
        chartTable.addCell(cell2);

        executableClass.addTableIntoDocument(chartTable);

        executableClass.addHeader(
                buildHeaderInputBean(ReportNameConstants.SOURCE_TO_VALIDATION_REPORT.getReportName()));

        executableClass.setFooter(buildFooterInputBean());

        executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.documentClose();

        executableClass.deleteTempImages("src/main/resources/snapFiles");
    }


}
