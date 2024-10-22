package com.p3solutions.archon_report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FinalResultBean {

    private String generatedBy;
    private String viewActivitySessionId;
    private String reportGeneratedTime;


    private String jobType;
    private String jobStatus;
    private String scheduledBy;
    private String scheduledTime;
    private String startTime;
    private String endTime;
    private String totalTime;

    private String applicationName;
    private String schemaName;
    private String jobName;


}
