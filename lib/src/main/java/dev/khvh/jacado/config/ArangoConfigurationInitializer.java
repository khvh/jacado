package dev.khvh.jacado.config;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.DbName;
import com.arangodb.entity.LoadBalancingStrategy;
import com.arangodb.mapping.ArangoJack;
import com.arangodb.model.PersistentIndexOptions;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.khvh.jacado.Model;
import dev.khvh.jacado.data.Document;
import dev.khvh.jacado.data.Index;
import dev.khvh.jacado.data.Indexed;

public class ArangoConfigurationInitializer {

  ArangoConfig config;

  public ArangoDB arangoDB() {
    var db = initBuilder().build();

    if (!db.db(DbName.of(config.db())).exists()) {
      db.createDatabase(DbName.of(config.db()));
    }

    return db;
  }

  public ArangoDB arangoDB(List<Class<? extends Model>> models) {
    return arangoDB(models, false);
  }

  public ArangoDB arangoDB(List<Class<? extends Model>> models, boolean dropCollections) {
    var db = arangoDB();

    models.forEach(m -> {
      var coll = documentName(m);
      var indices = indices(m);

      if (coll.isPresent()) {
        if (dropCollections) {
          try {
            db.db(DbName.of(config.db())).collection(coll.get()).drop();
          } catch (ArangoDBException e) {
            System.out.printf("Collection '%s' already dropped, skipping", coll.get());
          }
        }

        try {
          db.db(DbName.of(config.db())).createCollection(coll.get());
        } catch (ArangoDBException e) {
          System.out.printf("Collection '%s' already exists, skipping", coll.get());
        }
      }

      var collection = db.db(DbName.of(config.db())).collection(coll.orElseThrow());

      indices.forEach(idx -> {
        try {
          collection.ensurePersistentIndex(
            List.of(idx.name), new PersistentIndexOptions().unique(idx.unique)
          );
        } catch (ArangoDBException e) {
          System.out.printf("Index '%s' already exists, skipping", idx.name);
        }
      });
    });

    return db;
  }

  private ArangoDB.Builder initBuilder() {
    var jack = new ArangoJack();

    jack.configure(mapper -> {
      mapper.registerModule(new JavaTimeModule());
      mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    });

    var builder = new ArangoDB.Builder()
      .serializer(jack)
      .loadBalancingStrategy(LoadBalancingStrategy.ROUND_ROBIN)
      .user(config.username())
      .password(config.password());

    config.hosts().forEach(h -> builder.host(h.host(), h.port()));

    return builder;
  }

  public void setConfig(ArangoConfig config) {
    this.config = config;
  }

  private Optional<String> documentName(Class<? extends Model> model) {
    var a = model.getAnnotation(Document.class);

    return a != null ? Optional.of(a.name()) : Optional.empty();
  }

  private List<Index> indices(Class<? extends Model> model) {
    return Arrays.stream(model.getFields()).map(field -> {
      var indexed = field.getAnnotation(Indexed.class);

      return indexed == null
        ? null
        : new Index().setName(field.getName()).setUnique(indexed.unique());
    })
      .filter(Objects::nonNull)
      .toList();
  }

}
