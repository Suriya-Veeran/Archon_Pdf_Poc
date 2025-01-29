package com.p3solutions.archon_report_utility;

import com.p3solutions.archon_report_utility.constants.ReportNameConstants;
import com.p3solutions.archon_report_utility.exception.ReportGenerationException;
import com.p3solutions.archon_report_utility.process.*;
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
      case INGESTION_REPORT:
        IngestionReport ingestionReport = new IngestionReport(filePath);
        ingestionReport.generateReport();
        break;
      case LICENSE_VOLUME_STATISTICS_REPORT:
        LicenseVolumeStatisticsReport licenseVolumeStatisticsReport = new LicenseVolumeStatisticsReport(filePath);
        licenseVolumeStatisticsReport.generateReport();
        break;
      default:
          log.info("No Report  generation implemented for: {}", reportNameConstants);
        break;
    }
  }
}
