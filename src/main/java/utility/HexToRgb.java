package utility;

import com.itextpdf.kernel.colors.DeviceRgb;

public class HexToRgb {
    public static DeviceRgb hexToRgb(String hex) {
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new DeviceRgb(r, g, b);
    }
}
