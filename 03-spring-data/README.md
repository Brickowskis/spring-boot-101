## Module 3

In this module you'll hook up Spring Data JPA

cd into `03-spring-data

Add these dependencies to your `pom.xml`

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

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

Replace our `CandyJarRepository` with this interface

```java
import io.candyjar.model.Candy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandyJarRepository extends JpaRepository<Candy, Long> {
    Optional<Candy> findById(long id);
    Optional<Candy> findByName(String name);
}
```

Update our `Candy` object. 

Annotate the class with `@Entity`.
Annotate the `id` field with `@Id` and `@GeneratedValue`

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Candy {
	
	@Id
	@GeneratedValue
	private long id;
```
