# Elasticsearch Spring Boot Geosearch

RESTful API that allows a user to find Starbucks locations based on one or more of the following criteria:

  * Location (latitude, longitude & distance)
  * City
  * State
  * Zip code
  * Products (e.g. oven-warmed food)
  * Services (e.g. wireless hotstop)

## Technologies

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

1. Set the Spring profile to `default` in application.yml (only if previously changed).
```YAML
spring.profiles.active: default
```

2. Run the Java project using `mvn spring-boot:run` on the command line or using your favorite IDE.

## Running on Docker

### Prerequisites
* [Docker Engine](https://docs.docker.com/engine/installation/)

### Steps

1. Set the Spring profile to `docker` in `application.yml`
```YAML
spring.profiles.active: docker
```

2. From the root directory of this project, run `docker-compose up` on the command line.

3. Run the Java project using `mvn spring-boot:run` on the command line or using your favorite IDE.

## Running on AWS Elasticsearch Service & RDS

### Prerequisites
* AWS Elasticsearch Service cluster
* RDS instance up and running (or any other MySQL database)

### Steps

1. Set the Spring profile to `aws` in application.yml  
```YAML
spring.profiles.active: aws
```
2. Modify the `application.yml` file with your Elasticsearch & RDS settings
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

3. Run the Java project using `mvn spring-boot:run` on the command line or using your favorite IDE.

## Acknowledgements

Starbucks location data provided by [Socrata](https://opendata.socrata.com/Business/All-Starbucks-Locations-in-the-US/txu4-fsic)
