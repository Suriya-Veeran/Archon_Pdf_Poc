package apache_echarts.beans.chart;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AxisPointerBean {
    @Builder.Default
    private String type = "line"; // 'line', 'shadow', 'cross'
}
