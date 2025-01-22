package test;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChartData {
    private int value;
    private String label;
    private String color;
}
