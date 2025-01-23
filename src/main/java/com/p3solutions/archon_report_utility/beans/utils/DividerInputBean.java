package com.p3solutions.archon_report_utility.beans.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DividerInputBean {

    private String hexDecimal;

    private float lineWidth;

    private int pageNumber;

    private float width;

    private float height;


}
