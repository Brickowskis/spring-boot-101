### Introduction to Spring Boot

This workshop will guide you through creating a simple Spring Boot application. The workshop will be divided into 4 modules. Each module will cover different aspects of Spring Boot. 

## What you'll Need 

* JDK 1.8
* Maven 3.3+
* Your favorite IDE

### How to complete this workshop

[Download](https://github.com/Brickowskis/spring-boot-101/archive/master.zip) and unzip the source for this guide, or clone it using Git

`git clone https://github.com/Brickowskis/spring-boot-101.git`


### Module 1

* cd into `01-candyjar`
 
* You'll see a project has been started for us. However it's currently not a Spring Boot application. 

`pom.xml`

First we want to use the `spring-boot-starter-parent` 

```xml
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>2.0.2.RELEASE</version>
	<relativePath/> <!-- lookup parent from repository -->
</parent>
```

Next add the following dependencies

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
	<scope>provided</scope>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <scope>runtime</scope>
</dependency>
```

Finally add the `spring-boot-maven-plugin` and change the packaging type to `jar`

```xml
   <packaging>jar</packaging>
```

```xml
  <plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
  </plugin>
```

At this point the application should compile. 

Modify the `CandyJarApplication` so it looks like this

```java
@SpringBootApplication
public class CandyJarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandyJarApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CandyJarRepository candyJarRepository) {
		return (evt) -> Arrays.asList(
				"Twix".split(","))
				.forEach(c -> candyJarRepository.save(new Candy( c, 5)));
	}
}
```

Add the following to `CandyJarController`

```java
@GetMapping("/")
public ModelAndView index() {
	ModelAndView modelAndView = new ModelAndView();
	modelAndView.addObject("candy", candyJarService.findAll());
	modelAndView.setViewName("index");
	return modelAndView;
}
```

Add the following methods to `CandyJarRestController`

```java
@GetMapping("/candy")
public List<Candy> getCandy() {
	return candyJarService.findAll();
}

@GetMapping("/candy/{id}")
public Optional<Candy> getCandyById(@PathVariable Long id) {
	return candyJarService.findById(id);
}

@GetMapping("/candy/name/{name}")
public Optional<Candy> getCandyByName(@PathVariable String name) {
	return candyJarService.findByName(name);
}

@PostMapping("/candy")
public Candy addCandy(@RequestBody Candy candy) {
	return candyJarService.save(candy);
}

@PutMapping("/candy/{id}")
public Candy updateCandy(@PathVariable Long id, @RequestBody Candy candy) {
	candy.setId(id);
	return candyJarService.update(candy);
}
```

Make sure the class is annotated with `@RestController`

At this point we can run the application

`mvn spring-boot:run`

Hit the following URL in Chrome

`http://localhost:8080/`

The following REST endpoints should also be exposed.

`http://localhost:8080/candy/`

`http://localhost:8080/candy/1`

`http://localhost:8080/candy/name/Twix`

### Module 2

In this module we'll swap out logback for log4j2 and turn on the Spring Boot [Actuators](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)

Add the following to your `pom.xml`

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Exclude this dependency 

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

Add the `log4j2` starter

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

Add the following to `application.yml`

```yml
management:
  endpoints:
    web:
      exposure:
        include: beans, configprops, env, health, info, mappings, metrics, shutdown
```

Add the following to `application-local.yml`

```yml
management:
  endpoint:
    shutdown:
      enabled: true
```

Run the application `mvn spring-boot:run`

Hit the various actuator endpoints

`http://localhost:8080/actuator/health`

`http://localhost:8080/actuator/beans`

`http://localhost:8080/actuator/mappings`

### Module 3

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

```

Update our `Candy` object. 

Annotate the class with `@Entity`.
Annotate the `id` field with `@Id` and `@GeneratedValue`

```java
@Entity
public class Candy {
	
	@Id
	@GeneratedValue
	private long id;
```
### Module 4