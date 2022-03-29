package dev.khvh.jacado;

import java.util.List;

import dev.khvh.jacado.setup.Conf;
import dev.khvh.jacado.setup.SampleModel;
import io.testcontainers.arangodb.containers.ArangoContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class ArangoConfigurationInitializerTest {

  private static Conf conf;

  @Container
  private static final ArangoContainer container = new ArangoContainer(
    DockerImageName.parse("arangodb/arangodb:3.9.0-noavx").asCompatibleSubstituteFor("arangodb")
  )
    .withFixedPort(8529)
    .withoutAuth();

  @BeforeAll
  public static void setup() {
    conf = new Conf();
  }

  @Test
  public void testIndexCreation() {
    var adb = conf.arangoDB(List.of(SampleModel.class));

    Assertions.assertNotNull(adb);
  }

}
