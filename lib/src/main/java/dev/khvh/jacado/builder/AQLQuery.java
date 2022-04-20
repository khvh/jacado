package dev.khvh.jacado.builder;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class AQLQuery {

  private String aql;
  private Map<String, Object> props;

}
