package report.table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RowData {
    String colorHex;
    String label;
    String value;
}
