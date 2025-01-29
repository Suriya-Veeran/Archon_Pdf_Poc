package apache_echarts.beans.chart;


import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegendInfoBean {
    @Builder.Default
    private String orient = ""; // 'vertical' or 'horizontal'

    @Builder.Default
    private String left = ""; // Position of the legend (e.g., 'left', 'right', 'center', 'top', 'bottom')

    @Builder.Default
    private String bottom = "auto"; // Position of the legend (optional if using 'top', 'bottom', 'center', etc.)

    @Builder.Default
    private TextStyleBean textStyle = new TextStyleBean(); // Text style for the legend

    @Builder.Default
    private List<String> data = List.of(""); // Legend items, which are the names for each data series

}
