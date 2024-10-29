package com.p3solutions.archon_report_utility.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportNameConstants {
   MATERIALIZED_VIEW_REFRESH_REPORT ("Materialized View Refresh Report",
           "The materialised view after being created once, needs to be refreshed at intervals to get real time data. Find the details of this job run below.",
           "This report gives details on materialised view updates. ",
           "Materialized_View_Refresh.pdf"),

   TABLE_OPTIMIZATION_REPORT("Table Data Optimization Report","","","Table_Optimization.pdf" );

   private final String reportName;
   private final String description;
   private final String descriptionHeader;
   private final String fileName;

   public static ReportNameConstants getReportNameConstants(String reportName) {
      for (ReportNameConstants constants : ReportNameConstants.values()) {
         if (constants.getReportName().equals(reportName)) {
            return constants;
         }
      }
      return null;
   }

}
