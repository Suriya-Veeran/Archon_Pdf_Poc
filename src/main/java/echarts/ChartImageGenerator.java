package echarts;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChartImageGenerator {
  public static void generateChartImage(File htmlFile, String outputPath) throws IOException {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless"); 
    options.addArguments("--disable-gpu");
    
    WebDriver driver = new ChromeDriver(options);
    
    driver.get(htmlFile.toURI().toString());

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chart")));

    File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(file, new File(outputPath));

    driver.quit();
  }
}
