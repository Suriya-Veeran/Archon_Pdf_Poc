package apache_echarts.enums;

import lombok.Getter;

@Getter
public enum ChartTypes {
  BAR_CHART("bar"),
  PIE_CHART("pie"),
  DOUGHNUT_CHART("doughnut"),
  GAUGE_CHART("gauge");

  private final String value;

  ChartTypes(String value) {
    this.value = value;
  }
}
