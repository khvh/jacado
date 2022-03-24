package dev.khvh.jacado.setup;

import dev.khvh.jacado.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SampleModel extends Model {

  public String name;

}
