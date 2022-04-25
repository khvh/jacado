package dev.khvh.jacado.builder;

import java.util.Map;
import java.util.UUID;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.DbName;
import com.fasterxml.jackson.core.type.TypeReference;
import dev.khvh.jacado.setup.AppDatabase;
import dev.khvh.jacado.setup.SampleModel;
import io.testcontainers.arangodb.containers.ArangoContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class AQLBuilderTest {

  private static final String Q1 = "FOR x IN coll1 FILTER x.col1 == @b AND q AND x.col3 == @c SORT x.col1 ASC LIMIT 0, 100 return x";

  ArangoDB arangoDB = new ArangoDB.Builder()
    .host("localhost", 8529)
    .user("root")
    .build();

  @Container
  private static final ArangoContainer container = new ArangoContainer(
    DockerImageName.parse("arangodb/arangodb").asCompatibleSubstituteFor("arangodb")
  )
    .withFixedPort(8529)
    .withoutAuth();

  @Test
  void test1() {
    initDatabase();

    var builder = new AQLBuilder<SampleModel>(new AppDatabase());

    var q = builder
      .use("samples")
      .build();

    System.out.println("==============");
    System.out.println(builder);
    System.out.println(q.getQuery());
    System.out.println(q.findOne());
    System.out.println("==============");
  }

  private void initDatabase() {
    try {
      arangoDB.createDatabase(DbName.of("testdb"));
    } catch (ArangoDBException e) {
      System.out.println(e.getMessage());
    }

    try {
      arangoDB.db(DbName.of("testdb")).createCollection("test");
    } catch (ArangoDBException e) {
      System.out.println(e.getMessage());
    }
  }

}