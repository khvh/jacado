package dev.khvh.jacado.builder;

import java.util.Map;
import java.util.UUID;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.DbName;
import com.fasterxml.jackson.core.type.TypeReference;
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

    var aql = new AQL();

    aql
      .select("coll1")
      .filter()
      .by("x.col1 == @b", Map.of("b", "c"))
      .and()
      .by("x.col2 == 'asd'")
      .and()
      .by("x.col3 == @c", "c", 1)
      .sort("x.col1", AQLSortDirection.ASC)
      .limit(0, 100);
    
    Assertions.assertEquals(Q1, aql.toQuery().getAql());
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