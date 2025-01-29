package utility.unit_conversion;

import apache_echarts.enums.FormatTypes;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UnitConversion {

  public static int convertToKb(FormatTypes formatTypes, int value) {

    return switch (formatTypes) {
      case GB -> value * 1024 * 1024; // GB to KB
      case MB -> value * 1024; // MB to KB
      case KB -> value; // Already in KB
      default ->
          throw new IllegalArgumentException("Unknown file size unit: " + formatTypes.name());
    };
  }
}
