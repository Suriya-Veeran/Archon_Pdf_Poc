package apache_echarts.beans.chart;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesInfoBean {
    @Builder.Default
    private String name = "defaultName"; // Name of the series (e.g., 'pie', 'bar', etc.)

    @Builder.Default
    private String type = "pie"; // Type of the chart (e.g., 'pie', 'bar', 'gauge')

    @Builder.Default
    private List<DataInfoBean> data = new ArrayList<>(); // Data points in the series

    @Builder.Default
    private LabelInfoBean label = new LabelInfoBean(); // Label configuration

    @Builder.Default
    private List<String> radius = List.of("50%", "70%"); // Radius for pie or doughnut chart (Optional)
}

