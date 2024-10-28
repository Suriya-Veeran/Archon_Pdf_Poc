package com.p3solutions.archon_report_utility.beans.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ParagraphInputBean {

    private float width;
    private TextAlignment textAlignment;
    private VerticalAlignment verticalAlignment;
    private float fontSize;
    private Border border;
    private Color fontColor;
    private String firstText;
    private Link link;
    private String endText;

}
