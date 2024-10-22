package com.p3solutions.archon_report_utility.reports;

import com.itextpdf.io.font.constants.StandardFonts;
import com.p3solutions.archon_report_utility.beans.CellValueInputBean;
import com.p3solutions.archon_report_utility.beans.FinalResultBean;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.p3solutions.archon_report_utility.constants.ReportNameConstants;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.p3solutions.archon_report_utility.constants.CommonConstants.*;
import static com.p3solutions.archon_report_utility.constants.ColorConstants.*;
import static com.p3solutions.archon_report_utility.constants.FontConstants.*;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.*;
import static com.p3solutions.archon_report_utility.constants.HeaderConstants.*;
import static com.p3solutions.archon_report_utility.constants.ImageConstants.*;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.*;
import static com.p3solutions.archon_report_utility.constants.report_constants.MaterializedViewConstants.*;
import static com.p3solutions.archon_report_utility.constants.ServiceConstants.*;
import static com.p3solutions.archon_report_utility.constants.ReportNameConstants.*;

@Slf4j
public class GenerateReport {

    public static final  String REPORT_PDF = "/home/p3/IdeaProjects/Pdf_POC/src/main/resources/pdf_report/Materialized_View_Report.pdf";


    private static final String ERROR_MESSAGE =
            """
            com.p3solutions.mobius.core.afp_parser.core.afp.exceptions.AFPParserException: An exception occured when parsing structured field at file index position 0x353cd.
at com.p3solutions.mobius.core.afp_parser.core.afp.parser.AFPParser.parseNextSF(AFPParser.java:304)
at com.p3solutions.mobius.core.afp_parser.core.AFPTagsV6.parseAfpContentIntoIndexFile(AFPTagsV6.java:211)
at com.p3solutions.mobius.core.services.extraction.ExtractionHelper.updateIndexColumnIntoWriterBean(ExtractionHelper.java:600)
at com.p3solutions.mobius.core.services.extraction.ExtractionHelper.buildAttachmentFileCreationIntoBlobFolderAFP(ExtractionHelper.java:548)
at com.p3solutions.mobius.core.services.extraction.ExtractionCore.mobiusSetExecutionStarts(ExtractionCore.java:1031)
at com.p3solutions.mobius.core.services.extraction.ExtractionCore.startExtractionForMobius(ExtractionCore.java:316)
at com.p3solutions.mobius.core.services.extraction.ExtractionCore.startArchivalReportForMobius(ExtractionCore.java:202)
at com.p3solutions.mobius.core.services.extraction.ExtractionCore.process(ExtractionCore.java:120)
at com.p3solutions.mobius.core.services.fork_join.CustomForkJoinRecursiveTask.process(CustomForkJoinRecursiveTask.java:46)
at com.p3solutions.mobius.core.services.fork_join.CustomForkJoinRecursiveTask.compute(CustomForkJoinRecursiveTask.java:55)
            """;


    private FinalResultBean buildFinalResultBean(){

        return FinalResultBean.builder()
                .generatedBy("SysAdmin")
                .viewActivitySessionId("123456789012345")
                .reportGeneratedTime("Apr 22 2024 12:14:38 GMT")
                .jobStatus("Success")
                .jobType("There was an idea to bring together")
                .scheduledBy("Sysadmin")
                .scheduledTime("Apr 22 2024 12:12:38 GMT")
                .startTime("Apr 22 2024 12:12:38 GMT")
                .endTime("Apr 22 2024 12:14:38 GMT")
                .totalTime("00:00:08.776")
                .applicationName("The Bat and The Cat - Schrodinger's Cat ")
                .schemaName("Either die hero or live long enough to see ")
                .jobName("Materialized View")
                .build();

    }


    public void reportGeneration() throws IOException {
        try {
            PdfWriter coverWriter = new PdfWriter(REPORT_PDF);
            PdfDocument coverPdfDoc = new PdfDocument(coverWriter);
            Document inputDoc = new Document(coverPdfDoc, PageSize.A4, false);
            inputDoc.setMargins(35, 30, 30, 30);
            addEmptyLines(inputDoc,2);
            createHeaderTable(inputDoc, buildFinalResultBean(), coverPdfDoc);
            createFullLine(coverPdfDoc);
            addEmptyLines(inputDoc,1);
            createJobSummaryTable(inputDoc, buildFinalResultBean(), coverPdfDoc);
            createObjectiveSummaryTable(inputDoc, MATERIALIZED_VIEW_REFRESH_REPORT);
            createAdditionalInputTable(inputDoc);

            populateHeaderAndFooter(coverPdfDoc, inputDoc);

            documentClose(inputDoc);
            documentPdfClose(coverPdfDoc);
            pdfWriterFlush(coverWriter);
            pdfWriterClose(coverWriter);

            log.info("PDF generated successfully with dynamic content.");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    private void createFullLine(PdfDocument coverPdfDoc) {
        PdfPage pdfPage = coverPdfDoc.getPage(1);
        PdfCanvas canvas = new PdfCanvas(pdfPage);
        canvas.moveTo(0, 752);
        canvas.lineTo(700, 752);
        canvas.setLineWidth(0);
        canvas.closePathStroke();
    }

    private void createAdditionalInputTable(Document inputDoc) throws IOException {

        inputDoc.add(new Paragraph(new Text(ADDITIONAL_DETAILS)).setTextAlignment(TextAlignment.LEFT)
                .setFont(
                        PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI))
                .setFontSize(HEADING_FONT_SIZE));
        createLine(inputDoc);

        float[] pointColumnWidths = new float[]{250L, 50L, 250L, 50L};
        Table headerTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
        headerTable.setWidth(UnitValue.createPercentValue(100));
        headerTable.setMarginLeft(-10f);

        populateCell(false, headerTable, RECORDS_COUNT_BEFORE_REFRESH +
                0, TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(true, headerTable,
                null, TextAlignment.CENTER, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(false, headerTable,  RECORDS_COUNT_AFTER_REFRESH+
                702, TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(true, headerTable,
                null, TextAlignment.CENTER, WHITE, CELL_HEIGHT_FOR_TABLE, false);

        inputDoc.add(headerTable);

    }

    private void createObjectiveSummaryTable(Document inputDoc,
                                             ReportNameConstants reportNameConstants) throws IOException {
        inputDoc.add(new Paragraph(new Text(OBJECTIVE_HEADER)).setTextAlignment(TextAlignment.LEFT)
                .setFont(PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI))
                .setFontSize(HEADING_FONT_SIZE));

        createLine(inputDoc);

        switch (reportNameConstants){
            case MATERIALIZED_VIEW_REFRESH_REPORT :
                inputDoc.add(new Paragraph
                        (new Text(MATERIALIZED_VIEW_REFRESH_REPORT.getDescriptionHeader() + MATERIALIZED_VIEW_REFRESH_REPORT.getDescription()))
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFont(PdfFontFactory.createFont(TIMES_NEW_ROMAN, PdfEncodings.WINANSI))
                        .setFontSize(DESC_FONT_SIZE));
                break;
            default :
                break;
        }




    }

    private void createHeaderTable(Document inputDoc,
                                   FinalResultBean finalResultBean,
                                   PdfDocument coverPdfDoc) throws IOException {

        float[] pointColumnWidths = new float[]{200L, 10L, 200L, 10L, 200L, 10L};
        Table headerTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
        headerTable.setWidth(UnitValue.createPercentValue(100));
        headerTable.setMarginLeft(-10f);

        populateCell(false, headerTable, GENERATED_BY +
                finalResultBean.getScheduledBy(), TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_HEADER_TABLE, false);
        populateCell(true, headerTable,
                null, TextAlignment.CENTER, WHITE, CELL_HEIGHT_FOR_HEADER_TABLE, false);
        populateCell(false, headerTable, VIEW_ACTIVITY_SESSION_ID +
                finalResultBean.getViewActivitySessionId(), TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_HEADER_TABLE, false);
        populateCell(true, headerTable,
                null, TextAlignment.CENTER, WHITE, CELL_HEIGHT_FOR_HEADER_TABLE, false);
        populateCell(false, headerTable, REPORT_GENERATED_TIME +
                finalResultBean.getScheduledTime(), TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_HEADER_TABLE, false);
        populateCell(true, headerTable,
                null, TextAlignment.CENTER, WHITE, CELL_HEIGHT_FOR_HEADER_TABLE, false);

        inputDoc.add(headerTable);

        PdfPage pdfPage = coverPdfDoc.getPage(1);
        PdfCanvas canvas = new PdfCanvas(pdfPage);
        canvas.moveTo(0, 795);
        canvas.lineTo(700, 795);
        canvas.setLineWidth(0);
        canvas.closePathStroke();

    }

    private void createJobSummaryTable(Document inputDoc,
                                       FinalResultBean finalResultBean,
                                       PdfDocument coverPdfDoc) throws IOException {

        inputDoc.add(new Paragraph(new Text(JOB_SUMMARY)).setTextAlignment(TextAlignment.LEFT)
                .setFont(PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI))
                .setFontSize(HEADING_FONT_SIZE));

        createLine(inputDoc);

        addEmptyLines(inputDoc,1);

        createStatusColumn(inputDoc,finalResultBean);

        addEmptyLines(inputDoc,1);

        float[] pointColumnWidths = new float[]{150L, 10L, 150L, 10L, 150L, 10L};
        Table applicationSummaryFirstTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
        applicationSummaryFirstTable.setWidth(UnitValue.createPercentValue(100));
        applicationSummaryFirstTable.setMarginLeft(-10f);
        populateCell(true, applicationSummaryFirstTable, JOB_TYPE +
                finalResultBean.getJobType(), TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(false, applicationSummaryFirstTable,
                null, TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(true, applicationSummaryFirstTable, SCHEDULED_BY +
                finalResultBean.getScheduledBy(), TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(false, applicationSummaryFirstTable,
                null, TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(true, applicationSummaryFirstTable, SCHEDULED_TIME+
                finalResultBean.getScheduledTime(), TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(false, applicationSummaryFirstTable,
                null, TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);

        inputDoc.add(applicationSummaryFirstTable);

        addEmptyLines(inputDoc, 1);

        pointColumnWidths = new float[]{150L, 10L, 150L, 10L, 150L, 10L};

        Table applicationSummarySecondTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
        applicationSummarySecondTable.setWidth(UnitValue.createPercentValue(100)).useAllAvailableWidth();
        applicationSummarySecondTable.setMarginLeft(-10f);

        populateCell(true, applicationSummarySecondTable, START_TIME +
                finalResultBean.getStartTime(), TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(false, applicationSummarySecondTable,
                null, TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(true, applicationSummarySecondTable, END_TIME +
                finalResultBean.getEndTime(), TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(false, applicationSummarySecondTable,
                null, TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(true, applicationSummarySecondTable, TOTAL_TIME +
                finalResultBean.getTotalTime(), TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(false, applicationSummarySecondTable,
                null, TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);

        inputDoc.add(applicationSummarySecondTable);

        addEmptyLines(inputDoc, 1);

        pointColumnWidths = new float[]{150L, 10L, 150L, 10L, 150L, 10L};

        Table applicationSummaryThirdTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
        applicationSummaryThirdTable.setWidth(UnitValue.createPercentValue(100)).useAllAvailableWidth();
        applicationSummaryThirdTable.setMarginLeft(-10f);

        populateCell(true, applicationSummaryThirdTable, APPLICATION_NAME +
                finalResultBean.getApplicationName(), TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(false, applicationSummaryThirdTable,
                null, TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(true, applicationSummaryThirdTable, SCHEMA_NAME +
                finalResultBean.getSchemaName(), TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(false, applicationSummaryThirdTable,
                null, TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);
        populateCell(true, applicationSummaryThirdTable, JOB_NAME +
                        finalResultBean.getJobName(), TextAlignment.LEFT, WHITE,
                CELL_HEIGHT_FOR_TABLE, false);
        populateCell(false, applicationSummaryThirdTable,
                null, TextAlignment.LEFT, WHITE, CELL_HEIGHT_FOR_TABLE, false);

        inputDoc.add(applicationSummaryThirdTable);

        addEmptyLines(inputDoc, 1);

        PdfPage pdfPage = coverPdfDoc.getPage(1);
        PdfCanvas canvas = new PdfCanvas(pdfPage);
        canvas.moveTo(0, 795);
        canvas.lineTo(700, 795);
        canvas.setLineWidth(1);
        canvas.closePathStroke();
    }

    private void createStatusColumn(Document inputDoc,
                                    FinalResultBean finalResultBean) throws IOException {
        Table table = new Table(1);
        table.setWidth(UnitValue.createPercentValue(100));


        Color jobStatusColor = finalResultBean.getJobStatus().equalsIgnoreCase(SUCCESS)
                ?  hexaDecimalToRGB(LIGHT_GREEN_HEXA_DECIMAL)
                :  hexaDecimalToRGB(RED_HEXA_DECIMAL);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLDOBLIQUE);

        populateCellUsingBean(buildCellValueInputBean(JOB_STATUS+finalResultBean.getJobStatus(),
                table,
                TextAlignment.LEFT,
                jobStatusColor,
                STATUS_CELL_HEIGHT_FOR_TABLE,
                false,
                true,
                font));

        if(!finalResultBean.getJobStatus().equalsIgnoreCase(SUCCESS)) {
            jobStatusColor = RED;
            populateCell(false,
                    table,
                    ERROR_MESSAGE_HEADER + ERROR_MESSAGE,
                    TextAlignment.LEFT,
                    jobStatusColor,
                    STATUS_CELL_HEIGHT_FOR_TABLE,
                    true);
        }

        inputDoc.add(table);
    }

    private CellValueInputBean buildCellValueInputBean(String text,
                                                       Table table,
                                                       TextAlignment textAlignment,
                                                       Color jobStatusColor,
                                                       int cellHeight,
                                                       boolean header,
                                                       boolean status,
                                                       PdfFont font) {

        return CellValueInputBean.builder()
                .text(text)
                .table(table)
                .textAlignment(textAlignment)
                .backgroundColor(jobStatusColor)
                .cellHeight(cellHeight)
                .isHeader(header)
                .status(status)
                .font(font)
                .build();
    }

    private void populateCell(boolean isHeader,
                              Table table,
                              String text,
                              TextAlignment textAlignment,
                              Color backGroundColor,
                              int cellHeight,
                              boolean status) throws IOException {
        Cell cell = new Cell(1, 1);
        if (text != null && !text.trim().isEmpty()) {

            String[] arr = text.split("\\r?\\n");

            if (isHeader) {
                cell.add(new Paragraph(new Text(arr[0])).setFont(
                        PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI)));
                if (arr.length > 1) {
                    cell.add(new Paragraph(new Text("\t" + arr[1]).setFont(
                            PdfFontFactory.createFont(HELVETICA, PdfEncodings.WINANSI))));
                }
            } else {
                cell.add(new Paragraph(new Text("\t" + arr[0]).setFont(
                        PdfFontFactory.createFont(HELVETICA, PdfEncodings.WINANSI))));
                if (arr.length > 1) {
                    cell.add(new Paragraph(new Text(arr[1]).setFont(
                            PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI))));
                }
            }

            cell.setTextAlignment(textAlignment);
            cell.setFontColor(status ? WHITE : BLACK);
        }
        cell.setFontSize(DESC_FONT_SIZE);
        cell.setBackgroundColor(backGroundColor);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setPaddingLeft(10f);
        cell.setPaddingRight(10f);
        cell.setHeight(cellHeight);
        cell.setBorder(Border.NO_BORDER);

        table.addCell(cell);
    }


    private void populateCellUsingBean(CellValueInputBean cellValueInputBean) throws IOException {
        Cell cell = new Cell(1, 1);
        if (cellValueInputBean.getText() != null && ! cellValueInputBean.getText().trim().isEmpty()) {

            String[] arr = cellValueInputBean.getText().split("\\r?\\n");

            if (cellValueInputBean.isHeader()) {
                cell.add(new Paragraph(new Text(arr[0])).setFont(
                        PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI)));
                if (arr.length > 1) {
                    cell.add(new Paragraph(new Text("\t" + arr[1]).setFont(
                            PdfFontFactory.createFont(HELVETICA, PdfEncodings.WINANSI))));
                }
            } else {
                cell.add(new Paragraph(new Text("\t" + arr[0]).setFont(
                        PdfFontFactory.createFont(HELVETICA, PdfEncodings.WINANSI))));
                if (arr.length > 1) {
                    cell.add(new Paragraph(new Text(arr[1]).setFont(
                            PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI))));
                }
            }

            cell.setTextAlignment(cellValueInputBean.getTextAlignment());
            cell.setFontColor(cellValueInputBean.isStatus() ? WHITE : BLACK);
        }
        cell.setFontSize(DESC_FONT_SIZE);
        cell.setBackgroundColor(cellValueInputBean.getBackgroundColor());
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setPaddingLeft(10f);
        cell.setPaddingRight(10f);
        cell.setHeight(cellValueInputBean.getCellHeight());
        cell.setBorder(Border.NO_BORDER);

        cellValueInputBean.getTable().addCell(cell);
    }


    private void createLine(Document inputDoc) {
        SolidLine solidLine = new SolidLine();
        solidLine.setColor(hexaDecimalToRGB(LIGHT_GREY_LINE));
        LineSeparator lineSeparator = new LineSeparator(solidLine);
        inputDoc.add(lineSeparator);
    }

    public static void addEmptyLines(Document doc, int n) {
        for (int i = 0; i < n; i++)
            doc.add(new Paragraph(""));
    }

    private void populateHeaderAndFooter(PdfDocument coverPdfDoc,
                                         Document inputDoc) throws IOException {

        int numberOfPages = coverPdfDoc.getNumberOfPages();

        PdfFont font = PdfFontFactory.createFont(HELVETICA, PdfEncodings.WINANSI);

        Paragraph headerText = new Paragraph().
                setFont(PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI))
                .setFontSize(16)
                .add(MATERIALIZED_VIEW_REFRESH_REPORT.getReportName());

        Rectangle rect = new Rectangle(0, 0);
        PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);

        PdfAction action = PdfAction.createURI(PLATFORM_3_SOLUTIONS_URL);
        annotation.setAction(action);

        Link link = new Link(PLATFORM_3_SOLUTIONS, annotation);
        Paragraph titleText = new Paragraph().setFont(font).setFontSize(6).add(COPYRIGHT_2024).add(link)
                .add(ALL_RIGHTS_RESERVED);

        titleText.setWidth(UnitValue.createPercentValue(100));
        titleText.setTextAlignment(TextAlignment.LEFT);

        byte[] data = this.getClass().getClassLoader().getResourceAsStream(ARCHON_LOGO).readAllBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ImageData imageData = ImageDataFactory.create(ImageIO.read(inputStream), null);
        Image image = new Image(imageData);
        image.scaleToFit(70, 50);
        Paragraph logo = new Paragraph().add(image);
        for (int i = 1; i <= numberOfPages; i++) {
            Rectangle pageSize = coverPdfDoc.getPage(i).getPageSize();
            PdfPage pdfPage = coverPdfDoc.getPage(i);
            if (i != 1) {
                PdfCanvas canvas = new PdfCanvas(pdfPage);
                canvas.moveTo(0, 795);
                canvas.lineTo(700, 795);
                canvas.setLineWidth(0);
                canvas.closePathStroke();
            }

            float x = 500;
            float y = pageSize.getTop() - 42;
            inputDoc.showTextAligned(logo, x, y, i, TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
            inputDoc.showTextAligned(headerText, 30, 820, i, TextAlignment.LEFT, VerticalAlignment.TOP, 0);

            inputDoc.showTextAligned(titleText, 30, 10, i, TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
            inputDoc.showTextAligned(new Paragraph(PAGE + i + OF + numberOfPages).setFont(font).setFontSize(6),
                    pdfPage.getPageSize().getWidth() - 29, 10, i, TextAlignment.RIGHT, VerticalAlignment.BOTTOM, 0);

        }
    }

    private void documentPdfClose(PdfDocument pdfDocument) {
        if (pdfDocument != null) {
            pdfDocument.close();
        }
    }

    private void pdfWriterClose(PdfWriter pdfWriter) throws IOException {
        if (pdfWriter != null) {
            pdfWriter.close();
        }
    }

    private void pdfWriterFlush(PdfWriter pdfWriter) throws IOException {
        if (pdfWriter != null) {
            pdfWriter.flush();
        }
    }

    private void documentClose(Document document) {
        if (document != null) {
            document.close();
        }
    }

}