package apache_echarts.utility.screenshot_utils;

import static apache_echarts.constants.FileFormatConstants.PNG_WITH_EXTENSION;
import static apache_echarts.constants.FileNameConstants.SNAP;
import static apache_echarts.constants.PathConstants.*;
import static apache_echarts.constants.SpecialCharacterConstants.HYPHEN;

import apache_echarts.utility.webdriver.WebDriverConfig;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeadlessScreenshot {

  public static Image takeScreenshot(String url, String browserType, String chartType) {

    File screenshotFile;
    String filePath = SNAP_FILES + File.separator + SNAP + HYPHEN + chartType + PNG_WITH_EXTENSION;
    WebDriver driver = WebDriverConfig.getInstance(browserType);
    try {
      driver.get(url);
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

      // Wait for the chart container to be loaded
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("chart")));

      JavascriptExecutor js = (JavascriptExecutor) driver;

      // Adjust page size to fit the chart (removes scroll bar)
      js.executeScript("document.body.style.overflow = 'hidden';");
      js.executeScript("document.body.style.height = '100vh';");

      Thread.sleep(3000);

      wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("canvas")));


      js.executeScript("window.scrollTo(0, 0);");

      // Trigger resize event to refresh chart
      js.executeScript("window.dispatchEvent(new Event('resize'));");

      Thread.sleep(3000);

      // Take the screenshot
      screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      Files.copy(screenshotFile.toPath(), Path.of(filePath));

      ImageData imageData = ImageDataFactory.create(filePath);
      return new Image(imageData);
    } catch (IOException e) {
      throw new IllegalArgumentException("Error while taking screenshot: " + e.getMessage());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Thread was interrupted while taking a screenshot", e);
    } finally {
      driver.quit();
    }
  }
}
