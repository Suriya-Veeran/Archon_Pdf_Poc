package com.p3solutions.archon_report_utility;

import com.p3solutions.archon_report_utility.constants.ReportNameConstants;
import com.p3solutions.archon_report_utility.exception.ReportGenerationException;
import com.p3solutions.archon_report_utility.process.MaterializedViewReport;
import com.p3solutions.archon_report_utility.process.PurgeReport;
import com.p3solutions.archon_report_utility.process.TableOptimizationReport;
import com.p3solutions.archon_report_utility.yaml_utils.ConfigLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class Main {
  public static void main(String[] args) throws IOException, ReportGenerationException {
    String reportName = ConfigLoader.getInstance().getReportName();
    ReportNameConstants reportNameConstants = ReportNameConstants.getReportNameConstants(reportName);
    String filePath = ConfigLoader.getInstance().getLocation() + File.separator + reportNameConstants.getFileName();

    generateReport(reportNameConstants, filePath);


  }

  private static void generateReport(ReportNameConstants reportNameConstants,
                                     String filePath) throws IOException {

    switch (reportNameConstants) {
      case MATERIALIZED_VIEW_REFRESH_REPORT:
        MaterializedViewReport materializedViewReport =
                new MaterializedViewReport(filePath);
        materializedViewReport.generateReport();
        break;
      case TABLE_OPTIMIZATION_REPORT:
        TableOptimizationReport tableOptimizationReport =
                new TableOptimizationReport(filePath);
        tableOptimizationReport.generateReport();
        break;
      case PURGE_REPORT:
        PurgeReport purgeReport = new PurgeReport(filePath);
        purgeReport.generateReport();
        break;
      default:
          log.info("No Report  generation implemented for: {}", reportNameConstants);
        break;
    }
  }
}
