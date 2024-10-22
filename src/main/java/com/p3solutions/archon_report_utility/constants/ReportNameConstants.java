package com.p3solutions.archon_report_utility.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportNameConstants {
   MATERIALIZED_VIEW_REFRESH_REPORT ("Materialized View Refresh Report",
           "The materialised view after being created once, needs to be refreshed at intervals to get real time data. Find the details of this job run below.",
           "This report gives details on materialised view updates. ");

   private final String reportName;
   private final String description;
   private final String descriptionHeader;

}
