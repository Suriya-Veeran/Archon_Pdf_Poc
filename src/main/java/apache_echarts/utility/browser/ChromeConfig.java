package apache_echarts.utility.browser;

import static apache_echarts.constants.WebDriverConfigConstants.*;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@UtilityClass
public class ChromeConfig{

  public ChromeDriver createDriver() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments(HEADLESS_MODE);
    options.addArguments(DISABLE_GPU);
    options.addArguments(WINDOW_SIZE);
    return new ChromeDriver(options);
  }
}
