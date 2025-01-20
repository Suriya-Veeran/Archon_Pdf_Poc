package com.p3solutions.archon_report_utility.reports;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.p3solutions.archon_report_utility.beans.input_bean.OptimizationStatisticsBean;
import com.p3solutions.archon_report_utility.beans.utils.*;

import java.io.IOException;
import java.util.List;


public interface ExecutableClass {

    void reportProcessInitiated() throws IOException;

    void addHeader(HeaderInputBean headerInputBean) throws IOException;

    void setFooter(FooterInputBean footerInputBean);

    Table setTable(TableInputBean tableInputBean);

    Table setTable(TableInputBean tableInputBean, float[] pointColumnWidth);

    void addTableIntoDocument(Table table);

    void documentClose();

    void setCell(ColumnInputBean columnInputBean, Table table) throws IOException;

    void setCellTemplateContent(ColumnInputBean columnInputBean, Table table, List<String> contentList) throws IOException;

    void addEmptyLines(int numberOfPages);

    Paragraph createParagraph(ParagraphInputBean paragraphInputBean);

    Paragraph createParagraph(Image image);

    Paragraph createParagraph(float textWidth,
                              TextAlignment textAlignment,
                              VerticalAlignment verticalAlignment,
                              float fontSize,
                              Border border,
                              Color fontColor,
                              String text);

    Paragraph createParagraph(String content,
                              Color backgroundColor,
                              TextAlignment textAlignment,
                              VerticalAlignment verticalAlignment,
                              int fontSize,
                              String font) throws IOException;

    void createDivider(DividerInputBean dividerInputBean);

    void createHalfDivider(DividerInputBean dividerInputBean);

    void createHeaderText(String header,
                          String hexaDecimal,
                          TextAlignment textAlignment,
                          int fontSize,
                          String font) throws IOException;

    void addParagraphIntoDocument(Paragraph paragraph);

    void createJobStatusTable(String header, String value, Table statusTable) throws IOException;

    void createPieChart() throws IOException;

    Table setOptimizationStatistics(OptimizationStatisticsBean optimizationStatisticsBean, Table optimizationSettingTable);

    void setHeaderCell(Cell cell, Table table);

    void setDataCell(Cell cell, Table table);

    void setHiddenWatermarkImage(String location, String imagePath, String message ) throws IOException;

    void createJobStatusFailureTable(String errorMessageHeader, String failure, Table errorTable) throws IOException;

}
