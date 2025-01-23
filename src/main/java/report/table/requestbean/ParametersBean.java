package report.table.requestbean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ParametersBean {

    private String chartTitle;
    private String chartWidth;
    private String chartAxis;
    private String chartBarSettings;

    private String chartBorderRadius;
    private String chartLegendPosition;

}
