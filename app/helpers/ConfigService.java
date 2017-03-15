package helpers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigService {
  Config conf = ConfigFactory.load();
  Config keyConfig = conf.getConfig("key");

  String secretKey = keyConfig.getString("secret");
}
