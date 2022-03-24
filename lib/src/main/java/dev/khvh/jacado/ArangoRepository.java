package dev.khvh.jacado;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.entity.DocumentCreateEntity;
import dev.khvh.jacado.data.Collection;
import dev.khvh.jacado.data.Database;
import dev.khvh.jacado.data.Repository;

@SuppressWarnings("unchecked")
public abstract class ArangoRepository <T extends Model> implements Repository<T> {

  Database database;

  private ArangoCollection collection;
  private Class<T> itemClass;
  private String collectionName;

  public void setDatabase(Database database) {
    this.database = database;
  }

  public void init(Database database) {
    setDatabase(database);
    init();
  }

  public void init() {
    if (this.getClass().getAnnotation(Collection.class) != null) {
      collectionName = this.getClass().getAnnotation(Collection.class).name();

      collection = database.getCollection(
        collectionName
      );
    }

    Type genericSuperClass = getClass().getGenericSuperclass();

    ParameterizedType parametrizedType = null;

    while (parametrizedType == null) {
      if ((genericSuperClass instanceof ParameterizedType)) {
        parametrizedType = (ParameterizedType) genericSuperClass;
      } else {
        genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
      }
    }

    try {
      this.itemClass = (Class<T>) parametrizedType.getActualTypeArguments()[0];
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public ArangoCursor<T> query(String q, Map<String, Object> props) {
    return database
      .getDatabase()
      .query(q, props, null, itemClass);
  }

  public T persist(T entity) {
    var key = collection
      .insertDocument(
        entity
          .setCreatedAt(ZonedDateTime.now())
      )
      .getKey();

    return findOne(key).orElseThrow();
  }

  public List<T> persist(List<T> entities) {
    return collection
      .insertDocuments(entities)
      .getDocuments()
      .stream().map(DocumentCreateEntity::getKey)
      .map(k -> findOne(k).orElseThrow())
      .toList();
  }

  public T update(String id, T entity) {
    var key = collection
      .updateDocument(id, entity)
      .getKey();

    return findOne(key).orElseThrow();
  }

  public void delete(T entity) {
    collection.deleteDocument(entity.getKey());
  }

  public List<T> list() {
    return database
      .getDatabase()
      .query(
        "FOR x IN @@collectionName RETURN x",
        Map.of("@collectionName", collectionName),
        null,
        itemClass
      )
      .asListRemaining();
  }

  public List<T> list(List<String> ids) {
    return collection.getDocuments(ids, itemClass).getDocuments().stream().toList();
  }

  public List<T> list(String key, String value) {
    return database
      .getDatabase()
      .query(
        "FOR x IN @@collectionName FILTER x.@key == @value RETURN x",
        Map.of(
          "@collectionName", collectionName,
          "key", key,
          "value", value
        ),
        null,
        itemClass
      )
      .asListRemaining();
  }

  public List<T> list(String q, Map<String, Object> props) {
    return database
      .getDatabase()
      .query(q, props, null, itemClass)
      .asListRemaining();
  }

  public Optional<T> findOne(String id) {
    var doc = collection.getDocument(id, itemClass);

    return doc == null ? Optional.empty() : Optional.of(doc);
  }

  public Optional<T> findOne(String key, Object value) {
    return database
      .getDatabase()
      .query(
        "FOR x IN @@collectionName FILTER x.@key == @value RETURN x",
        Map.of(
          "@collectionName", collectionName,
          "key", key,
          "value", value
        ),
        null,
        itemClass
      )
      .stream()
      .findFirst();
  }

  public Optional<T> findOne(String q, Map<String, Object> props) {
    return database
      .getDatabase()
      .query(q, props, null, itemClass)
      .stream()
      .findFirst();
  }

}