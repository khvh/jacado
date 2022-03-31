package dev.khvh.jacado.config;

import java.util.List;

public interface ArangoConfig {

  String db();

  String username();

  String password();

  Boolean drop();

  List<ArangoHostConfig> hosts();

}
