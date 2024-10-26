package com.p3solutions.archon_report_utility.utils;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.p3solutions.archon_report_utility.beans.utils.*;
import com.p3solutions.archon_report_utility.reports.ExecutableClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.p3solutions.archon_report_utility.constants.ColorConstants.hexaDecimalToRGB;
import static com.p3solutions.archon_report_utility.constants.CommonConstants.OF;
import static com.p3solutions.archon_report_utility.constants.CommonConstants.PAGE;


public class ReportUtils implements ExecutableClass {

    private static final Logger log = LoggerFactory.getLogger(ReportUtils.class);

    private Document document = null;
    private final String outputPath;

    public ReportUtils(String outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public void reportProcessInitiated() throws IOException {
        log.info("Process initiated from Archon ADS");
        File outputFile = new File(outputPath);
        if(!outputFile.exists()){
            outputFile.createNewFile();
        }
        this.document = createDocument(outputFile.getAbsolutePath(), PageSize.A4);
    }

    @Override
    public void addHeader(HeaderInputBean headerInputBean) throws IOException {

        if (headerInputBean == null || document == null) {
            log.warn("HeaderInputBean or document is null. Header addition skipped.");
            return;
        }

        int numberOfPages =this.document.getPdfDocument().getNumberOfPages();

        for (int i = 1; i <= numberOfPages; i++) {
            addHeaderToPage(headerInputBean, i);
        }
    }

    private void addHeaderToPage(HeaderInputBean headerInputBean,
                                 int pageIndex) {
        Rectangle pageSize = document.getPdfDocument().getPage(pageIndex).getPageSize();
        float width = pageSize.getWidth();
        float height = pageSize.getHeight();

        Paragraph paragraph = createParagraph(headerInputBean.getContent(),
                headerInputBean.getBackgroundColor(),
                headerInputBean.getTextAlignment(),
                headerInputBean.getVerticalAlignment(),
                headerInputBean.getFontSize());

        document
                .showTextAligned(paragraph,
                        width - headerInputBean.getRightMargin(),
                        height - headerInputBean.getTopMargin(),
                        headerInputBean.getTextAlignment());

        if (headerInputBean.isLogoNeeded()) {
            Image logoImage = loadImage(headerInputBean.getImagePath(),
                    headerInputBean.getFitWidth(),
                    headerInputBean.getFitHeight());

            if (logoImage != null) {
                document
                        .showTextAligned(createParagraph(logoImage),
                                width - headerInputBean.getLogoWidth(),
                                height - headerInputBean.getLogoHeight(),
                                headerInputBean.getLogoTextAlignment());
            }
        }
    }


    private Image loadImage(String imagePath,
                            float fitWidth,
                            float fitHeight) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(
                Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(imagePath)).readAllBytes())) {
            ImageData imageData = ImageDataFactory.create(ImageIO.read(inputStream), null);
           return new Image(imageData).scaleToFit(fitWidth, fitHeight);
        } catch (IOException e) {
            log.error("Error loading image from path: {}", imagePath, e);
            return null;
        }
    }

    @Override
    public Paragraph createParagraph(Image image) {
        Paragraph paragraph = new Paragraph();
        return paragraph.add(image);
    }


    @Override
    public void setFooter(FooterInputBean footerInputBean){

        if (footerInputBean == null || document == null) {
            log.warn("FooterInputBean or document is null. Footer addition skipped.");
            return;
        }

        int numberOfPages = document.getPdfDocument().getNumberOfPages();

        for (int i = 1; i <= numberOfPages; i++) {
            addFooterToPage(footerInputBean, i, numberOfPages);
        }

    }

    private void addFooterToPage(FooterInputBean footerInputBean,
                                 int pageIndex,
                                 int totalPages) {

        Rectangle rectangle = new Rectangle(footerInputBean.getRectangleWidth(),footerInputBean.getRectangleHeight());

        float width = rectangle.getWidth();
        float height = rectangle.getHeight();

        Paragraph footerText = createFooterParagraph(footerInputBean, rectangle);
        document.showTextAligned(footerText,
                width - footerInputBean.getTextAlignmentWidth(),
                height - footerInputBean.getTextAlignmentHeight(),
                pageIndex,
                footerInputBean.getTextAlignment(),
                footerInputBean.getVerticalAlignment(),
                0);

        String pageText = PAGE + pageIndex + OF + totalPages;
        Paragraph pageNumberParagraph = new Paragraph(pageText)
                .setFontSize(footerInputBean.getFontSize())
                .setFontColor(footerInputBean.getFontColor())
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM);
        document.showTextAligned(pageNumberParagraph,
                width - footerInputBean.getPageAlignmentWidth(),
                height - footerInputBean.getPageAlignmentHeight(),
                pageIndex,
                footerInputBean.getTextAlignment(),
                footerInputBean.getVerticalAlignment(),
                0);
    }

    private Paragraph createFooterParagraph(FooterInputBean footerInputBean,
                                            Rectangle rectangle) {

        PdfLinkAnnotation annotation = new PdfLinkAnnotation(rectangle);
        annotation.setBorder(new PdfArray(new int[]{0,0,0}));

        PdfAction action = PdfAction.createURI(footerInputBean.getUrl());
        annotation.setAction(action);

        Link link = new Link(footerInputBean.getLinkText(), annotation);
        link.setBorder(Border.NO_BORDER);

        return createParagraph(ParagraphInputBean
                .builder()
                .width(footerInputBean.getWidth())
                .verticalAlignment(footerInputBean.getVerticalAlignment())
                .textAlignment(footerInputBean.getTextAlignment())
                .border(footerInputBean.getBorder())
                .fontSize(footerInputBean.getFontSize())
                .fontColor(footerInputBean.getFontColor())
                .firstText(footerInputBean.getCopyRightText())
                .link(link)
                .endText(footerInputBean.getAllRightsReservedText())
                .build());
    }

    @Override
    public Paragraph createParagraph(ParagraphInputBean paragraph){
        return new Paragraph()
                .setWidth(UnitValue.createPercentValue(paragraph.getWidth()))
                .setTextAlignment(paragraph.getTextAlignment())
                .setVerticalAlignment(paragraph.getVerticalAlignment())
                .setFontSize(paragraph.getFontSize())
                .setBorder(paragraph.getBorder())
                .setFontColor(paragraph.getFontColor())
                .add(paragraph.getFirstText())
                .add(paragraph.getLink())
                .add(paragraph.getEndText());
    }

    @Override
    public Paragraph createParagraph(float textWidth,
                                     TextAlignment textAlignment,
                                     VerticalAlignment verticalAlignment,
                                     float fontSize,
                                     Border border,
                                     Color fontColor,
                                     String text){
        return new Paragraph()
                .setWidth(UnitValue.createPercentValue(textWidth))
                .setTextAlignment(textAlignment)
                .setVerticalAlignment(verticalAlignment)
                .setFontSize(fontSize)
                .setBorder(border)
                .setFontColor(fontColor)
                .add(text);
    }

    @Override
    public void createDivider(DividerInputBean dividerInputBean) {

        PdfPage pdfPage = document.getPdfDocument().getPage(dividerInputBean.getPageNumber());
        PdfCanvas canvas = new PdfCanvas(pdfPage);
        canvas.setStrokeColor(hexaDecimalToRGB(dividerInputBean.getHexaDecimal()));

        canvas.moveTo(0, dividerInputBean.getHeight());
        canvas.lineTo(pdfPage.getPageSize().getWidth(),
                dividerInputBean.getHeight());

        canvas.setLineWidth(dividerInputBean.getLineWidth());
        canvas.closePathStroke();

    }

    @Override
    public void createHalfDivider(DividerInputBean dividerInputBean) {
        PdfPage pdfPage = document.getPdfDocument()
                .getPage(dividerInputBean.getPageNumber());
        PdfCanvas canvas = new PdfCanvas(pdfPage);
        canvas.setStrokeColor(hexaDecimalToRGB(dividerInputBean.getHexaDecimal()));
        canvas.moveTo(35, dividerInputBean.getHeight());
        canvas.lineTo(pdfPage.getPageSize().getWidth() - 30,
                dividerInputBean.getHeight());
        canvas.setLineWidth(dividerInputBean.getLineWidth());
        canvas.closePathStroke();
    }

    @Override
    public void createHeaderText(String header,
                                 String hexaDecimal,
                                 TextAlignment textAlignment,
                                 int fontSize,
                                 String font) throws IOException {
        this.document.add(new Paragraph(new Text(header))
                .setTextAlignment(textAlignment)
                .setFontColor(hexaDecimalToRGB(hexaDecimal))
                .setFont(
                        PdfFontFactory.createFont(font, PdfEncodings.WINANSI))
                .setFontSize(fontSize));
    }

    @Override
    public void documentClose() {
        this.document.close();
    }

    @Override
    public Table setTable(TableInputBean tableInputBean) {
        Table table = new Table(tableInputBean.getNumberOfColumns());
        table.setWidth(UnitValue.createPercentValue(tableInputBean.getWidth()));
        table.setKeepTogether(tableInputBean.isKeepTogether());
//        table.setBorder(tableInputBean.getBorder());
        return table;
    }

    @Override
    public Table setTable(TableInputBean tableInputBean, float[] pointColumnWidth) {
        Table table = new Table(UnitValue.createPercentArray(pointColumnWidth));
        table.setWidth(UnitValue.createPercentValue(tableInputBean.getWidth()));
        table.setKeepTogether(tableInputBean.isKeepTogether());
//        table.setBorder(tableInputBean.getBorder());
        return table;
    }


    @Override
    public void setCell(ColumnInputBean columnInputBean,
                        Table table) throws IOException {


        Cell cell = new Cell(columnInputBean.getRowSpan(),columnInputBean.getColumnSpan());

        cell.setTextAlignment(columnInputBean.getTextAlignment());
        cell.setBackgroundColor(columnInputBean.getBackgroundColor());
        cell.setFontSize(columnInputBean.getFontSize());
        cell.setFontColor(columnInputBean.getFontColor());
        cell.setFont(PdfFontFactory.createFont(columnInputBean.getFont()));
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        cell.setHeight(columnInputBean.getCellHeight());
        table.addCell(cell);
    }

    @Override
    public void setCellTemplateContent(ColumnInputBean columnInputBean,
                        Table table,
                        List<String> contentList) throws IOException {


        for (String content : contentList) {
            Cell cell = createCell(columnInputBean);
            cell.add(new Paragraph(content));
            table.addCell(cell);
        }
    }

    @Override
    public void addEmptyLines(int numberOfPages) {
        for (int i = 0; i < numberOfPages; i++)
            this.document.add(new Paragraph(""));

    }

    @Override
    public void addParagraphIntoDocument(Paragraph paragraph){
        this.document.add(paragraph);
    }

    private Cell createCell(ColumnInputBean columnInputBean) throws IOException {
        Cell cell = new Cell(columnInputBean.getRowSpan(), columnInputBean.getColumnSpan());
        cell.setTextAlignment(columnInputBean.getTextAlignment());

        PdfFont font = PdfFontFactory.createFont(columnInputBean.getFont());
        cell.setBackgroundColor(columnInputBean.getBackgroundColor());
        cell.setFontSize(columnInputBean.getFontSize());
        cell.setFontColor(columnInputBean.getFontColor());
        cell.setBorder(columnInputBean.getBorder());
        cell.setFont(font);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        cell.setHeight(columnInputBean.getCellHeight());
        return cell;
    }

    @Override
    public void addTableIntoDocument(Table table) {
        this.document.add(table);
    }


    @Override
    public Paragraph createParagraph(String content,
                                     Color backgroundColor,
                                     TextAlignment textAlignment,
                                     VerticalAlignment verticalAlignment,
                                     int fontSize) {

        return new Paragraph()
                .setFontColor(backgroundColor)
                .setFontSize(fontSize)
                .setTextAlignment(textAlignment)
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
        return new Table(1);
    }

    public Table createTable(Document document, String content){
        Table table = new Table(1);
        createCell(table,content);
        document.add(table);
        return table;
    }

    public void createCell(Table table, String content) {
        Cell cell = new Cell(1,1);
        cell.add(new Paragraph(content));
        table.addCell(cell);
    }


}
