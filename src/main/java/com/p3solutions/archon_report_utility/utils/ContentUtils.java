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
import static com.p3solutions.archon_report_utility.constants.report_constants.MaterializedViewConstants.*;

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
            case TABLE_OPTIMIZATION_REPORT:
                contentList.add(JOB_NAME+"Test Job Name");
                break;
            default:
                break;
        }

        contentList.add(REPORT_GENERATED_TIME + new Date());
        return contentList;

    }

    public static List<String> buildContentForJobColumn() {
        List<String> contentList = new ArrayList<>();
        contentList.add(JOB_TYPE + "Materialized View Refresh Report");
        contentList.add(SCHEDULED_BY + SYS_ADMIN);
        contentList.add(SCHEDULED_TIME + new Date());
        contentList.add(START_TIME + new Date());
        contentList.add(END_TIME + new Date());
        contentList.add(TOTAL_TIME + new Date());
        contentList.add(APPLICATION_NAME + "App name");
        contentList.add(SCHEMA_NAME + "Schema Name");
        contentList.add(JOB_NAME + "Materialized View");
        return contentList;

    }

    public static List<String> buildContentForJobSummaryForTableData(){
        List<String> contentList = new ArrayList<>();
        contentList.add(JOB_INSTANCE_ID+"0123546474-5252");
        contentList.add(JOB_TYPE+"Table Data Optimization");
        contentList.add(SCHEDULED_BY + SYS_ADMIN);
        contentList.add(SCHEDULED_TIME + new Date());
        contentList.add(START_TIME + new Date());
        contentList.add(END_TIME + new Date());
        contentList.add(TOTAL_TIME + new Date());
        contentList.add(APPLICATION_NAME + "App name");
        contentList.add(SCHEMA_NAME + "Schema Name");
        contentList.add(TABLE_NAME+"Address");
        return contentList;

    }
    public static List<String> buildContentForAdditionalInputColumn() {
        List<String> contentList = new ArrayList<>();
        contentList.add(RECORDS_COUNT_BEFORE_REFRESH + 0);
        contentList.add(RECORDS_COUNT_AFTER_REFRESH + 720);
        return contentList;
    }

    public static List<String> buildHeaderValues(){
        List<String> headerValues = new ArrayList<>();
        headerValues.add("Description");
        headerValues.add("Count of pre-data optimization");
        headerValues.add("Count of post-data optimization");
        headerValues.add("Message");
        return headerValues;
    }

}
