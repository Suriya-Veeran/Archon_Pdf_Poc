package com.p3solutions.archon_report_utility.utils;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
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
import com.p3solutions.archon_report_utility.LSBSteganography;
import com.p3solutions.archon_report_utility.beans.input_bean.OptimizationStatisticsBean;
import com.p3solutions.archon_report_utility.beans.utils.*;
import com.p3solutions.archon_report_utility.reports.ExecutableClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.p3solutions.archon_report_utility.constants.ColorConstants.*;
import static com.p3solutions.archon_report_utility.constants.CommonConstants.OF;
import static com.p3solutions.archon_report_utility.constants.CommonConstants.PAGE;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA_BOLD;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.ERROR_MESSAGE_HEADER;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.SUCCESS;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.buildTableInputBean;

public class ReportUtils implements ExecutableClass {

    private static final Logger log = LoggerFactory.getLogger(ReportUtils.class);

    private Document document = null;
    protected final String outputPath;

    public ReportUtils(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * Initializes the PDF document.
     */
    @Override
    public void reportProcessInitiated() throws IOException {
        log.info("Process initiated from Archon ADS");
        File outputFile = new File(outputPath);
        if (!outputFile.exists()) {
            log.info("file creation status : {}", outputFile.createNewFile());
        }
        this.document = createDocument(outputFile.getAbsolutePath(), PageSize.A4);
    }

    /**
     * Adds a header to all pages of the document.
     */
    @Override
    public void addHeader(HeaderInputBean headerInputBean) throws IOException {

        if (headerInputBean == null || document == null) {
            log.warn("HeaderInputBean or document is null. Header addition skipped.");
            return;
        }

        int numberOfPages = this.document.getPdfDocument().getNumberOfPages();

        for (int i = 1; i <= numberOfPages; i++) {
            addHeaderToPage(headerInputBean, i);
        }
    }

    private void addHeaderToPage(HeaderInputBean headerInputBean,
                                 int pageIndex) throws IOException {

        Rectangle pageSize = document.getPdfDocument().getPage(pageIndex).getPageSize();
        float width = pageSize.getWidth();
        float height = pageSize.getHeight();

        Paragraph paragraph =
                createParagraph(
                        headerInputBean.getContent(),
                        headerInputBean.getBackgroundColor(),
                        headerInputBean.getTextAlignment(),
                        headerInputBean.getVerticalAlignment(),
                        headerInputBean.getFontSize(),
                        headerInputBean.getFont());

        document.showTextAligned(
                paragraph,
                headerInputBean.getLeftMargin(),
                height - headerInputBean.getTopMargin(),
                headerInputBean.getTextAlignment());

        if (headerInputBean.isLogoNeeded()) {
            Image logoImage =
                    loadImage(
                            headerInputBean.getImagePath(),
                            headerInputBean.getFitWidth(),
                            headerInputBean.getFitHeight());

            if (logoImage != null) {
                document.showTextAligned(
                        createParagraph(logoImage),
                        width - headerInputBean.getFitWidth() - 17,
                        height - headerInputBean.getLogoHeight(),
                        headerInputBean.getLogoTextAlignment());
            }
        }
    }

    /**
     * Loads an image for header/footer if required.
     */
    private Image loadImage(String imagePath, float fitWidth, float fitHeight) {
        try (ByteArrayInputStream inputStream =
                     new ByteArrayInputStream(
                             Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(imagePath))
                                     .readAllBytes())) {
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

    /**
     * Adds footer content to all pages.
     */
    @Override
    public void setFooter(FooterInputBean footerInputBean) {

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

        Rectangle pageSize = document.getPdfDocument().getPage(pageIndex).getPageSize();
        float width = pageSize.getWidth();

        Rectangle rectangle =
                new Rectangle(footerInputBean.getRectangleWidth(), footerInputBean.getRectangleHeight());

        Paragraph footerText = createFooterParagraph(footerInputBean, rectangle);
        document.showTextAligned(
                footerText,
                footerInputBean.getTextAlignmentWidth(),
                footerInputBean.getTextAlignmentHeight(),
                pageIndex,
                footerInputBean.getTextAlignment(),
                footerInputBean.getVerticalAlignment(),
                0);

        String pageText = PAGE + pageIndex + OF + totalPages;
        Paragraph pageNumberParagraph =
                new Paragraph(pageText)
                        .setFontSize(footerInputBean.getFontSize())
                        .setFontColor(footerInputBean.getFontColor())
                        .setTextAlignment(TextAlignment.LEFT)
                        .setVerticalAlignment(VerticalAlignment.BOTTOM);
        document.showTextAligned(
                pageNumberParagraph,
                width - 60,
                footerInputBean.getPageAlignmentHeight(),
                pageIndex,
                footerInputBean.getTextAlignment(),
                footerInputBean.getVerticalAlignment(),
                0);
    }

    private Paragraph createFooterParagraph(FooterInputBean footerInputBean, Rectangle rectangle) {

        PdfLinkAnnotation annotation = new PdfLinkAnnotation(rectangle);
        annotation.setBorder(new PdfArray(new int[]{0, 0, 0}));

        PdfAction action = PdfAction.createURI(footerInputBean.getUrl());
        annotation.setAction(action);

        Link link = new Link(footerInputBean.getLinkText(), annotation);
        link.setBorder(Border.NO_BORDER);

        return createParagraph(
                ParagraphInputBean.builder()
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
    public Paragraph createParagraph(ParagraphInputBean paragraph) {
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
    public Paragraph createParagraph(
            float textWidth,
            TextAlignment textAlignment,
            VerticalAlignment verticalAlignment,
            float fontSize,
            Border border,
            Color fontColor,
            String text) {
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
        canvas.lineTo(pdfPage.getPageSize().getWidth(), dividerInputBean.getHeight());

        canvas.setLineWidth(dividerInputBean.getLineWidth());
        canvas.closePathStroke();
    }

    @Override
    public void createHalfDivider(DividerInputBean dividerInputBean) {
        PdfPage pdfPage = document.getPdfDocument().getPage(dividerInputBean.getPageNumber());
        PdfCanvas canvas = new PdfCanvas(pdfPage);
        canvas.setStrokeColor(hexaDecimalToRGB(dividerInputBean.getHexaDecimal()));
        canvas.moveTo(20, dividerInputBean.getHeight());
        canvas.lineTo(pdfPage.getPageSize().getWidth() - 18, dividerInputBean.getHeight());
        canvas.setLineWidth(dividerInputBean.getLineWidth());
        canvas.closePathStroke();
    }

    @Override
    public void createHeaderText(
            String header, String hexaDecimal, TextAlignment textAlignment, int fontSize, String font)
            throws IOException {
        this.document.add(
                new Paragraph(new Text(header))
                        .setTextAlignment(textAlignment)
                        .setFontColor(hexaDecimalToRGB(hexaDecimal))
                        .setFont(PdfFontFactory.createFont(font, PdfEncodings.WINANSI))
                        .setFontSize(fontSize)
                        .setPaddingLeft(-17));
    }

    @Override
    public void documentClose() {
        this.document.close();
    }

    @Override
    public Table setTable(TableInputBean tableInputBean) {
        Table table = new Table(1);
        table.setWidth(UnitValue.createPercentValue(tableInputBean.getWidth()));
        table.setKeepTogether(tableInputBean.isKeepTogether());
        table.setBorder(tableInputBean.getBorder());
        return table;
    }

    @Override
    public Table setTable(TableInputBean tableInputBean, float[] pointColumnWidth) {
        Table table = new Table(UnitValue.createPercentArray(pointColumnWidth));
        table.setWidth(UnitValue.createPercentValue(tableInputBean.getWidth()));
        table.setKeepTogether(tableInputBean.isKeepTogether());
        table.setBorder(tableInputBean.getBorder());
        return table;
    }

    @Override
    public void setCell(ColumnInputBean columnInputBean, Table table) throws IOException {

        Cell cell = new Cell(columnInputBean.getRowSpan(), columnInputBean.getColumnSpan());

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
    public void setCellTemplateContent(
            ColumnInputBean columnInputBean, Table table, List<String> contentList) throws IOException {

        for (String content : contentList) {
            Cell cell = createCell(columnInputBean, content);
            table.addCell(cell);
        }
    }

    @Override
    public void addEmptyLines(int numberOfPages) {
        for (int i = 0; i < numberOfPages; i++) {
            document.add(new Paragraph(""));
        }
    }

    @Override
    public void addParagraphIntoDocument(Paragraph paragraph) {
        this.document.add(paragraph);
    }

    @Override
    public void createJobStatusFailureTable(String header,
                                            String value,
                                            Table table) throws IOException {

        boolean status = value.equalsIgnoreCase(SUCCESS);

        if (!status) {
            Table errorTable = setTable(buildTableInputBean());
            Cell errorCell = new Cell(1, 1);
            Paragraph errorParagraph = new Paragraph(ERROR_MESSAGE_HEADER + "Schema ad_test_dbo2 is not found")
                    .setFontSize(7)
                    .setFont(PdfFontFactory.createFont(HELVETICA_BOLD))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginTop(2f)
                    .setMarginLeft(4f)
                    .setMarginBottom(2f);
            errorCell.add(errorParagraph);
            errorCell.setBackgroundColor(hexaDecimalToRGB(ERROR_MESSAGE_DECIMAL));
            errorCell.setTextAlignment(TextAlignment.LEFT);
            errorCell.setFontColor(hexaDecimalToRGB(RED_HEXA_DECIMAL));
            errorCell.setWidth(100);
            errorCell.setBorder(Border.NO_BORDER);
            errorCell.setHeight(16);
            errorCell.setKeepTogether(true);

            errorTable.addCell(errorCell);

            errorTable.setMarginLeft(-16);

            errorTable.setMarginRight(-18);

            document.add(errorTable);
        }

    }


    @Override
    public void createJobStatusTable(String header,
                                     String value,
                                     Table statusTable)
            throws IOException {


        Color fontColor =
                value.equalsIgnoreCase(SUCCESS)
                        ? hexaDecimalToRGB(LIGHT_GREEN_HEXA_DECIMAL)
                        : hexaDecimalToRGB(RED_HEXA_DECIMAL);

        Cell cell = new Cell(1, 1);

        Paragraph headerParagraph = new Paragraph(header + value)
                .setFontSize(7)
                .setFont(PdfFontFactory.createFont(HELVETICA_BOLD))
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginTop(2f)
                .setMarginLeft(4f)
                .setMarginBottom(2f);


        cell.setTextAlignment(TextAlignment.LEFT);
        cell.setFontColor(WHITE);
        cell.setWidth(100);
        cell.setBorder(Border.NO_BORDER);
        cell.setHeight(16);
        cell.setBackgroundColor(fontColor);
        cell.setKeepTogether(true);


        cell.add(headerParagraph);

        statusTable.addCell(cell);

        statusTable.setMarginLeft(-16);

        statusTable.setMarginRight(-18);

        document.add(statusTable);

    }

  @Override
  public void createPieChart() throws IOException {
    log.info("pie chart implementation");
  }


    @Override
    public void setHeaderCell(Cell cell, Table table) {
        table.setMarginLeft(-16);
        table.addHeaderCell(cell);
    }

    @Override
    public void setDataCell(Cell cell, Table table) {
        table.setMarginLeft(-16);
        table.addCell(cell);
    }

    @Override
    public void setHiddenWatermarkImage(String location,
                                        String imagePath,
                                        String message) throws IOException {

        BufferedImage coverImage = ImageIO.read(new File(imagePath));


        LSBSteganography.encodeMessage(coverImage, message);

        String modifiedImagePath = location + "/modified-cover-image.png";
        ImageIO.write(coverImage, "png", new File(modifiedImagePath));

        ImageData imageData = ImageDataFactory.create(modifiedImagePath);
        Image image = new Image(imageData);

        image.scaleToFit(200, 200);

        for (int i = 1; i <= document.getPdfDocument().getNumberOfPages(); i++) {
            PdfPage page = document.getPdfDocument().getPage(i);
            Rectangle pageSize = page.getPageSize();

            float x = (pageSize.getWidth() - image.getImageWidth()) / 2;
            float y = (pageSize.getHeight() - image.getImageHeight()) / 2;


            image.setFixedPosition(x, y);
            document.add(image);

        }
    }

    @Override
    public Table setOptimizationStatistics(
            OptimizationStatisticsBean optimizationStatisticsBean,
            Table optimizationSettingTable) {

        float[] columnWidths = {250f, 150f, 150f, 250f};
        optimizationSettingTable = new Table(columnWidths);

        setHeaderCell(createHeaderCell("Description"), optimizationSettingTable);
        setHeaderCell(createHeaderCell("Count of pre-data\noptimization"), optimizationSettingTable);
        setHeaderCell(createHeaderCell("Count of post-data\noptimization"), optimizationSettingTable);
        setHeaderCell(createHeaderCell("Message"), optimizationSettingTable);


        setDataCell(createDataCell("Data snapshots"), optimizationSettingTable);
        setDataCell(createDataCell("2"), optimizationSettingTable);
        setDataCell(createDataCell("1"), optimizationSettingTable);
        setDataCell(createDataCell("1 snapshot removed"), optimizationSettingTable);

        setDataCell(createDataCell("Data files"), optimizationSettingTable);
        setDataCell(createDataCell("8"), optimizationSettingTable);
        setDataCell(createDataCell("1"), optimizationSettingTable);
        setDataCell(createDataCell("8 data files merged as 1"), optimizationSettingTable);

        setDataCell(createDataCell("Size of table"), optimizationSettingTable);
        setDataCell(createDataCell("85.432 KB"), optimizationSettingTable);
        setDataCell(createDataCell("23.745 KB"), optimizationSettingTable);
        setDataCell(createDataCell("Table size reduced to 23.745 KB"), optimizationSettingTable);

        return optimizationSettingTable;
    }

    private static Cell createHeaderCell(String content) {
        Cell cell = new Cell();
        cell.add(new Paragraph(content));
        cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
        cell.setFontColor(WHITE);
        cell.setFontSize(10);
        cell.setTextAlignment(TextAlignment.LEFT);
        cell.setHeight(30);
        cell.setBold();
        return cell;
    }

    private static Cell createDataCell(String content) {
        Cell cell = new Cell();
        cell.add(new Paragraph(content));
        cell.setFontColor(BLACK);
        cell.setFontSize(10);
        cell.setHeight(30);
        cell.setTextAlignment(TextAlignment.LEFT);
        return cell;
    }

    private Cell createCell(ColumnInputBean columnInputBean, String content) throws IOException {
        Cell cell = new Cell(columnInputBean.getRowSpan(), columnInputBean.getColumnSpan());
        return configureCell(columnInputBean, cell, content);
    }

    private Cell configureCell(ColumnInputBean columnInputBean,
                               Cell cell,
                               String content)
            throws IOException {

        Cell cellValue = headerColorDetermination(columnInputBean, cell, content);
        cellValue.setTextAlignment(columnInputBean.getTextAlignment());

        cellValue.setBackgroundColor(columnInputBean.getBackgroundColor());
        cellValue.setBorder(columnInputBean.getBorder());
        cellValue.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cellValue.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        cellValue.setHeight(columnInputBean.getCellHeight());
        return cellValue;
    }

    private Cell headerColorDetermination(ColumnInputBean columnInputBean,
                                          Cell cell,
                                          String content)
            throws IOException {

        Color headerColor = hexaDecimalToRGB(PURE_BLACK_HEXA_DECIMAL);
        Color valueColor = hexaDecimalToRGB(DARK_GREY_HEXA_DECIMAL);


        PdfFont font = columnInputBean.isJobSummaryHeader()
                ? PdfFontFactory.createFont(HELVETICA)
                : PdfFontFactory.createFont(HELVETICA_BOLD);


        if (content != null && !content.trim().isEmpty()) {
            String[] split = content.split("\\r?\\n");
            if (columnInputBean.isHeader()) {
                cell.add(
                        new Paragraph(new Text(split[0]))
                                .setFont(font)
                                .setFontColor(headerColor)
                                .setFontSize(columnInputBean.getFontSize())
                );

                if (split.length > 1) {
                    cell.add(
                            new Paragraph(new Text("\t" + split[1]))
                                    .setFont(font)
                                    .setFontColor(headerColor)
                                    .setFontSize(columnInputBean.getFontSize() - 1f)
                    );
                }
            } else {
                cell.add(
                        new Paragraph(
                                new Text("\t" + split[0])
                                        .setFont(columnInputBean.isValueHeader() ? PdfFontFactory.createFont(HELVETICA) : font)
                                        .setFontColor(valueColor)
                                        .setFontSize(columnInputBean.getFontSize() - 1f)));


                if (split.length > 1) {
                    cell.add(
                            new Paragraph(
                                    new Text(split[1])
                                            .setFont(font)
                                            .setFontColor(headerColor)
                                            .setFontSize(columnInputBean.getFontSize())));
                }
            }
        }

        return cell;
    }

    @Override
    public void addTableIntoDocument(Table table) {
        document.add(table);
    }

    @Override
    public Paragraph createParagraph(
            String content,
            Color backgroundColor,
            TextAlignment textAlignment,
            VerticalAlignment verticalAlignment,
            int fontSize,
            String font)
            throws IOException {

        return new Paragraph()
                .setFont(PdfFontFactory.createFont(font))
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

    public String setReportName(String title, String extension) {
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

    public Table createTable(Document document, String content) {
        Table table = new Table(1);
        createCell(table, content);
        document.add(table);
        return table;
    }

    public void createCell(Table table, String content) {
        Cell cell = new Cell(1, 1);
        cell.add(new Paragraph(content));
        table.addCell(cell);
    }
}
