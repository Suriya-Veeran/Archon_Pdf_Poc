package apache_echarts.beans.chart;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TitleInfoBean {

    @Builder.Default
    private String text = "Default";  // title

    @Builder.Default
    private String subText = ""; // subTitle

    @Builder.Default
    private String left = "center";   // left values -> center, right, top, ''

    @Builder.Default
    private String bottom = ""; // bottom values -> bottom, center, right, top, ''

    @Builder.Default
    private String top = ""; // adjust the position of the text

    @Builder.Default
    private TextStyleBean textStyle = new TextStyleBean();  // Text style for title

    @Builder.Default
    private TextStyleBean subTextStyle = new TextStyleBean();  // Text style for subtitle


}
