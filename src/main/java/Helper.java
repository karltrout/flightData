import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * Various Helper Methods.
 *
 */
public class Helper {

  /**
   * PLease use this to load Properties from a config file.
   *
   * @param pathToPropertiesFile as it says.
   * @throws IOException - probably the file path is incorrect.
   */
  public static Properties loadProperties(String pathToPropertiesFile) throws IOException {

    Properties properties = new Properties();

    try (FileInputStream parametersFile = new FileInputStream(pathToPropertiesFile)) {
      properties.load(parametersFile);
    }

    return properties;
  }

}
