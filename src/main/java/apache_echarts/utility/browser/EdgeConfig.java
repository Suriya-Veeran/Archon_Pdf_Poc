package apache_echarts.utility.browser;

import static apache_echarts.constants.WebDriverConfigConstants.*;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

@UtilityClass
public class EdgeConfig {
  public EdgeDriver createDriver() {
    EdgeOptions options = new EdgeOptions();
    options.addArguments(HEADLESS_MODE);
    options.addArguments(DISABLE_GPU);
    options.addArguments(WINDOW_SIZE);
    return new EdgeDriver(options);
  }
}
