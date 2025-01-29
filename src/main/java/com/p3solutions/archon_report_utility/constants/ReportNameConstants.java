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
            "Table_Optimization.pdf"),

    PURGE_REPORT("Purge Report",
            """
                    The Purge report use case involves identifying and removing outdated or irrelevant data from the system to maintain database efficiency and integrity
                    """,
            "Purge_Report.pdf"),

    INGESTION_REPORT("Ingestion Report",
            """
                    The ingestion report details the process of importing data from various sources into the system. It includes metrics on data volume, ingestion times, and success rates, as well as any errors or issues encountered. This report ensures that the data ingestion process is efficient, accurate, and aligned with organizational requirements, providing a foundation for reliable data analytics and operations.
                    """,
            "Ingestion_Report.pdf"),

  LICENSE_VOLUME_STATISTICS_REPORT(
      "License Volume Statistics Report",
      """
                   The License Volume Statistics Report provides detailed insights into the utilization of software licenses within an organization. It includes metrics on the total number of licenses available, the number of licenses currently in use, and any remaining or unused licenses. This report helps organizations manage compliance, optimize license allocation, and plan for future licensing needs, ensuring cost-effective use of software resources and adherence to licensing agreements.
                   """,
      "License_Volume_Statistics.pdf"),

    SOURCE_TO_VALIDATION_REPORT("Source To Validation Report",
            """
                   The Source to Target Validation Report ensures data integrity and accuracy during data migration or ETL (Extract, Transform, Load) processes. It includes checks to confirm that data from the source matches the data loaded into the target system, verifying record counts, data types, and key business rules. This report helps identify discrepancies early, ensuring reliable and consistent data transfer, which is critical for maintaining data quality in the target system.
                   """,
            "Source_To_Validation_Report.pdf"
            )

    ;

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
