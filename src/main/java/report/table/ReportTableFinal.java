package report.table;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.p3solutions.archon_report_utility.constants.ReportNameConstants;
import com.p3solutions.archon_report_utility.reports.ExecutableClass;
import com.p3solutions.archon_report_utility.utils.ReportUtils;

import java.io.IOException;

import static com.p3solutions.archon_report_utility.constants.ColorConstants.GREY_SILVER_HEXA_DECIMAL;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.*;

public class ReportTableFinal extends ReportUtils {

  public ReportTableFinal(String outputPath) {
    super(outputPath);
  }

  public static void main(String[] args) throws IOException {

    ExecutableClass executableClass =
        new ReportUtils("/home/p3/IdeaProjects/Pdf_POC/src/main/resources/sampleTest/Test.pdf");
    executableClass.reportProcessInitiated();

    executableClass.addEmptyLines(1);

    Image pieChart = executableClass.createPieChart();

    Table table = new Table(3);

    table.addCell(new Cell().add(new Paragraph("Color")));
    table.addCell(new Cell().add(new Paragraph("Label")));
    table.addCell(new Cell().add(new Paragraph("Value")));

    table.addCell(new Cell().add(new Paragraph("")
            .setBackgroundColor(hexToRgb("BAE1FF"))
            .setWidth(20)
            .setHeight(20)));
    table.addCell(new Cell().add(new Paragraph("Cat1")));
    table.addCell(new Cell().add(new Paragraph("10")));

    table.addCell(new Cell().add(new Paragraph("")
            .setBackgroundColor(hexToRgb("008FFF"))
            .setWidth(20)
            .setHeight(20)));
    table.addCell(new Cell().add(new Paragraph("Cat2")));
    table.addCell(new Cell().add(new Paragraph("15")));

    table.addCell(new Cell().add(new Paragraph("")
            .setBackgroundColor(hexToRgb("FF5733"))
            .setWidth(20)
            .setHeight(20)));
    table.addCell(new Cell().add(new Paragraph("Cat3")));
    table.addCell(new Cell().add(new Paragraph("20")));

    table.addCell(new Cell().add(new Paragraph("")
            .setBackgroundColor(hexToRgb("DAF7A6"))
            .setWidth(20)
            .setHeight(20)));
    table.addCell(new Cell().add(new Paragraph("Cat4")));
    table.addCell(new Cell().add(new Paragraph("25")));

    table.addCell(new Cell().add(new Paragraph("")
            .setBackgroundColor(hexToRgb("FFC300"))
            .setWidth(20)
            .setHeight(20)));
    table.addCell(new Cell().add(new Paragraph("Cat5")));
    table.addCell(new Cell().add(new Paragraph("30")));

    table.addCell(new Cell().add(new Paragraph("")
            .setBackgroundColor(hexToRgb("581845"))
            .setWidth(20)
            .setHeight(20)));
    table.addCell(new Cell().add(new Paragraph("Cat6")));
    table.addCell(new Cell().add(new Paragraph("35")));

    Table layoutTable = new Table(2);

    layoutTable.addCell(new Cell().add(pieChart).setBorder(Border.NO_BORDER));

    layoutTable.addCell(new Cell().add(table).setBorder(Border.NO_BORDER));

    executableClass.addParagraphIntoDocument(new Paragraph().add(layoutTable));

    executableClass.addHeader(
        buildHeaderInputBean(ReportNameConstants.INGESTION_REPORT.getReportName()));

    executableClass.setFooter(buildFooterInputBean());

    executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.documentClose();
  }

  private static DeviceRgb hexToRgb(String hex) {
    int r = Integer.parseInt(hex.substring(0, 2), 16);
    int g = Integer.parseInt(hex.substring(2, 4), 16);
    int b = Integer.parseInt(hex.substring(4, 6), 16);
    return new DeviceRgb(r, g, b);
  }
}
