package jfree.charts.test;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.p3solutions.archon_report_utility.constants.ReportNameConstants;
import com.p3solutions.archon_report_utility.reports.ExecutableClass;
import com.p3solutions.archon_report_utility.utils.ReportUtils;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.RingPlot;
import report.table.RowData;
import report.table.enums.ChartType;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ChartFactory;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.p3solutions.archon_report_utility.constants.ChartTableHeaderConstants.*;
import static com.p3solutions.archon_report_utility.constants.ColorConstants.DIVIDER_LINE_HEXA_DECIMAL;
import static com.p3solutions.archon_report_utility.constants.ColorConstants.GREY_SILVER_HEXA_DECIMAL;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA_BOLD;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.*;
import static utility.AddColorRowToTable.addColorRowToTable;
import static utility.jfreeUtils.JFreeChartUtils.generateJFreeChart;
import static utility.jfreeUtils.JFreeChartUtils.saveChartAsImage;

public class Charts extends ReportUtils {

  public Charts(String outputPath) {
    super(outputPath);
  }

  public static void main(String[] args) throws IOException {
    ExecutableClass executableClass =
        new ReportUtils(
            "/home/p3/IdeaProjects/Pdf_POC/src/main/resources/sampleTest/JfreeChart.pdf");
    executableClass.reportProcessInitiated();

    executableClass.addEmptyLines(1);

    executableClass.createDivider(buildDividerInputBean(800L, 0.5f, DIVIDER_LINE_HEXA_DECIMAL));

    List<RowData> dataList =
        Arrays.asList(
            new RowData("BAE1FF", "Category 1 ", "10"),
            new RowData("008FFF", "Category 2 ", "15"),
            new RowData("FF5733", "Category 3", "20"),
            new RowData("DAF7A6", "Category 4", "25"),
            new RowData("FFC300", "Category 5", "30"),
            new RowData("581845", "Category 6", "35"));

    addChartTableSection(executableClass, ChartType.BAR_VERTICAL_CHART, dataList, false);
    addChartTableSection(executableClass, ChartType.PIE_CHART, dataList, false);
    addChartTableSection(executableClass, ChartType.DOUGHNUT_CHART, dataList, false);

    executableClass.addHeader(
        buildHeaderInputBean(ReportNameConstants.INGESTION_REPORT.getReportName()));

    executableClass.setFooter(buildFooterInputBean());

    executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.documentClose();
  }

  private static void addChartTableSection(
      ExecutableClass executableClass,
      ChartType chartType,
      List<RowData> dataList,
      Boolean tableDescription)
      throws IOException {

    JFreeChart chart = generateJFreeChart(chartType, dataList);

    File chartFile = saveChartAsImage(chart);

    Image chartImage = new Image(ImageDataFactory.create(chartFile.getAbsolutePath()));
    chartImage.setWidth(200);
    chartImage.setHeight(150);

    Table layoutTable = new Table(new float[] {1, 5});
    Cell chartCell = new Cell().add(chartImage).setBorder(Border.NO_BORDER).setPaddingLeft(-30);
    layoutTable.addCell(chartCell);
    if (Boolean.TRUE.equals(tableDescription)) {
      Table labelTable = createLabelTable(dataList);
      layoutTable.addCell(new Cell().add(labelTable).setBorder(Border.NO_BORDER));
    }
    executableClass.addTableIntoDocument(layoutTable);
  }



  private static Table createLabelTable(List<RowData> dataList) throws IOException {
    Table labelTable = new Table(new float[] {3, 12, 5});
    labelTable.setMaxWidth(120);
    labelTable.setMaxHeight(120);
    labelTable.setBorder(Border.NO_BORDER);

    labelTable.addCell(
        new Cell()
            .add(new Paragraph(COLOR_HEADER))
            .setFontSize(4)
            .setBorder(Border.NO_BORDER)
            .setFont(PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI)));
    labelTable.addCell(
        new Cell()
            .add(new Paragraph(LABEL))
            .setFontSize(4)
            .setBorder(Border.NO_BORDER)
            .setFont(PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI)));
    labelTable.addCell(
        new Cell()
            .add(new Paragraph(VALUE))
            .setFontSize(4)
            .setBorder(Border.NO_BORDER)
            .setFont(PdfFontFactory.createFont(HELVETICA_BOLD, PdfEncodings.WINANSI)));

    for (RowData row : dataList) {
      addColorRowToTable(labelTable, row.getColorHex(), row.getLabel(), row.getValue());
    }

    return labelTable;
  }
}
