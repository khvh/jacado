package dev.khvh.jacado.data;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Index {

  public String name;
  public boolean unique;

}
