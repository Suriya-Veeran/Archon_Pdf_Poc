package apache_echarts.beans.chart;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemStyle {
    @Builder.Default
    private String color = "black";
}
