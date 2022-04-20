package dev.khvh.jacado.setup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.khvh.jacado.Model;
import dev.khvh.jacado.data.Document;
import dev.khvh.jacado.data.Indexed;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Document(name = "samples")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleModel extends Model {

  public String name;

  @Indexed(unique = true)
  public String uniqueName;

  @Indexed
  public String indexedName;

}
