package dev.khvh.jacado.builder;


import dev.khvh.jacado.Model;

public class AQL<T extends Model> {

  private final AQLBuilder<T> instance;
  private final AQLQuery query;

  public AQL(String collectionName, AQLBuilder<T> instance) {
    this.instance = instance;

    query = new AQLQuery();

    query.getQuery().add("FOR x IN @@collectionName ");
    query.getProps().put("@collectionName", collectionName);
  }

  public AQLBuilder<T> build() {
    return instance.setQuery(query);
  }

}
