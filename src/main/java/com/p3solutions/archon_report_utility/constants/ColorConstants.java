package com.p3solutions.archon_report_utility.constants;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColorConstants {

    public static final Color BLACK = new DeviceRgb(0, 0, 0);
    public static final Color BLUE = new DeviceRgb(0, 0, 255);
    public static final Color CYAN = new DeviceRgb(0, 255, 255);
    public static final Color DARK_GRAY = new DeviceRgb(64, 64, 64);
    public static final Color GRAY = new DeviceRgb(128, 128, 128);
    public static final Color GREEN = new DeviceRgb(0, 255, 0);
    public static final Color DARK_GREEN = new DeviceRgb(0, 100, 0);
    public static final Color LIGHT_GRAY = new DeviceRgb(192, 192, 192);
    public static final Color MAGENTA = new DeviceRgb(255, 0, 255);
    public static final Color ORANGE = new DeviceRgb(255, 200, 0);
    public static final Color PINK = new DeviceRgb(255, 175, 175);
    public static final Color RED = new DeviceRgb(255, 0, 0);
    public static final Color DARK_RED = new DeviceRgb(139, 0, 0);
    public static final Color WHITE = new DeviceRgb(255, 255, 255);
    public static final Color YELLOW = new DeviceRgb(255, 255, 0);
    public static final Color FIELDS_GREY_COLOR = new DeviceRgb(238, 238, 238);



    public static Color hexaDecimalToRGB(String hexaDecimal) {
        int r = Integer.valueOf(hexaDecimal.substring(0, 2), 16);
        int g = Integer.valueOf(hexaDecimal.substring(2, 4), 16);
        int b = Integer.valueOf(hexaDecimal.substring(4, 6), 16);
        return new DeviceRgb(r, g, b);
    }

    public static final String LIGHT_GREEN_HEXA_DECIMAL = "007D2B";

    public static final String RED_HEXA_DECIMAL = "D60000";

    public static final String DIVIDER_LINE_HEXA_DECIMAL = "E9E9E9";

    public static final String ASH_HEXA_DECIMAL = "BCBCBC";

    public static final String BLACK_HEXA_DECIMAL = "030303";

    public static final String SMOKY_BLACK_HEXA_DECIMAL = "0D0D0D";

    public static final String PURE_BLACK_HEXA_DECIMAL = "000000";

    public static final String DARK_GREY_HEXA_DECIMAL = "2C2C2C";

    public static final String GREY_SILVER_HEXA_DECIMAL = "B8B8B8";

    public static final String WHITE_SMOKE_HEXA_DECIMAL = "3F3F3F";

    public static final String FOOTER_HEXA_DECIMAL = "E2E2E2";


}
