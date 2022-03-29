package dev.khvh.jacado.data;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.arangodb.ArangoCursor;

/**
 * Repository defines some methods to be implemented by the implementing Repository
 *
 * @param <T> class type to be returned from queries
 */
public interface Repository<T> {

  ArangoCursor<T> query(String q, Map<String, Object> props);

  T persist(T entity);

  List<T> persist(List<T> entities);

  T update(String id, T entity);

  void delete(T entity);

  List<T> list();

  List<T> list(List<String> ids);

  List<T> list(String key, Object value);

  List<T> list(String q, Map<String, Object> props);

  Optional<T> findOne(String id);

  Optional<T> findOne(String key, Object value);

  Optional<T> findOne(String q, Map<String, Object> props);

}
