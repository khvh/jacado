package dev.khvh.jacado.model;

import java.util.List;

import io.testcontainers.arangodb.containers.ArangoContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class ArangoModelTest {


  @Container
  private static final ArangoContainer container = new ArangoContainer(
    DockerImageName.parse("arangodb/arangodb:3.9.0-noavx").asCompatibleSubstituteFor("arangodb")
  )
    .withFixedPort(8529)
    .withoutAuth();

  @Test
  public void checkContainerIsRunning() {
    Assertions.assertTrue(container.isRunning());
  }

  @Test
  void isWorking() {
    var os = new OneService();
    var ts = new TwoService();

    ts.init();

    var two = ts.persist(new Two());
    var one = os.persist(
      new One().setTwos(List.of(
        two
      ))
    );

    Assertions.assertNotNull(two.getId());
  }

}
