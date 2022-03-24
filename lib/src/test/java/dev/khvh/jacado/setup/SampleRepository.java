package dev.khvh.jacado.setup;

import dev.khvh.jacado.data.Collection;

@Collection(name = AppDatabase.COLLECTION)
public class SampleRepository extends BaseRepository<SampleModel> {

  public SampleRepository() {
    super.init();
  }

}
