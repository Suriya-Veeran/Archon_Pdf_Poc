package com.p3solutions.archon_report_utility.process;

import com.p3solutions.archon_report_utility.reports.ExecutableClass;
import com.p3solutions.archon_report_utility.utils.ReportUtils;

import java.io.IOException;

public class PurgeReport extends ReportUtils {
  public PurgeReport(String outputPath) {
    super(outputPath);
  }

  public void generateReport() throws IOException {
    ExecutableClass executableClass = new ReportUtils(outputPath);
    executableClass.reportProcessInitiated();
  }
}
