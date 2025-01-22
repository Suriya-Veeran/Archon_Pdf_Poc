package test;

import lombok.Getter;

@Getter
public enum ChartType {

    BAR_HORIZONTAL_CHART("Bar Horizontal Chart", "bhs"),
    BAR_VERTICAL_CHART("Bar Vertical Chart", "bvs"),
    PIE_CHART("Pie Chart", "p"),
    DOUGHNUT_CHART("Doughnut Chart", "pd");

    private final String name;
    private final String type;

    ChartType(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
