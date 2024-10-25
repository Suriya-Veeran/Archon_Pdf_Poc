package com.p3solutions.archon_report_utility.reports;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.p3solutions.archon_report_utility.beans.utils.HeaderInputBean;
import com.p3solutions.archon_report_utility.utils.ReportUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;

import static com.p3solutions.archon_report_utility.constants.ColorConstants.PURE_BLACK_HEXA_DECIMAL;
import static com.p3solutions.archon_report_utility.constants.ColorConstants.hexaDecimalToRGB;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.HEADING_FONT_SIZE;

@Slf4j
public class Executable_Class {

    public static final ReportUtils reportUtils = new ReportUtils();

    private static final String report_Location = "C:\\Users\\P3INW82\\IdeaProjects\\Archon_Pdf_Poc\\src\\main\\resources\\pdf_report\\Report.pdf";

    private HeaderInputBean buildHeaderInputBean(Document document) {
        return HeaderInputBean
                .builder()
                .content("Materialized View Report")
                .fontSize(HEADING_FONT_SIZE)
                .backgroundColor(hexaDecimalToRGB(PURE_BLACK_HEXA_DECIMAL))
                .textAlignment(TextAlignment.LEFT)
                .verticalAlignment(VerticalAlignment.TOP)
                .rightMargin(10f)
                .topMargin(5f)
                .inputDocument(document)
                .build();
    }

    public void reportProcessInitiated() throws FileNotFoundException {
        log.info("Process initiated from Archon ADS");

        Document document = reportUtils.createDocument(report_Location, PageSize.A4);
        HeaderInputBean headerInputBean = buildHeaderInputBean(document);
        Table table = reportUtils.createTable(document,headerInputBean.getContent());
////        reportUtils.createCell(table,headerInputBean.getContent());
//        reportUtils.setHeader(headerInputBean);
        reportUtils.documentClose(document);
    }

}
