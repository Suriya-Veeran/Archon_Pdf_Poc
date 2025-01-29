package apache_echarts.beans.chart;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextStyleBean {

    @Builder.Default
    private int fontSize = 12;      // Font size (e.g., 12, 14, 16, etc.)

    @Builder.Default
    private String fontFamily = "Arial"; // Font family (e.g., "Arial", "Verdana", "Helvetica", etc.)

    @Builder.Default
    private String fontWeight = "normal"; // Font weight (e.g., "normal", "bold", "lighter")

    @Builder.Default
    private String color = "#000000";      // Text color (e.g., "#000000" for black, "red", etc.)
}
