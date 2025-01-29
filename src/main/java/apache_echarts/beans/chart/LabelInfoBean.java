package apache_echarts.beans.chart;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabelInfoBean {

    @Builder.Default
    private boolean show = true; // Whether the label is displayed
    @Builder.Default
    private String position = "inside"; // Position of the label (e.g., inside, outside)
    @Builder.Default
    private int fontSize = 12; // Font size of the label
    @Builder.Default
    private String formatter = "{b}: {c} ({d}%)"; // Text format for the label (e.g., "{b}: {c} ({d}%)")

    // New Properties
    @Builder.Default
    private String color = "#000000"; // Color of the label text
    @Builder.Default
    private String fontWeight = "normal"; // Font weight (e.g., normal, bold)
    @Builder.Default
    private String fontFamily = "Helvetica"; // Font family (e.g., Arial, Verdana)
    @Builder.Default
    private String align = "center"; // Horizontal text alignment (e.g., left, center, right)
    @Builder.Default
    private String verticalAlign = "middle"; // Vertical text alignment (e.g., top, middle, bottom)
    @Builder.Default
    private String backgroundColor = "transparent"; // Background color of the label
    @Builder.Default
    private List<Integer> padding = List.of(5, 5, 5, 5); // Padding around the label text
    @Builder.Default
    private String shadowColor = "transparent"; // Shadow color for the label
    @Builder.Default
    private int shadowBlur = 0; // Shadow blur for the label
    @Builder.Default
    private int shadowOffsetX = 0; // Horizontal shadow offset
    @Builder.Default
    private int shadowOffsetY = 0; // Vertical shadow offset
}
