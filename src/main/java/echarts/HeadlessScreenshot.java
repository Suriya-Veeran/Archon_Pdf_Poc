package echarts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeadlessScreenshot {
  public static File takeScreenshot(String url,
                                    String outputPath) {

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    options.addArguments(
        "--window-size=1920x1080"); // Set window size (important for full-page screenshots)

    WebDriver driver = new ChromeDriver(options);
    File screenshotFile = null;

    try {
      driver.get(url);

      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      wait.until(
          ExpectedConditions.visibilityOfElementLocated(
              By.tagName("body")));
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

      Thread.sleep(2000);

      screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

      Path destination = Paths.get(outputPath);
      Files.copy(screenshotFile.toPath(), Path.of("src/main/resources/echartOutputFolder" + File.separator + destination));

    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    } finally {
      driver.quit();
    }

    return screenshotFile;
  }
}
