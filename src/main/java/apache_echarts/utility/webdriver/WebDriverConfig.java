package apache_echarts.utility.webdriver;

import apache_echarts.utility.browser.ChromeConfig;
import apache_echarts.utility.browser.EdgeConfig;
import apache_echarts.utility.browser.FirefoxConfig;
import java.util.Locale;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;

@UtilityClass
public class WebDriverConfig {

    public static WebDriver getInstance(String browser) {
        browser = browser.toLowerCase(Locale.ROOT);

        return switch (browser) {
            case "chrome" -> ChromeConfig.createDriver();
            case "edge" -> EdgeConfig.createDriver();
            case "firefox" -> FirefoxConfig.createFirefoxDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };

    }

}
