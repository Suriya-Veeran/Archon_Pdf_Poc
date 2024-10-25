package com.p3solutions.archon_report_utility;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.p3solutions.archon_report_utility.beans.utils.*;
import com.p3solutions.archon_report_utility.reports.ExecutableClass;
import com.p3solutions.archon_report_utility.utils.ReportUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.p3solutions.archon_report_utility.constants.ColorConstants.*;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA_BOLD;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.*;
import static com.p3solutions.archon_report_utility.constants.HeaderConstants.*;
import static com.p3solutions.archon_report_utility.constants.ImageConstants.ARCHON_LOGO;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.*;
import static com.p3solutions.archon_report_utility.constants.ServiceConstants.*;

public class Main {
    public static void main(String[] args) throws IOException {

        ExecutableClass executableClass = new ReportUtils("/home/p3/IdeaProjects/Pdf_POC/src/main/resources/pdf_report/report.pdf");
        executableClass.reportProcessInitiated();

        executableClass.addEmptyLines(1);

        Table table = executableClass.setTable(buildTableInputBean());

        executableClass.setCellTemplateContent(buildColumnInputBean(),table,buildContentForColumn());

        executableClass.addTableIntoDocument(table);

        executableClass.addEmptyLines(1);

        executableClass.createDivider(buildDividerInputBean(800L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.createDivider(buildDividerInputBean(760L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.createHeaderText(JOB_SUMMARY, BLACK_HEXA_DECIMAL, TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);

        executableClass.setCellTemplateContent(buildColumnInputBean(),table,buildContentForJobColumn());

        executableClass.addTableIntoDocument(table);

        executableClass.createDivider(buildDividerInputBean(590L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.addEmptyLines(1);

        executableClass.createHeaderText(OBJECTIVE_HEADER,BLACK_HEXA_DECIMAL,TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);


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

        executableClass.createDivider(buildDividerInputBean(525L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.addEmptyLines(1);

        executableClass.createHeaderText(ADDITIONAL_DETAILS,BLACK_HEXA_DECIMAL,TextAlignment.LEFT, HEADING_FONT_SIZE, HELVETICA_BOLD);


        executableClass.addHeader(buildHeaderInputBean());

        executableClass.setFooter(buildFooterInputBean());

        executableClass.createDivider(buildDividerInputBean(50L, 1, GREY_SILVER_HEXA_DECIMAL));

        executableClass.documentClose();

    }

    private static TableInputBean buildTableInputBean() {
        return TableInputBean
                .builder()
                .width(100)
                .keepTogether(true)
                .border(Border.NO_BORDER)
                .numberOfColumns(3)
                .build();
    }

    private static ColumnInputBean buildColumnInputBean() {
       return ColumnInputBean
                .builder()
                .border(Border.NO_BORDER)
                .backgroundColor(WHITE)
                .fontSize(DESC_FONT_SIZE)
                .textAlignment(TextAlignment.LEFT)
                .fontColor(hexaDecimalToRGB(PURE_BLACK_HEXA_DECIMAL))
                .font(HELVETICA_BOLD)
                .rowSpan(1)
                .columnSpan(1)
                .cellHeight(CELL_HEIGHT_FOR_HEADER_TABLE)
                .isHeader(true)
                .build();
    }


    private static DividerInputBean buildDividerInputBean(float height,
                                                          float lineWidth,
                                                          String hexaDecimal) {
        return DividerInputBean
                .builder()
                .pageNumber(1)
                .hexaDecimal(hexaDecimal)
                .height(height)
                .lineWidth(lineWidth)
                .build();
    }

    private static HeaderInputBean buildHeaderInputBean() {
       return HeaderInputBean
                .builder()
                .content("Materialized View Report")
                .fontSize(HEADING_FONT_SIZE)
                .backgroundColor(hexaDecimalToRGB(PURE_BLACK_HEXA_DECIMAL))
                .textAlignment(TextAlignment.LEFT)
                .verticalAlignment(VerticalAlignment.TOP)
                .rightMargin(560)
                .topMargin(25)
                .isLogoNeeded(true)
                .imagePath(ARCHON_LOGO)
                .fitHeight(60)
                .fitWidth(60)
                .logoWidth(100)
                .logoHeight(35)
                .logoTextAlignment(TextAlignment.LEFT)
                .logoVerticalAlignment(VerticalAlignment.TOP)
                .build();
    }

    private static FooterInputBean buildFooterInputBean() {
        return FooterInputBean
                .builder()
                .rectangleHeight(0)
                .rectangleWidth(0)
                .url(PLATFORM_3_SOLUTIONS_URL)
                .linkText(PLATFORM_3_SOLUTIONS)
                .copyRightText(COPYRIGHT_2024)
                .allRightsReservedText(ALL_RIGHTS_RESERVED)
                .width(100)
                .textAlignment(TextAlignment.LEFT)
                .verticalAlignment(VerticalAlignment.BOTTOM)
                .fontSize(FOOTER_FONT_SIZE)
                .font(HELVETICA)
                .fontColor(hexaDecimalToRGB(PURE_BLACK_HEXA_DECIMAL))
                .border(Border.NO_BORDER)
                .textAlignmentHeight(820)
                .textAlignmentWidth(560)
                .pageAlignmentHeight(820)
                .pageAlignmentWidth(100)
                .build();
    }

    private static List<String> buildContentForColumn() {
        List<String> contentList = new ArrayList<>();
        contentList.add(GENERATED_BY+"Sysadmin");
        contentList.add(VIEW_ACTIVITY_SESSION_ID+12333727);
        contentList.add(REPORT_GENERATED_TIME+new Date());
        return contentList;
    }

    private static List<String> buildContentForJobColumn() {
        List<String> contentList = new ArrayList<>();
        contentList.add(JOB_TYPE+"Materialized View Refresh Report");
        contentList.add(SCHEDULED_BY+"Sysadmin");
        contentList.add(SCHEDULED_TIME+new Date());
        contentList.add(START_TIME+new Date());
        contentList.add(END_TIME+new Date());
        contentList.add(TOTAL_TIME+new Date());
        contentList.add(APPLICATION_NAME+"App name");
        contentList.add(SCHEMA_NAME+"Schema Name");
        contentList.add(JOB_NAME+"Materialized View");
        return contentList;

    }
}