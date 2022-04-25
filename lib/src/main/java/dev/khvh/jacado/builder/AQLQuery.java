package dev.khvh.jacado.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

  private List<String> query = new ArrayList<>();
  private Map<String, Object> props = new HashMap<>();

}
