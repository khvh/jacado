package dev.khvh.jacado;

import java.time.ZonedDateTime;

import com.arangodb.entity.DocumentField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Model {

  @DocumentField(DocumentField.Type.KEY)
  private String key;

  @DocumentField(DocumentField.Type.ID)
  private String id;

  @DocumentField(DocumentField.Type.REV)
  private String rev;

  public ZonedDateTime createdAt;
  public ZonedDateTime updatedAt;

  @Override
  public String toString() {
    return id;
  }

}
