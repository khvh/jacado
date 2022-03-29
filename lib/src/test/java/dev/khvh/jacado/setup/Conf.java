package dev.khvh.jacado.setup;

import dev.khvh.jacado.config.ArangoConfigurationInitializer;
public class Conf extends ArangoConfigurationInitializer {

  TestConfig config;

  public Conf() {
    config = new TestConfig();

    super.setConfig(config);
  }

}
