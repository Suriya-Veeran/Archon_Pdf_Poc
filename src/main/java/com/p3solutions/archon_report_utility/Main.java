package com.p3solutions.archon_report_utility;

import com.p3solutions.archon_report_utility.constants.ReportNameConstants;
import com.p3solutions.archon_report_utility.process.MaterializedViewReport;
import com.p3solutions.archon_report_utility.process.TableOptimizationReport;
import java.io.File;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    ReportNameConstants reportNameConstants = ReportNameConstants.TABLE_OPTIMIZATION_REPORT;
    switch (reportNameConstants) {
      case MATERIALIZED_VIEW_REFRESH_REPORT:
        MaterializedViewReport materializedViewReport =
            new MaterializedViewReport(
                "/home/p3/IdeaProjects/Pdf_POC/src/main/resources/pdf_report"
                    + File.separator
                    + reportNameConstants.getFileName());
        materializedViewReport.generateReport();
        break;
      case TABLE_OPTIMIZATION_REPORT:
        TableOptimizationReport tableOptimizationReport =
            new TableOptimizationReport(
                "/home/p3/IdeaProjects/Pdf_POC/src/main/resources/pdf_report"
                    + File.separator
                    + reportNameConstants.getFileName());
        tableOptimizationReport.generateReport();
        break;
      default:
        break;
    }
  }
}
