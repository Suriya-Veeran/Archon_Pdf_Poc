package com.p3solutions.archon_report_utility.utils;

import com.p3solutions.archon_report_utility.constants.ReportNameConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.p3solutions.archon_report_utility.constants.HeaderConstants.*;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.*;
import static com.p3solutions.archon_report_utility.constants.report_constants.MaterializedViewConstants.RECORDS_COUNT_AFTER_REFRESH;
import static com.p3solutions.archon_report_utility.constants.report_constants.MaterializedViewConstants.RECORDS_COUNT_BEFORE_REFRESH;
import static com.p3solutions.archon_report_utility.constants.report_constants.TableDataOptimizationReportConstants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentUtils {

    private static final String SYS_ADMIN = "Sysadmin";

    public static List<String> buildContentForColumn(String name) {
        ReportNameConstants reportNameConstants = ReportNameConstants.getReportNameConstants(name);

        List<String> contentList = new ArrayList<>();
        contentList.add(GENERATED_BY + SYS_ADMIN);
        switch (Objects.requireNonNull(reportNameConstants)) {
            case MATERIALIZED_VIEW_REFRESH_REPORT :
                contentList.add(VIEW_ACTIVITY_SESSION_ID + 12333727);
                 break;
            case TABLE_OPTIMIZATION_REPORT, INGESTION_REPORT:
                contentList.add(JOB_NAME+"Test Job Name");
                break;
            default:
                break;
        }

        contentList.add(REPORT_GENERATED_TIME + new Date());
        return contentList;

    }

    public static List<String> buildContentForJobSummary(ReportNameConstants type){
        List<String> contentList = new ArrayList<>();
        switch (Objects.requireNonNull(type)) {
            case MATERIALIZED_VIEW_REFRESH_REPORT:
                contentList.add(JOB_TYPE + "Materialized View Refresh Report");
                addCommonContent(contentList);
                contentList.add(JOB_NAME + "Materialized View");
                break;
            case TABLE_OPTIMIZATION_REPORT:
                contentList.add(JOB_INSTANCE_ID + "0123546474-5252");
                contentList.add(JOB_TYPE + "Table Data Optimization");
                addCommonContent(contentList);
                contentList.add(TABLE_NAME + "Address Table");
                break;
            case INGESTION_REPORT:
                addCommonContent(contentList);
                contentList.add(TABLE_NAME+"Claim");
                contentList.add(INGESTION_TYPE+"REST");
                contentList.add(INGESTION_MODE+"Ingest Data");
                contentList.add(SOURCE_DATA_FILE_STORAGE_PROFILE_NAME+"Local File System");
                contentList.add(STORAGE_TYPE+"Default Storage");
                contentList.add(BUCKET_NAME+"N/A");
                contentList.add(BUCKET_TYPE+"N/A");
                contentList.add(REGION+"N/A");
                contentList.add(SOURCE_PATH+"/home/p3/IdeaProjects/Pdf_POC/src/main/resources");
                contentList.add(FILES_COUNT_PER_SET+10);
                break;
            default:
                throw new IllegalArgumentException("Unsupported content type: " + type);
        }
        return contentList;
    }


    private static void addCommonContent(List<String> contentList) {
        contentList.add(SCHEDULED_BY + SYS_ADMIN);
        contentList.add(SCHEDULED_TIME + new Date());
        contentList.add(START_TIME + new Date());
        contentList.add(END_TIME + new Date());
        contentList.add(TOTAL_TIME + new Date());
        contentList.add(APPLICATION_NAME + "App name");
        contentList.add(SCHEMA_NAME + "Schema Name");
    }


    public static List<String> buildContentForAdditionalInputColumn() {
        List<String> contentList = new ArrayList<>();
        contentList.add(RECORDS_COUNT_BEFORE_REFRESH + 0);
        contentList.add(RECORDS_COUNT_AFTER_REFRESH + 720);
        return contentList;
    }

    public static List<String> buildHeaderValues(){
        List<String> headerValues = new ArrayList<>();
        headerValues.add(DESCRIPTION);
        headerValues.add(COUNT_OF_PRE_DATA_OPTIMIZATION);
        headerValues.add(COUNT_OF_POST_DATA_OPTIMIZATION);
        headerValues.add(MESSAGE);
        return headerValues;
    }

}
