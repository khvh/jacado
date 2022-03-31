package dev.khvh.jacado.setup;

import java.util.List;

import dev.khvh.jacado.config.ArangoConfigurationInitializer;
import dev.khvh.jacado.data.JacadoConfiguration;

@JacadoConfiguration(
  packages = {
    "dev.khvh.jacado.setup"
  }
)
public class TestConfiguration extends ArangoConfigurationInitializer {

  public TestConfiguration() {
    super(new TestConfig());
  }

}