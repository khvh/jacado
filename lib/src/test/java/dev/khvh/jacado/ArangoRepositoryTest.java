package dev.khvh.jacado;

import java.util.List;
import java.util.Map;

import dev.khvh.jacado.setup.SampleModel;
import dev.khvh.jacado.setup.SampleRepository;
import io.testcontainers.arangodb.containers.ArangoContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class ArangoRepositoryTest {

  @Container
  private static final ArangoContainer container = new ArangoContainer(
    DockerImageName.parse("arangodb/arangodb:3.9.0-noavx").asCompatibleSubstituteFor("arangodb")
  )
    .withFixedPort(8529)
    .withoutAuth();

  private static SampleRepository testRepository;

  @BeforeAll
  public static void setup() {
    testRepository = new SampleRepository();
  }

  @Test
  public void checkContainerIsRunning() {
    Assertions.assertTrue(container.isRunning());
  }

  @Test
  void query() {
  }

  @Test
  void persist() {
    var m = testRepository.persist(
      new SampleModel()
    );

    Assertions.assertNotNull(m.getId());
  }

  @Test
  void update() {
    var m = testRepository.persist(
      new SampleModel()
    );

    m.name = "TEST";

    m = testRepository.update(m.getKey(), m);

    Assertions.assertEquals(m.getName(), "TEST");
  }

  @Test
  void updateByKeyValue() {
    var m = testRepository.persist(
      new SampleModel()
    );

    m.name = "TEST";

    m = testRepository.update("_key", m.getKey(), m);

    Assertions.assertEquals(m.getName(), "TEST");
  }

  @Test
  void delete() {
    var m = testRepository.persist(
      new SampleModel()
    );

    testRepository.delete(m);

    Assertions.assertTrue(testRepository.findOne(m.getKey()).isEmpty());
  }

  @Test
  void deleteByKey() {
    var m = testRepository.persist(
      new SampleModel()
    );

    testRepository.delete(m.getKey());

    Assertions.assertTrue(testRepository.findOne(m.getKey()).isEmpty());
  }

  @Test
  void deleteByKeyValue() {
    var m = testRepository.persist(
      new SampleModel()
    );

    testRepository.delete("_key", m.getKey());

    Assertions.assertTrue(testRepository.findOne(m.getKey()).isEmpty());
  }

  @Test
  void list() {
    testRepository.persist(new SampleModel());
    testRepository.persist(new SampleModel());
    testRepository.persist(new SampleModel());

    Assertions.assertTrue((long) testRepository.list().size() >= 3);
  }

  @Test
  void testListByKeyValue() {
    testRepository.persist(new SampleModel().setName("first"));

    Assertions.assertTrue((long) testRepository.list("name", "first").size() >= 1);
  }

  @Test
  void testListByIds() {
    var m = testRepository.persist(new SampleModel().setName("first"));

    Assertions
      .assertTrue(
        (long) testRepository
          .list(List.of(m.getKey()))
          .size() >= 1
      );
  }

  @Test
  void testListByQuery() {
    testRepository.persist(new SampleModel().setName("first"));

    Assertions
      .assertTrue(
        (long) testRepository
          .list("FOR x IN samples FILTER x.name == @f RETURN x", Map.of("f", "first"))
          .size() >= 1
      );
  }


  @Test
  void findOneById() {
    var m = testRepository.persist(new SampleModel());

    Assertions
      .assertTrue(testRepository.findOne(m.getKey()).isPresent());
  }

  @Test
  void testFindOneByKeyValue() {
    var m = testRepository.persist(new SampleModel());

    Assertions
      .assertTrue(testRepository.findOne("_key", m.getKey()).isPresent());
  }

  @Test
  void testFindOneByQuery() {
    var m = testRepository.persist(new SampleModel());

    Assertions
      .assertTrue(
        testRepository
          .findOne("FOR x IN samples FILTER x._key == @f LIMIT 1 RETURN x", Map.of("f", m.getKey()))
          .isPresent()
      );
  }

}