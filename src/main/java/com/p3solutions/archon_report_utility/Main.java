package com.p3solutions.archon_report_utility;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.p3solutions.archon_report_utility.reports.ExecutableClass;
import com.p3solutions.archon_report_utility.utils.ReportUtils;

import java.io.IOException;

import static com.p3solutions.archon_report_utility.constants.ColorConstants.*;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA_BOLD;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.DESC_FONT_SIZE;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.HEADING_FONT_SIZE;
import static com.p3solutions.archon_report_utility.constants.HeaderConstants.*;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.*;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.*;

public class Main {
    public static void main(String[] args) throws IOException {

        float[] pointColumnWidths = new float[]{350L, 350L, 350L};

        ExecutableClass executableClass = new ReportUtils("C:\\Users\\P3INW82\\IdeaProjects\\Archon_Pdf_Poc\\src\\main\\resources\\pdf_report\\Report.pdf");
        executableClass.reportProcessInitiated();

        executableClass.addEmptyLines(1);

        Table table = executableClass.setTable(buildTableInputBean(), pointColumnWidths);

        executableClass.setCellTemplateContent(buildColumnInputBean(), table, buildContentForColumn());

        executableClass.addTableIntoDocument(table);

        executableClass.addEmptyLines(1);

        executableClass.createDivider(buildDividerInputBean(800L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.createDivider(buildDividerInputBean(760L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.createHeaderText(JOB_SUMMARY, BLACK_HEXA_DECIMAL, TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);

        executableClass.createHalfDivider(buildDividerInputBean(735L, 1, GREY_SILVER_HEXA_DECIMAL));

        Table summaryTable = executableClass.setTable(buildTableInputBean(), pointColumnWidths);

        executableClass.setCellTemplateContent(buildColumnInputBean(), summaryTable, buildContentForJobColumn());

        executableClass.addTableIntoDocument(summaryTable);


        executableClass.createHalfDivider(buildDividerInputBean(600L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.addEmptyLines(1);

        executableClass.createHeaderText(OBJECTIVE_HEADER, BLACK_HEXA_DECIMAL, TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);


        String text = """
                The materialised view after being created once, needs to be refreshed at intervals to get real time data. Find the details of this job run below.
                This report gives details on materialised view updates.
                """;

        Paragraph objectiveParagraph = executableClass.createParagraph(text,
                hexaDecimalToRGB(SMOKY_BLACK_HEXA_DECIMAL),
                TextAlignment.LEFT,
                VerticalAlignment.TOP,
                DESC_FONT_SIZE);

        executableClass.addParagraphIntoDocument(objectiveParagraph);

        executableClass.createHalfDivider(buildDividerInputBean(540L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.addEmptyLines(1);

        executableClass.createHeaderText(ADDITIONAL_DETAILS, BLACK_HEXA_DECIMAL, TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);

        Table additionalTable = executableClass.setTable(buildTableInputBean(), pointColumnWidths);

        executableClass.setCellTemplateContent(buildColumnInputBean(), additionalTable, buildContentForAdditionalInputColumn());

        executableClass.addTableIntoDocument(additionalTable);

        executableClass.addHeader(buildHeaderInputBean());

        executableClass.setFooter(buildFooterInputBean());

        executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.documentClose();

    }

}