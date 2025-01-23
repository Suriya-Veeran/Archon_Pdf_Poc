package report.table.requestbean;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report.table.RowData;
import report.table.enums.ChartType;

@Getter
@Setter
@Builder
public class ChartRequestBean {
    private String fileName;
    private ChartType chartType;
    private List<RowData> chartDataList;
    private List<String> legends;
    private String margins;
    private int cropX;
    private int cropY;
    private int cropWidth;
    private int cropHeight;
    private ParametersBean parametersBean;
}
