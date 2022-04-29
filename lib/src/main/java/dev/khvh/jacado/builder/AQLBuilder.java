package dev.khvh.jacado.builder;

import java.util.List;
import java.util.UUID;

import dev.khvh.jacado.Model;

public class AQLBuilder<T extends Model> {

  private final UUID id;
  private AQLQuery query;

  public AQLBuilder() {
    id = UUID.randomUUID();
  }

  public AQL<T> use(String collection) {
    return new AQL<T>(collection, new AQLBuilder<T>());
  }

  public List<Model> exec() {
    return null;
  }

  public AQLBuilder<T> setQuery(AQLQuery query) {
    this.query = query;

    return this;
  }

  public AQLQuery getQuery() {
    query.getQuery().add(" RETURN x");

    return query;
  }

  public String toString() {
    return "[" + id + "]";
  }

}
