package dev.khvh.jacado.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AQL {

  private final List<String> aql;
  private final Map<String, Object> props;

  public AQL() {
    aql = new ArrayList<>();
    props = new HashMap<>();
  }

  public AQL select(String collection) {
    aql.add("FOR x IN " + collection);

    return this;
  }

  public AQL filter() {
    aql.add("FILTER");

    return this;
  }

  public AQL and() {
    aql.add("AND");

    return this;
  }

  public AQL by(String q) {
    aql.add("q");

    return this;
  }

  public AQL by(String q, Map<String, Object> props) {
    aql.add(q);
    this.props.putAll(props);

    return this;
  }

  public AQL by(String q, String key, Object value) {
    aql.add(q);

    this.props.put(key, value);

    return this;
  }

  public AQL sort(String keys, AQLSortDirection direction) {
    aql.add(String.format("SORT %s %s", keys.trim(), direction.getDirection()));

    return this;
  }

  public AQL limit(int offset, int count) {
    aql.add(String.format("LIMIT %d, %d", offset, count));

    return this;
  }

  public String toAQL() {
    if (!aql.get(aql.size() - 1).equals("return x")) {
      aql.add("return x");
    }

    return String.join(" ", aql);
  }

  public AQLQuery toQuery() {
    return new AQLQuery()
      .setAql(toAQL())
      .setProps(props);
  }

}
