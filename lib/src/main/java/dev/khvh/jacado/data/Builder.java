package dev.khvh.jacado.data;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * AQL builder
 * @param <T>
 */
public interface Builder<T> {

  Builder<T> createCollection(String collectionName);

  Builder<T> collection(String collectionName);

  Builder<T> insert(T data);

  Builder<T> filter(String key, Object value);

  Builder<T> update(String key, Object value);

  Builder<T> replace(String key, Object value);

  T one();

  T one(TypeReference<T> ref);

  List<T> list();

  List<T> list(TypeReference<List<T>> ref);

  String toAQL();

}
