package test;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ImageChartsOld {

  private static final String BASE_URI = "https://image-charts.com/chart";

  private static void barChart(String fileName) throws IOException {

    URL url = new URL(BASE_URI+"");
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

    urlConnection.setRequestMethod("GET");
    urlConnection.setRequestProperty("Accept", "application/json");

    Path filePath = Paths.get("src/main/resources/test" + fileName + ".png");
    int responseCode = urlConnection.getResponseCode();
    log.info("response code {}", responseCode);

    if (responseCode == HttpURLConnection.HTTP_OK) {
      Files.createDirectories(filePath.getParent());

      try (BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
          FileOutputStream fos = new FileOutputStream(filePath.toFile())) {

        byte[] buffer = new byte[1024];
        int count;
        while ((count = bis.read(buffer)) != -1) {
          fos.write(buffer, 0, count);
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    barChart("barchart");
  }
}
