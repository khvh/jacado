package dev.khvh.jacado;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.DbName;
import com.arangodb.mapping.ArangoJack;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.khvh.jacado.data.Database;

public class AppDatabase implements Database {

  @Override
  public ArangoCollection getCollection(String name) {
    return getDatabase().collection(name);
  }

  @Override
  public ArangoDatabase getDatabase() {
    return arangoDB().db(DbName.of("sample"));
  }

  private ArangoDB arangoDB() {
    var jack = new ArangoJack();

    jack.configure(mapper -> {
      mapper.registerModule(new JavaTimeModule());
      mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    });

    var builder = new ArangoDB.Builder()
      .serializer(jack)
      .host("localhost", 8529)
      .user("root")
      .password("");

    return builder.build();
  }

}
