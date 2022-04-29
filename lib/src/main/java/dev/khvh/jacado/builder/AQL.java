package dev.khvh.jacado.builder;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import java.util.UUID;

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

  public AQL<T> filter(String key, Object value) {
    var k = NanoIdUtils
      .randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 5);
    var v = NanoIdUtils
      .randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 5);

    query.getQuery().add(
      String.format(" FILTER x.@%s == @%s ", k, v)
    );

    query.getProps().put(k, key);
    query.getProps().put(v, value);

    return this;
  }

  public AQLBuilder<T> build() {
    return instance.setQuery(query);
  }

}
