## Module 3

In this module we'll learn the basics of [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.0.2.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data).  Spring Data JPA is basic ORM mapper to map java objects to relational databases. 

cd into `03-spring-data`


### Update our Dependencies

Add the `spring-boot-starter-data-jpa` to your `pom.xml`

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

Run a `mvn dependency:tree`. Inspect what libraries the starter pulled in for you

Add these dependencies to your `pom.xml`.   

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
	<scope>runtime</scope>
</dependency>

<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>
```

#### Important Concepts

* Spring Boot database starters will automatially attempt to wire-up an in-memory database. H2 is a popular in-memory database supported by Spring Boot.

Add the following to `application-local.yml`

```yml
spring:
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    database: h2
    hibernate:
      ddl-auto: create-drop
```

#### Important Concepts
* This will provide a browser based console Spring Boot will auto-configure for us.
* H2 Console is only intended for development use. Take care to ensure it is not enabled in production.

### Update the Repository

We are going to replace the hard coded candy objects with an in-memory relational database. 

Replace the `CandyJarRepository` with this interface

```java
import io.candyjar.model.Candy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandyJarRepository extends JpaRepository<Candy, Long> {
    Optional<Candy> findById(long id);
    Optional<Candy> findByName(String name);
}
```

### Update our `Candy` object. 

Annotate the class with `@Entity`.
Annotate the `id` field with `@Id` and `@GeneratedValue`

```java
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Candy {
	
	@Id
	@GeneratedValue
	private long id;
	...
	...
```

#### Important Concepts

* `@Entity` annotation declares this object as a JPA managed entity.
* `@Id` declares this field as the primary key of the entity
* `@GeneratedValue` Identifies the type of primary key that will be used. 

Run the application. 

`mvn spring-boot:run -Dspring-boot.run.profiles=local`

Hit the following URL in Chrome. Your candy jar should still function with an in-memory database. 

* [http://localhost:8080/](http://localhost:8080/)

Hit the following URL in Chrome. Use `jdbc:h2:mem:testdb` as the JDBC URL. You should see the H2 console

* [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

### Summary

Congratulations you have just completed module 3. 
Please cd into `04-spring-data` to prepare for the next module. 
