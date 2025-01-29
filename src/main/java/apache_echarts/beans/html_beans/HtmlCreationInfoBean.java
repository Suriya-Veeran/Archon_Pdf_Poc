package apache_echarts.beans.html_beans;

import apache_echarts.beans.chart.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HtmlCreationInfoBean {

    @Builder.Default
    private ChartBasicInfo chartBasicInfo = new ChartBasicInfo(); // Basic information about the chart (e.g., language, charset)

    @Builder.Default
    private TitleInfoBean titleInfoBean = new TitleInfoBean(); // Information for the chart title

    @Builder.Default
    private ToolTipInfoBean toolTipInfoBean = new ToolTipInfoBean(); // Tooltip configuration

    @Builder.Default
    private LegendInfoBean legendInfoBean = new LegendInfoBean(); // Legend information

    @Builder.Default
    private AxisInfoBean xaxisInfoBean = new AxisInfoBean(); // X-axis configuration

    @Builder.Default
    private AxisInfoBean yaxisInfoBean = new AxisInfoBean(); // Y-axis configuration

    @Builder.Default
    private SeriesInfoBean seriesInfoBean = new SeriesInfoBean(); // Series data for the chart


}
