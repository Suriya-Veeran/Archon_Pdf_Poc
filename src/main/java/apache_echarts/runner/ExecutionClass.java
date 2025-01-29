package apache_echarts.runner;

import static apache_echarts.constants.PathConstants.*;

import apache_echarts.beans.html_beans.HtmlCreationInfoBean;
import apache_echarts.enums.BrowserTypes;
import apache_echarts.utility.html.HtmlFileGenerator;
import apache_echarts.utility.screenshot_utils.HeadlessScreenshot;
import apache_echarts.utility.yaml.YamlMapper;
import java.io.File;
import java.io.IOException;

public class ExecutionClass {
  public static void main(String[] args) throws IOException {
    HtmlCreationInfoBean htmlCreationInfoBean =
        YamlMapper.parseYaml(PIE_YAML);

    File htmlFile = HtmlFileGenerator.generateHtml(htmlCreationInfoBean);

    HeadlessScreenshot.takeScreenshot(
        htmlFile.toURI().toString(),
        BrowserTypes.CHROME.getValue(),
        htmlCreationInfoBean.getChartBasicInfo().getChartType());
  }
}
