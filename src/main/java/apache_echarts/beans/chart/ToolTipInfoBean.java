package apache_echarts.beans.chart;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolTipInfoBean {

    @Builder.Default
    private String trigger = "item"; // 'item' or 'axis'

    @Builder.Default
    private AxisPointerBean axisPointer = new AxisPointerBean(); // Configuration for axis pointer (if trigger is 'axis')

    @Builder.Default
    private String formatter = "{b}: {c} ({d}%)"; // Tooltip format string  Format of the tooltip (e.g., '{b}: {c} ({d}%)')

    @Builder.Default
    private String backgroundColor = "#FFFFFF"; // Background color of the tooltip

    @Builder.Default
    private String borderColor = "#000000"; // Border color of the tooltip

    @Builder.Default
    private int borderWidth = 1; // Border width

    @Builder.Default
    private List<Integer> padding = List.of(); // Padding for the tooltip

    @Builder.Default
    private TextStyleBean textStyle = new TextStyleBean(); // Text style for the tooltip

    @Builder.Default
    private String extraCssText = "font-size: 14px;"; // Extra CSS styling

}
