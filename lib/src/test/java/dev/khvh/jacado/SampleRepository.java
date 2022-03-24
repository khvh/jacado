package dev.khvh.jacado;

import dev.khvh.jacado.data.Collection;

@Collection(name = "sample")
public class SampleRepository extends BaseRepository<SampleModel> {

  public SampleRepository() {
    super.init();
  }

}
