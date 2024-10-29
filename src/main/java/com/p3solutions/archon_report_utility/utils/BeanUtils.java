package com.p3solutions.archon_report_utility.utils;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.p3solutions.archon_report_utility.beans.input_bean.OptimizationStatisticsBean;
import com.p3solutions.archon_report_utility.beans.utils.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.p3solutions.archon_report_utility.constants.ColorConstants.*;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA;
import static com.p3solutions.archon_report_utility.constants.FontConstants.HELVETICA_BOLD;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.*;
import static com.p3solutions.archon_report_utility.constants.FontSizeConstants.FOOTER_FONT_SIZE;
import static com.p3solutions.archon_report_utility.constants.ImageConstants.ARCHON_LOGO;
import static com.p3solutions.archon_report_utility.constants.ServiceConstants.*;
import static com.p3solutions.archon_report_utility.constants.ServiceConstants.ALL_RIGHTS_RESERVED;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanUtils {

    private static final MapperUtils mapperUtils = new MapperUtils();

    public static TableInputBean buildTableInputBean() {
        return TableInputBean
                .builder()
                .width(100)
                .keepTogether(true)
                .border(Border.NO_BORDER)
                .numberOfColumns(3)
                .build();
    }

    public static ColumnInputBean buildColumnInputBean() {
        return ColumnInputBean
                .builder()
                .border(Border.NO_BORDER)
                .backgroundColor(WHITE)
                .fontSize(DESC_FONT_SIZE)
                .textAlignment(TextAlignment.LEFT)
                .fontColor(hexaDecimalToRGB(PURE_BLACK_HEXA_DECIMAL))
                .font(HELVETICA_BOLD)
                .rowSpan(1)
                .columnSpan(1)
                .cellHeight(CELL_HEIGHT_FOR_HEADER_TABLE)
                .isHeader(true)
                .build();
    }

    public static ColumnInputBean buildColumnInputBeanForJobSummary() {
        return ColumnInputBean
                .builder()
                .border(Border.NO_BORDER)
                .backgroundColor(WHITE)
                .fontSize(DESC_FONT_SIZE)
                .textAlignment(TextAlignment.LEFT)
                .fontColor(hexaDecimalToRGB(PURE_BLACK_HEXA_DECIMAL))
                .font(HELVETICA_BOLD)
                .rowSpan(1)
                .columnSpan(1)
                .cellHeight(CELL_HEIGHT_FOR_HEADER_TABLE)
                .isHeader(false)
                .build();
    }

    public static ColumnInputBean buildAdditionalInputBean() {
        return ColumnInputBean
                .builder()
                .border(Border.NO_BORDER)
                .backgroundColor(WHITE)
                .fontSize(DESC_FONT_SIZE)
                .textAlignment(TextAlignment.LEFT)
                .font(HELVETICA_BOLD)
                .rowSpan(1)
                .columnSpan(1)
                .cellHeight(CELL_HEIGHT_FOR_HEADER_TABLE)
                .isHeader(false)
                .build();
    }


    public static DividerInputBean buildDividerInputBean(float height,
                                                         float lineWidth,
                                                         String hexaDecimal) {
        return DividerInputBean
                .builder()
                .pageNumber(1)
                .hexaDecimal(hexaDecimal)
                .height(height)
                .lineWidth(lineWidth)
                .build();
    }

    public static HeaderInputBean buildHeaderInputBean(String name) {
        return HeaderInputBean
                .builder()
                .content(name)
                .fontSize(12)
                .backgroundColor(hexaDecimalToRGB(BLACK_HEXA_DECIMAL))
                .font(HELVETICA_BOLD)
                .textAlignment(TextAlignment.LEFT)
                .verticalAlignment(VerticalAlignment.TOP)
                .leftMargin(20)
                .rightMargin(580)
                .topMargin(30)
                .isLogoNeeded(true)
                .imagePath(ARCHON_LOGO)
                .fitHeight(60)
                .fitWidth(60)
                .logoWidth(90)
                .logoHeight(35)
                .logoTextAlignment(TextAlignment.LEFT)
                .logoVerticalAlignment(VerticalAlignment.TOP)
                .build();
    }

    public static FooterInputBean buildFooterInputBean() {
        return FooterInputBean
                .builder()
                .rectangleHeight(0)
                .rectangleWidth(0)
                .url(PLATFORM_3_SOLUTIONS_URL)
                .linkText(PLATFORM_3_SOLUTIONS)
                .copyRightText(COPYRIGHT_2024)
                .allRightsReservedText(ALL_RIGHTS_RESERVED)
                .width(100)
                .textAlignment(TextAlignment.LEFT)
                .verticalAlignment(VerticalAlignment.BOTTOM)
                .fontSize(FOOTER_FONT_SIZE)
                .font(HELVETICA)
                .fontColor(hexaDecimalToRGB(PURE_BLACK_HEXA_DECIMAL))
                .border(Border.NO_BORDER)
                .textAlignmentHeight(15)
                .textAlignmentWidth(20)
                .pageAlignmentHeight(15)
                .pageAlignmentWidth(525)
                .build();
    }

    public static OptimizationStatisticsBean buildOptimizationStatisticsBean(List<String> headerValues) {
        List<String> valueList = new ArrayList<>();
        return OptimizationStatisticsBean
                .builder()
                .headers(headerValues)
                .values(valueList)
                .build();
    }

}
