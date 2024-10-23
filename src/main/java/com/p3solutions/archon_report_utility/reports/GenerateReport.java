package com.p3solutions.archon_report_utility.reports;

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
                .jobType("There was an idea to bring together aaadadgasdgg dsjadvasjdvvd g Abd")
                .scheduledBy("Sysadmin")
                .scheduledTime("Apr 22 2024 12:12:38 GMT")
                .startTime("Apr 22 2024 12:12:38 GMT")
                .endTime("Apr 22 2024 12:14:38 GMT")
                .totalTime("00:00:08.776")
                .applicationName("The Bat and The Cat - Schrodinger's Cat ")
                .schemaName("Either die hero or live long enough to see ")
                .jobName("Materialized View")
                .recordsBeforeRefresh("0")
                .recordsAfterRefresh("720")
                .build();

    }


    public void reportGeneration() throws IOException {
        try {

            PdfWriter coverWriter = new PdfWriter(REPORT_PDF);
            PdfDocument coverPdfDoc = new PdfDocument(coverWriter);
            Document inputDoc = new Document(coverPdfDoc, PageSize.A4, false);

            FinalResultBean finalResultBean = buildFinalResultBean();

            inputDoc.setMargins(35, 30, 30, 30);

            addEmptyLines(inputDoc,2);

            reportProcess("MATERIALIZED",finalResultBean,inputDoc,coverPdfDoc);

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

    private void reportProcess(String type,
                               FinalResultBean finalResultBean,
                               Document inputDoc,
                               PdfDocument coverPdfDoc) throws IOException {

        switch (type){
            case "MATERIALIZED":
                generateMaterializedReport(finalResultBean,inputDoc,coverPdfDoc);
                break;
            case "INGESTION":
                // generateIngestionReport();
                break;
            default:
                break;
        }

    }

    private void generateMaterializedReport(FinalResultBean finalResultBean,
                                            Document inputDoc,
                                            PdfDocument coverPdfDoc) throws IOException {

        createFullLine(coverPdfDoc,DIVIDER_LINE_HEXA_DECIMAL,800L,0);

        createHeaderTable(inputDoc,finalResultBean);

        createFullLine(coverPdfDoc,ASH_HEXA_DECIMAL,760L,2);

        createJobSummaryTable(inputDoc, finalResultBean, coverPdfDoc);

        createObjectiveSummaryTable(inputDoc, MATERIALIZED_VIEW_REFRESH_REPORT);

        createAdditionalInputTable(inputDoc, finalResultBean);

        populateHeaderAndFooter(coverPdfDoc, inputDoc,MATERIALIZED_VIEW_REFRESH_REPORT.getReportName());

        createFullLine(coverPdfDoc,FOOTER_HEXA_DECIMAL,25L,1);
    }

    private void createFullLine(PdfDocument coverPdfDoc,
                                String hexaDecimal,
                                float height,
                                float lineWidth) {
        PdfPage pdfPage = coverPdfDoc.getPage(1);
        PdfCanvas canvas = new PdfCanvas(pdfPage);
        canvas.setStrokeColor(hexaDecimalToRGB(hexaDecimal));
        canvasWrite(canvas,pdfPage.getPageSize().getWidth(),height,0);
        canvas.setLineWidth(lineWidth);
        canvas.closePathStroke();
    }

    private void canvasWrite(PdfCanvas canvas,
                             float width,
                             float height,
                             float origin) {
        canvas.moveTo(origin, height);
        canvas.lineTo(width, height);
    }

    private void createAdditionalInputTable(Document inputDoc,
                                            FinalResultBean finalResultBean) throws IOException {

        inputDoc.add(new Paragraph(new Text(ADDITIONAL_DETAILS)).setTextAlignment(TextAlignment.LEFT)
                        .setFontColor(hexaDecimalToRGB(BLACK_HEXA_DECIMAL))
                .setFont(
                        PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI))
                .setFontSize(HEADING_FONT_SIZE));

        createLine(inputDoc);

        float[] pointColumnWidths = new float[]{250L, 50L, 250L, 50L};
        Table headerTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
        headerTable.setWidth(UnitValue.createPercentValue(100));
        headerTable.setMarginLeft(-10f);

        populateCellForAdditionalInputTable(headerTable, finalResultBean);

        inputDoc.add(headerTable);

    }

    private void populateCellForAdditionalInputTable(Table headerTable,
                                                     FinalResultBean finalResultBean) throws IOException {

        cellCreation(headerTable,RECORDS_COUNT_BEFORE_REFRESH+finalResultBean.getRecordsBeforeRefresh(),TextAlignment.LEFT,false,false,false);
        cellCreation(headerTable,null,TextAlignment.LEFT,false,false, false);
        cellCreation(headerTable,RECORDS_COUNT_AFTER_REFRESH+finalResultBean.getRecordsAfterRefresh(),TextAlignment.LEFT,false,false, false);
        cellCreation(headerTable,null,TextAlignment.LEFT,false,false,false);

    }

    private void createObjectiveSummaryTable(Document inputDoc,
                                             ReportNameConstants reportNameConstants) throws IOException {
        inputDoc.add(new Paragraph(new Text(OBJECTIVE_HEADER)).setTextAlignment(TextAlignment.LEFT)
                        .setFontColor(hexaDecimalToRGB(BLACK_HEXA_DECIMAL))
                .setFont(PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI))
                .setFontSize(HEADING_FONT_SIZE));

        createLine(inputDoc);

        switch (reportNameConstants.getReportName()){
            case "Materialized View Refresh Report" :
                inputDoc.add(new Paragraph
                        (new Text(MATERIALIZED_VIEW_REFRESH_REPORT.getDescriptionHeader() + MATERIALIZED_VIEW_REFRESH_REPORT.getDescription()))
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontColor(hexaDecimalToRGB(SMOKY_BLACK_HEXA_DECIMAL))
                        .setFont(PdfFontFactory.createFont(TIMES_NEW_ROMAN, PdfEncodings.WINANSI))
                        .setFontSize(DESC_FONT_SIZE));
                break;
            default :
                break;
        }




    }

    private void createHeaderTable(Document inputDoc,
                                   FinalResultBean finalResultBean) throws IOException {

        float[] pointColumnWidths = new float[]{2L, 1.5f ,2L , 1.5f , 2L};
//        Table headerTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
        Table headerTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
        headerTable.setWidth(UnitValue.createPercentValue(100));
        headerTable.setAutoLayout();
//        headerTable.setMarginLeft(-10f);
        headerTable.setMarginTop(-5f);

        populateCellForHeaderTable(headerTable,finalResultBean);

        inputDoc.add(headerTable);
    }


    private void cellCreation(Table headerTable,
                              String text,
                              TextAlignment textAlignment,
                              boolean header,
                              boolean jobStatusNeeded,
                              boolean jobStatus) throws IOException {

        Cell cell = new Cell(1,1);
        Color headerColor = hexaDecimalToRGB(PURE_BLACK_HEXA_DECIMAL);
        Color valueColor = hexaDecimalToRGB(DARK_GREY_HEXA_DECIMAL);

        Color backgroundColor = jobStatus ? hexaDecimalToRGB(LIGHT_GREEN_HEXA_DECIMAL) : hexaDecimalToRGB(RED_HEXA_DECIMAL);

       if(text != null && !text.trim().isEmpty())
       {
           String[] arr = text.split("\\r?\\n");

           if (header) {

               cell.add(new Paragraph(new Text(arr[0]))
                               .setFont(PdfFontFactory.createFont(HELVETICA_BOLD))
                       .setFontColor(jobStatusNeeded ? WHITE : headerColor));
               if (arr.length > 1) {
                   cell.add(new Paragraph(new Text("\t" + arr[1])
                           .setFontColor(jobStatusNeeded ? WHITE : valueColor)));
               }
           }
           else {

               cell.add(new Paragraph(new Text("\t" + arr[0])
                       .setFontColor(jobStatusNeeded ? WHITE : valueColor)));
               if (arr.length > 1) {
                   cell.add(new Paragraph(new Text(arr[1])
                           .setFont(PdfFontFactory.createFont(HELVETICA_BOLD))
                           .setFontColor(jobStatusNeeded ? WHITE : headerColor)));
               }

           }

           cell.setTextAlignment(textAlignment);

       }

       cell.setKeepTogether(true);
        cell.setFontSize(DESC_FONT_SIZE);
        cell.setBackgroundColor(jobStatusNeeded ? backgroundColor : WHITE);
//        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
//        cell.setHorizontalAlignment(HorizontalAlignment.LEFT);
//        cell.setPaddingLeft(10f);
//        cell.setPaddingRight(10f);
//        cell.setHeight(jobStatusNeeded ? STATUS_CELL_HEIGHT_FOR_TABLE : CELL_HEIGHT_FOR_HEADER_TABLE);
        cell.setBorder(Border.NO_BORDER);

        headerTable.addCell(cell);

    }

    private void createJobSummaryTable(Document inputDoc,
                                       FinalResultBean finalResultBean,
                                       PdfDocument coverPdfDoc) throws IOException {

        addEmptyLines(inputDoc,1);

        inputDoc.add(new Paragraph(new Text(JOB_SUMMARY)).setTextAlignment(TextAlignment.LEFT)
                .setFont(PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI))
                .setFontSize(HEADING_FONT_SIZE));

        createHalfLine(coverPdfDoc, GREY_SILVER_HEXA_DECIMAL, 735L, 0);

        createStatusColumn(inputDoc,finalResultBean);

        addEmptyLines(inputDoc,1);

//        float[] pointColumnWidths = new float[]{2L, 0.8f ,2L , 0.8f , 2L};
        float[] pointColumnWidths = new float[]{3L, 0.5f ,3L , 0.5f , 2L};
        Table applicationSummaryFirstTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
        applicationSummaryFirstTable.setWidth(UnitValue.createPercentValue(100));
        applicationSummaryFirstTable.setKeepTogether(true);
        applicationSummaryFirstTable.setAutoLayout();
//        applicationSummaryFirstTable.setMarginLeft(-10f);

        populateCellForJobSummaryTable(applicationSummaryFirstTable,finalResultBean);

        inputDoc.add(applicationSummaryFirstTable);

        addEmptyLines(inputDoc, 1);

        pointColumnWidths = new float[]{150L, 10L, 150L, 10L, 150L, 10L};

        Table applicationSummarySecondTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
        applicationSummarySecondTable.setWidth(UnitValue.createPercentValue(100)).useAllAvailableWidth();
        applicationSummarySecondTable.setMarginLeft(-10f);

        populateCellForJobSummarySecondTable(applicationSummarySecondTable,finalResultBean);

        inputDoc.add(applicationSummarySecondTable);

        addEmptyLines(inputDoc, 1);

        pointColumnWidths = new float[]{150L, 10L, 150L, 10L, 150L, 10L};

        Table applicationSummaryThirdTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
        applicationSummaryThirdTable.setWidth(UnitValue.createPercentValue(100)).useAllAvailableWidth();
        applicationSummaryThirdTable.setMarginLeft(-10f);

        populateCellForJobSummaryThirdTable(applicationSummaryThirdTable,finalResultBean);

        inputDoc.add(applicationSummaryThirdTable);

        addEmptyLines(inputDoc, 1);

    }

    private void populateCellForJobSummaryThirdTable(Table applicationSummaryThirdTable,
                                                     FinalResultBean finalResultBean) throws IOException {

        cellCreation(applicationSummaryThirdTable,APPLICATION_NAME+finalResultBean.getApplicationName(),TextAlignment.LEFT,true,false,false);
        cellCreation(applicationSummaryThirdTable,null,TextAlignment.CENTER,false,false,false);
        cellCreation(applicationSummaryThirdTable,SCHEMA_NAME+finalResultBean.getSchemaName(),TextAlignment.LEFT,true,false,false);
        cellCreation(applicationSummaryThirdTable,null,TextAlignment.CENTER,false,false,false);
        cellCreation(applicationSummaryThirdTable,JOB_NAME+finalResultBean.getJobName(),TextAlignment.LEFT,true,false,false);
        cellCreation(applicationSummaryThirdTable,null,TextAlignment.CENTER,false,false,false);

    }


    private void populateCellForHeaderTable(Table headerTable,
                                            FinalResultBean finalResultBean) throws IOException {


        cellCreation(headerTable,GENERATED_BY+finalResultBean.getGeneratedBy(),TextAlignment.LEFT,true,false,false);
        cellCreation(headerTable,null,TextAlignment.CENTER,false,false,false);
        cellCreation(headerTable,VIEW_ACTIVITY_SESSION_ID+finalResultBean.getViewActivitySessionId(),TextAlignment.LEFT,true,false,false);
        cellCreation(headerTable,null,TextAlignment.CENTER,false,false,false);
        cellCreation(headerTable,REPORT_GENERATED_TIME+finalResultBean.getReportGeneratedTime(),TextAlignment.LEFT,true,false,false);
//        cellCreation(headerTable,null,TextAlignment.CENTER,false,false,false);


    }


    private void populateCellForJobSummaryTable(Table applicationSummaryFirstTable,
                                                FinalResultBean finalResultBean) throws IOException {

        cellCreation(applicationSummaryFirstTable,JOB_TYPE+finalResultBean.getJobType(),TextAlignment.LEFT,true,false,false);
        cellCreation(applicationSummaryFirstTable,null,TextAlignment.LEFT,false,false,false);
        cellCreation(applicationSummaryFirstTable,SCHEDULED_BY+finalResultBean.getScheduledBy(),TextAlignment.LEFT,true,false,false);
        cellCreation(applicationSummaryFirstTable,null,TextAlignment.LEFT,false,false,false);
        cellCreation(applicationSummaryFirstTable,REPORT_GENERATED_TIME+finalResultBean.getStartTime(),TextAlignment.LEFT,true,false,false);
//        cellCreation(applicationSummaryFirstTable,null,TextAlignment.LEFT,false,false,false);

    }


    private void populateCellForJobSummarySecondTable(Table applicationSummarySecondTable,
                                                      FinalResultBean finalResultBean) throws IOException {

        cellCreation(applicationSummarySecondTable,START_TIME+finalResultBean.getStartTime(),TextAlignment.LEFT,true,false,false);
        cellCreation(applicationSummarySecondTable,null,TextAlignment.LEFT,true,false,false);
        cellCreation(applicationSummarySecondTable,END_TIME+finalResultBean.getEndTime(),TextAlignment.LEFT,true,false,false);
        cellCreation(applicationSummarySecondTable,null,TextAlignment.LEFT,true,false,false);
        cellCreation(applicationSummarySecondTable,TOTAL_TIME+finalResultBean.getTotalTime(),TextAlignment.LEFT,true,false,false);
        cellCreation(applicationSummarySecondTable,null,TextAlignment.LEFT,true,false,false);

    }


    private void createHalfLine(PdfDocument coverPdfDoc,
                                String hexaDecimal,
                                long height,
                                int lineWidth) {
        PdfPage pdfPage = coverPdfDoc.getPage(1);
        PdfCanvas canvas = new PdfCanvas(pdfPage);
        canvas.setStrokeColor(hexaDecimalToRGB(hexaDecimal));
        canvasWrite(canvas,pdfPage.getPageSize().getWidth()-30,height,30);
        canvas.setLineWidth(lineWidth);
        canvas.closePathStroke();
    }

    private void createStatusColumn(Document inputDoc,
                                    FinalResultBean finalResultBean) throws IOException {
        Table table = new Table(1);
        table.setWidth(UnitValue.createPercentValue(100));

        boolean isJobSuccess = finalResultBean.getJobStatus().equalsIgnoreCase(SUCCESS);


        cellCreation(table,
                isJobSuccess ? JOB_STATUS+finalResultBean.getJobStatus() : ERROR_MESSAGE_HEADER+ERROR_MESSAGE,
                TextAlignment.LEFT,
                false,
                true,
                isJobSuccess);

        inputDoc.add(table);
    }

    private void createLine(Document inputDoc) {
        SolidLine solidLine = new SolidLine();
        solidLine.setColor(hexaDecimalToRGB(GREY_SILVER_HEXA_DECIMAL));
        solidLine.setLineWidth(0.5f);
        LineSeparator lineSeparator = new LineSeparator(solidLine);
        inputDoc.add(lineSeparator);
    }

    public static void addEmptyLines(Document doc, int n) {
        for (int i = 0; i < n; i++)
            doc.add(new Paragraph(""));
    }

    private void populateHeaderAndFooter(PdfDocument coverPdfDoc,
                                         Document inputDoc,
                                         String reportName) throws IOException {

        int numberOfPages = coverPdfDoc.getNumberOfPages();

        Color color = hexaDecimalToRGB(BLACK_HEXA_DECIMAL);

        PdfFont font = PdfFontFactory.createFont(HELVETICA, PdfEncodings.WINANSI);

        Paragraph headerText = new Paragraph()
                .setFontColor(color)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .add(reportName);

        Rectangle rect = new Rectangle(0, 0);
        PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);

        PdfAction action = PdfAction.createURI(PLATFORM_3_SOLUTIONS_URL);
        annotation.setAction(action);

        Link link = new Link(PLATFORM_3_SOLUTIONS, annotation);

        Paragraph titleText = new Paragraph()
                .setFont(font)
                .setFontSize(9)
                .setBorder(Border.NO_BORDER)
                .setFontColor(hexaDecimalToRGB(WHITE_SMOKE_HEXA_DECIMAL))
                .add(COPYRIGHT_2024)
                .add(link)
                .add(ALL_RIGHTS_RESERVED);

        titleText.setWidth(UnitValue.createPercentValue(100));
        titleText.setTextAlignment(TextAlignment.LEFT);

        byte[] data = this.getClass().getClassLoader().getResourceAsStream(ARCHON_LOGO).readAllBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ImageData imageData = ImageDataFactory.create(ImageIO.read(inputStream), null);
        Image image = new Image(imageData);
        image.scaleToFit(60, 50);
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
            float y = pageSize.getTop() - 20;
            inputDoc.showTextAligned(logo, x, y, i, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
            inputDoc.showTextAligned(headerText, 30, 820, i, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

            inputDoc.showTextAligned(titleText, 30, 10, i, TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);

            inputDoc.showTextAligned(new Paragraph(PAGE + i + OF + numberOfPages)
                            .setFont(font)
                            .setFontSize(9)
                            .setBorder(Border.NO_BORDER)
                            .setFontColor(hexaDecimalToRGB(WHITE_SMOKE_HEXA_DECIMAL)),
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