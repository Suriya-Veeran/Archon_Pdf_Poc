package com.p3solutions.archon_report_utility.process;

import com.itextpdf.layout.element.Table;
import com.p3solutions.archon_report_utility.constants.ReportNameConstants;
import com.p3solutions.archon_report_utility.reports.ExecutableClass;
import com.p3solutions.archon_report_utility.utils.ReportUtils;

import java.io.IOException;

import static com.p3solutions.archon_report_utility.constants.ColorConstants.*;
import static com.p3solutions.archon_report_utility.constants.report_constants.TableDataOptimizationReportConstants.LEFT_MARGIN_WIDTH;
import static com.p3solutions.archon_report_utility.constants.report_constants.TableDataOptimizationReportConstants.POINT_COLUMN_WIDTH;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.*;
import static com.p3solutions.archon_report_utility.utils.BeanUtils.buildDividerInputBean;
import static com.p3solutions.archon_report_utility.utils.ContentUtils.buildContentForColumn;

public class PurgeReport extends ReportUtils {
  public PurgeReport(String outputPath) {
    super(outputPath);
  }

  public void generateReport() throws IOException {
    ExecutableClass executableClass = new ReportUtils(outputPath);
    executableClass.reportProcessInitiated();

    executableClass.addEmptyLines(1);

    Table table = executableClass.setTable(buildTableInputBean(), POINT_COLUMN_WIDTH);

    table.setMarginLeft(LEFT_MARGIN_WIDTH);

    executableClass.setCellTemplateContent(buildColumnInputBean(),
            table,
            buildContentForColumn(ReportNameConstants.PURGE_REPORT.getReportName()));

    executableClass.addTableIntoDocument(table);

    executableClass.addEmptyLines(1);

    executableClass.createDivider(buildDividerInputBean(800L, 0.5f, DIVIDER_LINE_HEXA_DECIMAL));

    executableClass.createDivider(buildDividerInputBean(760L, 1, ASH_HEXA_DECIMAL));

    executableClass.addEmptyLines(1);

    executableClass.addHeader(buildHeaderInputBean(ReportNameConstants.INGESTION_REPORT.getReportName()));

    executableClass.setFooter(buildFooterInputBean());

    executableClass.createDivider(buildDividerInputBean(30L, 1, GREY_SILVER_HEXA_DECIMAL));

    executableClass.documentClose();


  }
}
