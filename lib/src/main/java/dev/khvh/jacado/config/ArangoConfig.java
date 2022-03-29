package dev.khvh.jacado.config;

import java.util.List;

public interface ArangoConfig {

  String db();

  String username();

  String password();

  List<ArangoHostConfig> hosts();

}
