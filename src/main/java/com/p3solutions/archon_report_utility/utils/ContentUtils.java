package com.p3solutions.archon_report_utility.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.p3solutions.archon_report_utility.constants.HeaderConstants.*;
import static com.p3solutions.archon_report_utility.constants.JobSummaryConstants.*;
import static com.p3solutions.archon_report_utility.constants.report_constants.MaterializedViewConstants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentUtils {

    public static List<String> buildContentForColumn() {
        List<String> contentList = new ArrayList<>();
        contentList.add(GENERATED_BY + "Sysadmin");
        contentList.add(VIEW_ACTIVITY_SESSION_ID + 12333727);
        contentList.add(REPORT_GENERATED_TIME + new Date());
        return contentList;
    }

    public static List<String> buildContentForJobColumn() {
        List<String> contentList = new ArrayList<>();
        contentList.add(JOB_TYPE + "Materialized View Refresh Report");
        contentList.add(SCHEDULED_BY + "Sysadmin");
        contentList.add(SCHEDULED_TIME + new Date());
        contentList.add(START_TIME + new Date());
        contentList.add(END_TIME + new Date());
        contentList.add(TOTAL_TIME + new Date());
        contentList.add(APPLICATION_NAME + "App name");
        contentList.add(SCHEMA_NAME + "Schema Name");
        contentList.add(JOB_NAME + "Materialized View");
        return contentList;

    }

    public static List<String> buildContentForAdditionalInputColumn() {
        List<String> contentList = new ArrayList<>();
        contentList.add(RECORDS_COUNT_BEFORE_REFRESH + 0);
        contentList.add(RECORDS_COUNT_AFTER_REFRESH + 720);
        return contentList;
    }
}
