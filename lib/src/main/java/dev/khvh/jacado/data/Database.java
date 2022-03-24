package dev.khvh.jacado.data;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDatabase;

public interface Database {

  ArangoCollection getCollection(String name);

  ArangoDatabase getDatabase();

}
