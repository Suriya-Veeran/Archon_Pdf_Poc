package com.p3solutions.archon_report_utility;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class LSBSteganographyDecoder {

  public static String decodeMessage(BufferedImage image) {
    StringBuilder binaryMessage = new StringBuilder();

    outerLoop:
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int color = image.getRGB(x, y);
        int blue = color & 0xFF;

        binaryMessage.append(blue & 1);


        if (binaryMessage.length() >= 16
            && binaryMessage.substring(binaryMessage.length() - 16).equals("1111111111111110")) {
          break outerLoop;
        }
      }
    }

    // Convert binary message to text
    return toText(
        binaryMessage.toString().substring(0, binaryMessage.length() - 16)); // Remove delimiter
  }

  private static String toText(String binaryMessage) {
    StringBuilder message = new StringBuilder();
    for (int i = 0; i < binaryMessage.length(); i += 8) {
      String byteStr = binaryMessage.substring(i, i + 8);
      message.append((char) Integer.parseInt(byteStr, 2));
    }
    return message.toString();
  }

  public static void main(String[] args) {
    try {
      BufferedImage image = ImageIO.read(new File("/home/p3/Documents/steg/output.png"));
      String message = decodeMessage(image);
      System.out.println("Decoded Message: " + message);
    } catch (IOException e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}
