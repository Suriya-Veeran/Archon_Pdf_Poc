package com.p3solutions.archon_report_utility.beans.input_bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OptimizationStatisticsBean {

    private List<String> headers;

    private List<String> values;


}
