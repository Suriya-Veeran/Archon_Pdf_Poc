package com.p3solutions.archon_report_utility.beans;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CellValueInputBean {
    private String text;
    private Table table;
    private TextAlignment textAlignment;
    private Color backgroundColor;
    private int cellHeight;
    private boolean isHeader;
    private boolean status;
    private PdfFont font;
}
