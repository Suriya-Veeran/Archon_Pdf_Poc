package apache_echarts.beans.chart;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChartBasicInfo {

    @Builder.Default
    private String language = "en";  // language of the html page
    @Builder.Default
    private String charSet = "UTF-8";   // charset like UTF
    @Builder.Default
    private String title = "Default Chart Title";     // title of the page
    @Builder.Default
    private String chartWidth = "800px"; // chart width
    @Builder.Default
    private String chartHeight = "600px"; // chart height
    @Builder.Default
    private String chartType = "pie";  // Default chart type (e.g., pie , bar , doughnut)


}
