# Elasticsearch Spring Boot Geosearch

RESTful API that allows a user to find Starbucks locations based on one of the following criteria:

  * Location (latitude, longitude & distance)
  * City
  * State
  * Zip code
  
## Geosearch Options

This project demonstrates two ways in which you can utilize Elasticsearch's geospatial search capabilities via the Spring Data Elasticsearch library.

1. **ElasticsearchRepository method** - implementation automatically taken care of by Spring Data Elasticsearch via naming convention `findByLocationNear()`
```java
@RepositoryRestResource(path = "/starbucks-locations", collectionResourceRel = "/starbucks-locations")
public interface StarbucksSearchRepository extends ElasticsearchRepository<Starbucks, Long>, StarbucksSearchRepositoryCustom {

  Page<Starbucks> findByLocationNear(@Param("location") Point point, @Param("distance") Distance distance, Pageable pageable);

}
```
2. **Custom Repository method** - implementation defined in `StarbucksSearchRepositoryImpl`
```java
@Repository
public class StarbucksSearchRepositoryImpl implements StarbucksSearchRepositoryCustom {

	private final JestClient jestClient;
	private final JestElasticsearchTemplate elasticsearchTemplate;

	public StarbucksSearchRepositoryImpl(JestClient jestClient) {
		this.jestClient = jestClient;
		this.elasticsearchTemplate = new JestElasticsearchTemplate(this.jestClient);
	}

	@Override
	public Page<Starbucks> findByLocationWithin(Point point, Distance distance, Pageable pageable) {
		return elasticsearchTemplate.queryForPage(getGeoQuery(point, distance, pageable), Starbucks.class);
	}

	private CriteriaQuery getGeoQuery(Point point, Distance distance, Pageable pageable) {
		return new CriteriaQuery(
				new Criteria("location").within(point, distance),
				pageable
		);
	}

}
```
## Technologies

* [Spring Boot](https://projects.spring.io/spring-boot/)
* [Spring Data Elasticsearch](https://projects.spring.io/spring-data-elasticsearch/)
* [Spring Data JPA](https://projects.spring.io/spring-data-jpa/)
* [Spring Data REST](https://projects.spring.io/spring-data-rest/)
* [Spring Data Jest](https://github.com/VanRoy/spring-data-jest)
* [Docker](https://www.docker.com/)
* [AWS Elasticsearch Service](https://aws.amazon.com/elasticsearch-service/)
* [AWS Relational Database Service](https://aws.amazon.com/rds/)

## Configuration Options

| Spring Profile | Database | Elasticsearch |
|:--------------:|:--------:|:-------------:|
| default | in-memory H2 | in-memory |
| docker | [latest MySQL image](https://hub.docker.com/r/library/mysql/) | [Elasticsearch 2.4.5-alpine image](https://hub.docker.com/r/library/elasticsearch/) |
| aws | AWS RDS | AWS Elasticsearch Service |

## Running Locally

### Prerequisites
* Java 8
* Maven

### Steps

1. Set the Spring profile to `default` in `application.yml` (only if previously changed)
```YAML
spring.profiles.active: default
```

2. Run the Java project using `mvn spring-boot:run` on the command line or using your favorite IDE

## Running on Docker

### Prerequisites
* [Docker Engine](https://docs.docker.com/engine/installation/)

### Steps

1. Set the Spring profile to `docker` in `application.yml`
```YAML
spring.profiles.active: docker
```

2. From the root directory of this project, run `docker-compose up` on the command line

3. Run the Java project using `mvn spring-boot:run` on the command line or using your favorite IDE

## Running on AWS Elasticsearch Service & RDS

### Prerequisites
* AWS Elasticsearch Service cluster
* RDS instance up and running (or any other MySQL database)

### Steps

1. Set the Spring profile to `aws` in `application.yml`  
```YAML
spring.profiles.active: aws
```
2. Modify the `application.yml` file with your Elasticsearch & RDS settings.
```YAML
spring:
  profiles: aws
  data.jest:
    uri: https://[AWS_ELASTICSEARCH_URI]  # URI for AWS Elasticsearch index
    aws-region: [AWS_REGION_NAME]
  datasource:  # RDS settings
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://[AWS_DB_HOST]:3306/starbucks
    username: [DB_USERNAME]
    password: [DB_PASSWORD]
```

3. Run the Java project using `mvn spring-boot:run` on the command line or using your favorite IDE

## Running the Tests

There are a complete set of unit tests covering core search capabilities as well as data population/synchronization from a MySQL database.  To run the tests from the command line:

```
mvn test
```

## Acknowledgements

Starbucks location data provided by [Socrata](https://opendata.socrata.com/Business/All-Starbucks-Locations-in-the-US/txu4-fsic)
