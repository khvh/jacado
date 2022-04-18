package dev.khvh.jacado.setup;

import java.util.List;

import com.arangodb.*;
import com.arangodb.mapping.ArangoJack;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.khvh.jacado.data.Database;

public class AppDatabase implements Database {

  public static final String DATABASE = "sample";
  public static final List<String> COLLECTIONS = List.of(
    "samples",
    "ones",
    "twos"
  );

  @Override
  public ArangoCollection getCollection(String name) {
    return getDatabase().collection(name);
  }

  @Override
  public ArangoDatabase getDatabase() {
    return arangoDB().db(DbName.of(DATABASE));
  }

  private ArangoDB arangoDB() {
    var jack = new ArangoJack();

    jack.configure(mapper -> {
      mapper.registerModule(new JavaTimeModule());
      mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    });

    var db = new ArangoDB.Builder()
      .serializer(jack)
      .host("localhost", 8529)
      .user("root")
      .password("")
      .build();

    if (!db.db(DbName.of(DATABASE)).exists()) {
      db.createDatabase(DbName.of(DATABASE));
    }

    COLLECTIONS.forEach(COLLECTION -> {
      try {
        db.db(DbName.of(DATABASE)).createCollection(COLLECTION);
      } catch (ArangoDBException e) {
        System.out.println(e.getMessage());
      }
    });

    return db;
  }

}
