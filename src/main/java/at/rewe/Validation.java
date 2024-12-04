package at.rewe;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Validation {

  private String error;
  private TreeMap<String, String> map;

  public Validation(TreeMap<String, String> map) {
    this.map = map;
  }

  public enum Regex {
    NUMWERTE4("^\\d+(\\.\\d{1,4})?$"),
    NUMWERTE("^\\d+$"),
    CHECKBOX("^[xXjJyY]$"),
    DATE("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$"),
    GTIN("^\\d{1,14}$"),
    GLN("^\\d{13}$"), // Correct regex for 13-digit GLN
    KEINE("^.*$"),
    LENGTH70(),
    LENGTH40();

    private String regex;

    Regex() {
      this.regex = "length";
    }

    Regex(String regex) {
      this.regex = regex;
    }

    public String getRegex() {
      return regex;
    }
  }

  public String validate() {
    // Correct regex alignment to map keys
    List<Regex> regexList = Arrays.asList(
        Regex.LENGTH70, Regex.GLN, Regex.DATE, Regex.NUMWERTE, Regex.KEINE, Regex.LENGTH40, Regex.KEINE,
        Regex.KEINE, Regex.NUMWERTE, Regex.KEINE, Regex.KEINE, Regex.NUMWERTE4, Regex.KEINE,
        Regex.NUMWERTE, Regex.NUMWERTE, Regex.KEINE, Regex.KEINE, Regex.NUMWERTE, Regex.GTIN,
        Regex.GTIN, Regex.GTIN, Regex.GTIN, Regex.NUMWERTE, Regex.NUMWERTE, Regex.NUMWERTE,
        Regex.NUMWERTE4, Regex.NUMWERTE, Regex.CHECKBOX, Regex.CHECKBOX, Regex.KEINE,
        Regex.CHECKBOX, Regex.CHECKBOX, Regex.CHECKBOX, Regex.CHECKBOX, Regex.KEINE,
        Regex.NUMWERTE4, Regex.NUMWERTE4, Regex.NUMWERTE4, Regex.CHECKBOX
    );

    int regexIndex = 0; // To track the current regex
    for (Map.Entry<String, String> entry : map.entrySet()) {
      if (regexIndex >= regexList.size()) {
        break; // No more regex patterns
      }

      String key = entry.getKey();
      String value = entry.getValue();
      Regex currentRegex = regexList.get(regexIndex);

      // Special handling for LENGTH validations
      if (currentRegex == Regex.LENGTH70) {
        if (value.length() > 70) {
          error = "Error: Value for key " + key + " is longer than 70 characters";
          System.out.println(error);
          regexIndex++;
          continue;
        }
      } else if (currentRegex == Regex.LENGTH40) {
        if (value.length() > 40) {
          error = "Error: Value for key " + key + " is longer than 40 characters";
          System.out.println(error);
          regexIndex++;
          continue;
        }
      }

      // Handle GLN specifically
      if (currentRegex == Regex.GLN) {
        if (value.matches("^\\d\\.\\d+E\\d+$")) {
          value = new BigDecimal(value).toPlainString(); // Convert scientific notation
        }
      }

      // General regex validation
      String regexPattern = currentRegex.getRegex();
      if (regexPattern != null && !Pattern.matches(regexPattern, value)) {
        error = "Error: Validation failed for key: " + key + ", Value: " + value;
        System.out.println(error);
      } else {
        System.out.println("Validation passed for key: " + key + ", Value: " + value);
      }

      regexIndex++; // Move to the next regex
    }

    return error;
  }
}
