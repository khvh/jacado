package dev.khvh.jacado.setup;

import dev.khvh.jacado.config.ArangoConfigurationInitializer;

public class Conf extends ArangoConfigurationInitializer {

  public Conf() {
    super(new TestConfig());
  }

}
