package dev.khvh.jacado.setup;

import dev.khvh.jacado.ArangoRepository;
import dev.khvh.jacado.Model;

public abstract class BaseRepository <T extends Model> extends ArangoRepository<T> {

  public void init() {
    super.setDatabase(new AppDatabase());

    super.init();
  }

}
