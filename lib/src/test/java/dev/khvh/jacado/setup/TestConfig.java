package dev.khvh.jacado.setup;

import java.util.List;

import dev.khvh.jacado.config.ArangoConfig;
import dev.khvh.jacado.config.ArangoHostConfig;

public class TestConfig implements ArangoConfig {

  public static final String DATABASE = "sample";
  public static final String COLLECTION = "samples";
  public static final String USER = "root";
  public static final String PASSWORD = "";

  @Override
  public String db() {
    return DATABASE;
  }

  @Override
  public String username() {
    return USER;
  }

  @Override
  public String password() {
    return PASSWORD;
  }

  @Override
  public List<ArangoHostConfig> hosts() {
    return List.of(
      new TestArangoHostConfig()
    );
  }
}
