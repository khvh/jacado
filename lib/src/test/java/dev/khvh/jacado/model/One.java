package dev.khvh.jacado.model;

import java.util.List;

import dev.khvh.jacado.Model;
import dev.khvh.jacado.data.Document;
import dev.khvh.jacado.data.Ref;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Document(name = "ones")
public class One extends Model {

  @Ref(lazy = true)
  List<Two> twos;

}
