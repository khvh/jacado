package dev.khvh.jacado.builder;

import java.awt.image.SampleModel;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.khvh.jacado.Model;
import dev.khvh.jacado.data.Database;
import dev.khvh.jacado.data.Document;

@SuppressWarnings("unchecked")
public class AQLBuilder<T extends Model> {

  private final UUID id;
  private AQLQuery query;
  private Database database;
  private Class<T> itemClass;

  public AQLBuilder(Database database) {
    id = UUID.randomUUID();
    this.database = database;
    parseType();
  }

  public AQL<T> use(String collection) {
    return new AQL<T>(collection, new AQLBuilder<T>(database));
  }

  public List<Model> exec() {
    return null;
  }

  public AQLBuilder<T> setQuery(AQLQuery query) {
    this.query = query;

    return this;
  }

  public AQLQuery getQuery() {
    return query;
  }

  public String toString() {
    return "[" + id + "]";
  }

  public List<T> findAll() {
    return database
      .getDatabase()
      .query(String.join(" ", query.getQuery()), query.getProps(), itemClass)
      .stream()
      .toList();
  }

  public Optional<T> findOne() {
    query.getQuery().add(" RETURN x");

    return database
      .getDatabase()
      .query(String.join(" ", query.getQuery()), query.getProps(), itemClass)
      .stream()
      .findFirst();
  }

  private void parseType() {
    Type genericSuperClass = getClass();

    var parametrizedType = (ParameterizedType) getClass()
      .getGenericSuperclass();
//    ParameterizedType parametrizedType = ((ParameterizedType) getClass()
//      .getGenericSuperclass()).getActualTypeArguments()[0];

    try {
      this.itemClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
