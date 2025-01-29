package apache_echarts.utility.yaml;

import apache_echarts.beans.html_beans.HtmlCreationInfoBean;
import java.io.FileReader;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YamlMapper {
    public static HtmlCreationInfoBean parseYaml(String yamlFilePath) throws IOException {
        Yaml yaml = new Yaml();
        FileReader fileReader = new FileReader(yamlFilePath);
        return yaml.loadAs(fileReader, HtmlCreationInfoBean.class);
    }
}
