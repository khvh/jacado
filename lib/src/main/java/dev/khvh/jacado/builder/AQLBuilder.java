package dev.khvh.jacado.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.fasterxml.jackson.core.type.TypeReference;
import dev.khvh.jacado.Model;
import dev.khvh.jacado.data.Builder;

public class AQLBuilder<T extends Model> implements Builder<T> {
  private String aql = "";

  public AQLBuilder() {

  }

  public AQLBuilder(ArangoDatabase db) {

  }

  public Builder<T> createCollection(String collectionName) {
    return this;
  }

  @Override
  public Builder<T> collection(String collectionName) {
    return null;
  }

  @Override
  public Builder<T> insert(T data) {

    return this;
  }

  @Override
  public Builder<T> filter(String key, Object value) {

    return this;
  }

  @Override
  public Builder<T> update(String key, Object value) {
    return null;
  }

  @Override
  public Builder<T> replace(String key, Object value) {
    return null;
  }

  @Override
  public T one() {
    return null;
  }

  @Override
  public T one(TypeReference<T> ref) {
    return null;
  }

  @Override
  public List<T> list() {
    return null;
  }

  @Override
  public List<T> list(TypeReference<List<T>> ref) {
    return null;
  }

  @Override
  public String toAQL() {
    return "";
  }

}
