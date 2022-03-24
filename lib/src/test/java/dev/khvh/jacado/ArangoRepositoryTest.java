package dev.khvh.jacado;

import junit.framework.TestCase;
import org.junit.Test;

public class ArangoRepositoryTest extends TestCase {

  @Test
  public void testList() {
    var repo = new SampleRepository();

    repo.persist(new SampleModel());

    var l = repo.list();

    assertTrue(l.size() > 0);
  }

}