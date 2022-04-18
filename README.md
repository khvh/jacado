# Jacado

## Description

Jacado is small project to help with the usage of ArangoDB in java.

It uses arango-java-driver to do the magic and provides a way to use it with a bit less boilerplate.

## Install

Currently, hosted in GitHub packages.

1. Add the repository reference.

```groovy
repositories {
    maven {
        name = "KHVH"
        url = uri("https://mvn.kube.khvh.dev/artifactory/khvh/")
    }
}
```

2. Include the library and dependencies.

```groovy
dependencies {

    implementation 'dev.khvh:jacado:0.0.3'
    implementation 'com.arangodb:arangodb-java-driver:6.16.1'
    implementation 'com.arangodb:jackson-dataformat-velocypack:3.0.0'

}
```

## Usage

1. Create the database configuration

```java
public class AppDatabase implements Database {

  public ArangoDB arangoDB() {
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

    var db = builder.build();

    if (!db.db(DbName.of(config.db())).exists()) {
      db.createDatabase(DbName.of(config.db()));
    }

    return db;
  }

  @Override
  public ArangoCollection getCollection(String s) {
    return getDatabase().collection(s);
  }

  @Override
  public ArangoDatabase getDatabase() {
    return arangoDB().db(DbName.of(arangoConfig.db()));
  }

}
```

2. Create a base Repository with the config

```java
public abstract class BaseRepository <T extends Model> extends ArangoRepository<T> {

  @Inject
  AppDatabase database;

  @PostConstruct
  void initialize() {
    super.init(database);
  }

}

```

3. Create model & repository

```java
public class Sample extends Model {
  public String name;
}
```

```java
@Collection(name = "sample")
public class SampleRepository extends BaseRepository<Sample> {
}
```

4. Use in your resources/services/wherever

```java
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/api/v1/samples")
public class SampleResource {

  @Inject
  SampleRepository sampleRepository;

  @GET
  public List<Sample> list() {
    return sampleRepository.list();
  }

}
```

## As query builder (WIP)

### As a standalone instance

```java
@ApplicationScoped
public class ExampleService {

  @Inject
  AQL aql;

  public List<Example> findAllExamplesByName(String name) {
    return aql
      .for(Collection.Examples)
      .filter("name", name)
      .findAll();
  }

  public Optional<Example> findOneByKey(String key) {
    return aql
      .for(Collection.Examples)
      .filter("_key", key)
      .findOneOptional();
  }

}
```

### As a builder

```java
@ApplicationScoped
@Collection(name = "examples")
public class ExampleBuilder extends AQLBuilder {

  public List<Example> findAllExamplesByName(String name) {
    return aql
      .for(Collection.Examples)
      .filter("name", name)
      .findAll();
  }

  public Optional<Example> findOneByKey(String key) {
    return aql
      .for(Collection.Examples)
      .filter("_key", key)
      .findOneOptional();
  }

}
```

## License

[MIT](./LICENSE)
