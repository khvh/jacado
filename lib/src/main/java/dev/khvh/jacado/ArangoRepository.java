package dev.khvh.jacado;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.entity.DocumentCreateEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.khvh.jacado.data.Database;
import dev.khvh.jacado.data.Document;
import dev.khvh.jacado.data.Ref;
import dev.khvh.jacado.data.Repository;

@SuppressWarnings("unchecked")
public abstract class ArangoRepository <T extends Model> implements Repository<T> {

  private Database database;
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

    if (itemClass.getAnnotation(Document.class) != null) {
      collectionName = itemClass.getAnnotation(Document.class).name();

      collection = database.getCollection(
        collectionName
      );
    }
  }

  public ArangoCursor<T> query(String q, Map<String, Object> props) {
    return database
      .getDatabase()
      .query(q, props, null, itemClass);
  }

  public T persist(T entity) {
    if (Arrays.stream(entity.getClass().getFields()).anyMatch(f -> f.getAnnotation(Ref.class) != null)) {
      var om = new ObjectMapper();

      var val = om.convertValue(entity, new TypeReference<Map<String, Object>>() {});
    }

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

  public T update(String key, Object value, T entity) {
    var doc = findOne(key, value).orElseThrow();

    collection.updateDocument(doc.getKey(), entity);

    return findOne(key, value).orElseThrow();
  }

  public void delete(String key) {
    delete(findOne(key).orElseThrow());
  }

  public void delete(T entity) {
    collection.deleteDocument(entity.getKey());
  }

  public void delete(String key, Object value) {
    var doc = findOne(key, value).orElseThrow();

    collection.deleteDocument(doc.getKey());
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

  public List<T> list(String key, Object value) {
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
