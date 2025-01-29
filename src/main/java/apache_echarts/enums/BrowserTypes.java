package apache_echarts.enums;

import lombok.Getter;

@Getter
public enum BrowserTypes {
  CHROME("chrome"),
  EDGE("edge"),
  FIREFOX("firefox");

  private final String value;

  BrowserTypes(String value) {
    this.value = value;
  }
}
