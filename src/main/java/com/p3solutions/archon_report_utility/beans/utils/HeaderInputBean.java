package com.p3solutions.archon_report_utility.beans.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HeaderInputBean {
    private String content;
    private PdfDocument pdfDocument;
    private TextAlignment textAlignment;
    private float rightMargin;
    private float topMargin;
    private Color backgroundColor;
    private int fontSize;
    private Document inputDocument;
    private VerticalAlignment verticalAlignment;
}
