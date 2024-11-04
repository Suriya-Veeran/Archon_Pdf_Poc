package com.p3solutions.archon_report_utility.beans.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ColumnInputBean {

    private String content;
    private int fontSize;
    private Color backgroundColor;
    private Border border;
    private String font;
    private Color fontColor;
    private TextAlignment textAlignment;
    private Table table;
    private boolean isHeader;

    private int rowSpan;
    private int columnSpan;
    private int cellHeight;

    private boolean isJobSummaryHeader;
    private boolean valueHeader;
    private String jobStatus;

}
