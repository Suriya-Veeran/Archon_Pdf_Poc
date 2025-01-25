package echarts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class PuppetGenerator {
  public static void createDynamicScript(String pageURL, String screenshotName) {
    try {
      String puppeteerScript = generatePuppeteerScript(pageURL, screenshotName);
      String scriptPath = "generated_screenshot.js";
      writeScriptToFile(puppeteerScript, scriptPath);
      executePuppeteerScript(scriptPath);
    } catch (IOException | InterruptedException e) {
      log.error("Error during script execution: ", e);
    }
  }

  private static String generatePuppeteerScript(String pageURL, String screenshotName) {
    return String.format(
        """
            const puppeteer = require('puppeteer');
            (async () => {
                const browser = await puppeteer.launch({ headless: true, args: ['--no-sandbox', '--disable-setuid-sandbox', '--disable-gpu'] });
                const page = await browser.newPage();
                await page.goto('%s', { waitUntil: 'networkidle0', timeout: 60000 });
                await page.setViewport({ width: 1280, height: 800 });
                await page.waitForFunction('document.querySelector("#chart").clientHeight > 0');
                await page.waitForFunction('document.querySelector("#chart").clientWidth > 0');
                await page.waitForNetworkIdle({ idleTime: 500, timeout: 60000 });
                await page.screenshot({ path: '%s' });
                await browser.close();
            })();
            """,
        pageURL, screenshotName);
  }

  private static void writeScriptToFile(String script, String filePath) throws IOException {
    Files.write(Paths.get(filePath), script.getBytes());
    log.info("Puppeteer script written to: {}", filePath);
  }

  private static void executePuppeteerScript(String scriptPath)
      throws IOException, InterruptedException {
    String command = "node " + scriptPath;
    Process process = Runtime.getRuntime().exec(command);
    process.waitFor();

    try (BufferedReader reader =
            new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader =
            new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
      reader.lines().forEach(log::info);
      errorReader.lines().forEach(log::error);
    }

    log.info("Screenshot taken and saved.");
  }
}
