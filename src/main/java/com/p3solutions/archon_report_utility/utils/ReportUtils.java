package com.p3solutions.archon_report_utility.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.VerticalAlignment;
import com.p3solutions.archon_report_utility.beans.utils.HeaderInputBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ReportUtils {

    private static final Logger log = LoggerFactory.getLogger(ReportUtils.class);

    public void setHeader(HeaderInputBean headerInputBean) {

        int numberOfPages = headerInputBean.getInputDocument().getPdfDocument().getNumberOfPages();

        for (int i = 1; i <= numberOfPages; i++) {
            Rectangle pageSize = headerInputBean
                    .getInputDocument()
                    .getPdfDocument()
                    .getPage(i)
                    .getPageSize();

            float width = pageSize.getWidth();
            float height = pageSize.getHeight();
            log.info("width: " + width + " height: " + height);

            Paragraph paragraph = createParagraph(headerInputBean.getContent(),
                    headerInputBean.getBackgroundColor(),
                    headerInputBean.getVerticalAlignment(),
                    headerInputBean.getFontSize());

            headerInputBean.getInputDocument()
                    .showTextAligned(paragraph,
                            width - headerInputBean.getRightMargin(),
                            height - headerInputBean.getTopMargin(),
                            headerInputBean.getTextAlignment());

        }
    }

    public Paragraph createParagraph(String content,
                                      Color backgroundColor,
                                      VerticalAlignment verticalAlignment,
                                      int fontSize) {

        return new Paragraph()
                .setFontColor(backgroundColor)
                .setFontSize(fontSize)
                .setVerticalAlignment(verticalAlignment)
                .add(content);

    }


    public void documentPdfClose(PdfDocument pdfDocument) {
        if (pdfDocument != null) {
            pdfDocument.close();
        }
    }

    public void pdfWriterClose(PdfWriter pdfWriter) throws IOException {
        if (pdfWriter != null) {
            pdfWriter.close();
        }
    }

    public void pdfWriterFlush(PdfWriter pdfWriter) throws IOException {
        if (pdfWriter != null) {
            pdfWriter.flush();
        }
    }

    public void documentClose(Document document) {
        if (document != null) {
            document.close();
        }
    }

    public String setReportName(String title,
                                      String extension) {
        return title + "." + extension;
    }


    public PdfWriter createPdfWriter(String location) throws FileNotFoundException {
        return new PdfWriter(location);
    }

    public PdfDocument createPdfDocument(String location) throws FileNotFoundException {
        PdfWriter pdfWriter = new PdfWriter(location);
        return new PdfDocument(pdfWriter);

    }

    public PdfDocument createPdfDocument(PdfWriter pdfWriter) {
        return new PdfDocument(pdfWriter);
    }

    public Document createDocument(String location) throws FileNotFoundException {
        return new Document(createPdfDocument(location), PageSize.A4, false);
    }

    public Document createDocument(String location, PageSize pageSize) throws FileNotFoundException {
        return new Document(createPdfDocument(location), pageSize, false);
    }

    public Table createTable() {
        Table table = new Table(1);
        return table;
    }

    public Table createTable(Document document,String content){
        Table table = new Table(1);
        createCell(table,content);
        document.add(table);
        return table;
    }

    public void createCell(Table table,String content) {
        Cell cell = new Cell(1,1);
        cell.add(new Paragraph(content));
        table.addCell(cell);
    }
}
