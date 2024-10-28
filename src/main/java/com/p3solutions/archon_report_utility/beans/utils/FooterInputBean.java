package com.p3solutions.archon_report_utility.beans.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class FooterInputBean {

    private int rectangleWidth;
    private int rectangleHeight;

    private String url;

    private String linkText;


    private int width;
    private TextAlignment textAlignment;
    private VerticalAlignment verticalAlignment;
    private int fontSize;
    private String font;
    private Color fontColor;
    private Border border;

    private String copyRightText;
    private String allRightsReservedText;

    private float textAlignmentWidth;
    private float textAlignmentHeight;

    private float pageAlignmentWidth;
    private float pageAlignmentHeight;


}
