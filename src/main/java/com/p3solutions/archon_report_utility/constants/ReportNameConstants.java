package com.p3solutions.archon_report_utility.constants;

import com.p3solutions.archon_report_utility.exception.EnumNotFound;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportNameConstants {
    MATERIALIZED_VIEW_REFRESH_REPORT("Materialized View Refresh Report",
            """
                    The materialised view after being created once, needs to be refreshed at intervals to get real time data. Find the details of this job run below. This report gives details on materialised view updates.
                    """,
            "Materialized_View_Refresh.pdf"),

    TABLE_OPTIMIZATION_REPORT("Table Data Optimization Report",
            """
                    This report shows the Storage optimisation achieved after the process run. Table data optimisation jobs identify scope of optimizing the way this data is stored using all the data and metadata information available for that table. This involves techniques like merging data sets , compressing data,cleaning up fragmented storage etc.
                    """,
            "Table_Optimization.pdf");

    private final String reportName;
    private final String description;
    private final String fileName;

    public static ReportNameConstants getReportNameConstants(String reportName) {
        for (ReportNameConstants constants : ReportNameConstants.values()) {
            if (constants.getReportName().equals(reportName)) {
                return constants;
            }
        }
        throw new EnumNotFound(reportName);
    }

}
