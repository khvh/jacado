package dev.khvh.jacado.model;

import dev.khvh.jacado.data.Collection;
import dev.khvh.jacado.setup.BaseRepository;

@Collection(name = "ones")
public class OneService extends BaseRepository<One> {

  public OneService() {
    super.init();
  }

}
