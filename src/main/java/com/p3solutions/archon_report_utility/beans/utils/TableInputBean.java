package com.p3solutions.archon_report_utility.beans.utils;

import com.itextpdf.layout.borders.Border;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TableInputBean {

    private String content;
    private float width;
    private boolean keepTogether;
    private Border border;
    private int numberOfColumns;





}
