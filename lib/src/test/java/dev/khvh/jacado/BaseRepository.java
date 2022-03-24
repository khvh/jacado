package dev.khvh.jacado;

public abstract class BaseRepository <T extends Model> extends ArangoRepository<T> {

  public void init() {
    super.setDatabase(new AppDatabase());

    super.init();
  }

}
