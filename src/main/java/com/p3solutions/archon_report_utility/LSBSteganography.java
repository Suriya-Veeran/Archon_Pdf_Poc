package com.p3solutions.archon_report_utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LSBSteganography {

  public static void encodeMessage(BufferedImage image,
                                   String message) {
    String binaryMessage = toBinary(message);


    binaryMessage += "1111111111111110";

    int messageIndex = 0;

    outerLoop:
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        if (messageIndex < binaryMessage.length()) {
          int color = image.getRGB(x, y);
          int blue = color & 0xFF;


          blue = (blue & 0xFE) | (binaryMessage.charAt(messageIndex) - '0');
          messageIndex++;


          color = (color & 0xFFFF00) | blue;
          image.setRGB(x, y, color);
        } else {
          break outerLoop;
        }
      }
    }
  }

  public static void encodeImage(BufferedImage coverImage,
                                 BufferedImage watermarkImage) {
    int width = watermarkImage.getWidth();
    int height = watermarkImage.getHeight();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (x < coverImage.getWidth() && y < coverImage.getHeight()) {
          int watermarkPixel = watermarkImage.getRGB(x, y);
          int coverPixel = coverImage.getRGB(x, y);
          
          int watermarkRed = (watermarkPixel >> 16) & 0xFF;
          int watermarkGreen = (watermarkPixel >> 8) & 0xFF;
          int watermarkBlue = watermarkPixel & 0xFF;
          
          int newRed = (coverPixel & 0xFFFFFFFE) | (watermarkRed & 0x1);
          int newGreen = (coverPixel & 0xFFFFFFFE) | (watermarkGreen & 0x1);
          int newBlue = (coverPixel & 0xFFFFFFFE) | (watermarkBlue & 0x1);
          
          int newPixel = (coverPixel & 0xFF000000) | (newRed << 16) | (newGreen << 8) | newBlue;
          coverImage.setRGB(x, y, newPixel);
        }
      }
    }
  }

  private static String toBinary(String message) {
    StringBuilder binary = new StringBuilder();
    for (char c : message.toCharArray()) {
      binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(" ", "0"));
    }
    return binary.toString();
  }

}
