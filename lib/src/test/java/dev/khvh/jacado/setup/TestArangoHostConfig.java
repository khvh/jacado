package dev.khvh.jacado.setup;

import dev.khvh.jacado.config.ArangoHostConfig;

public class TestArangoHostConfig implements ArangoHostConfig {

  public static final String HOST = "localhost";
  public static final int PORT = 8529;

  @Override
  public String host() {
    return HOST;
  }

  @Override
  public int port() {
    return PORT;
  }

}
