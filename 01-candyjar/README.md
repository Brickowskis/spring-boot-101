## Module 1

* cd into `01-candyjar`
 
* You'll see a project has been started for us. However it's currently not a Spring Boot application. It won't even compile in its current state. 

### Creating the POM

Modify your `pom.xml` to use the `spring-boot-starter-parent` 

```xml
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>2.0.2.RELEASE</version>
	<relativePath/> <!-- lookup parent from repository -->
</parent>
```
#### Important Concepts

* The `spring-boot-starter-parent` pom provides sensible maven defaults. It also provides the dependency managment for Spring Boot so you can omit the `version` tag in your dependencies.  

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

#### Important Concepts

* Spring Boot uses the concept of `starter` dependencies. The `starters` provide the dependencies you are likely to need for that type of functionality. 
* Run the command `mvn dependency:tree` and analyze the dependencies that are brought in by each starter. 

### Make the JAR executable

Add the `spring-boot-maven-plugin` and change the packaging type to `jar`

```xml
   <packaging>jar</packaging>
```

Add the `spring-boot-maven-plugin` to the plugins section of your pom. 

```xml
  <plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
  </plugin>
```

#### Important Concepts

* The `sprint-boot-maven-plugin` creates an exectuable jar during maven's `repackage` phase. 
* The `spring-boot-starter-parent` provides most of the configuration for the plugin. Take a peek inside the parent pom to see.


### Create the entry point

Modify the `CandyJarApplication` so it looks like this

```java
import io.candyjar.model.Candy;
import io.candyjar.repository.CandyJarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

#### Important Concepts

* `@SpringBootApplication` is a convenience annotation for `@SpringBootConfiguration`, `@ComponentScan`, `@EnableAutoConfiguration`
* `SpringApplication.run` is spring's mechanism to bootstrap the application.

### Exposing RESTful endpoints
Add the following method to `CandyJarController`

```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

...
...
...


@GetMapping("/")
public ModelAndView index() {
	ModelAndView modelAndView = new ModelAndView();
	modelAndView.addObject("candy", candyJarService.findAll());
	modelAndView.setViewName("index");
	return modelAndView;
}
```

Add the following methods to `CandyJarRestController` and annotate the class with `@RestController`

```java
import io.candyjar.model.Candy;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
...
...
...
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
#### Important Concepts

* These methods will expose RESTful endpoints on our application. 
* The `spring-boot-starter-web` is pulling in most of the dependencies we need. 

At this point we can run the application

`mvn spring-boot:run`

Hit the following URL in Chrome

* [http://localhost:8080/](http://localhost:8080/)

The following REST endpoints should also be exposed.

* [http://localhost:8080/candy/](http://localhost:8080/candy/)

* [http://localhost:8080/candy/1](http://localhost:8080/candy/1)

* [http://localhost:8080/candy/name/Twix](http://localhost:8080/candy/name/Twix)

Examine the contents of the jar file

`jar tvf candyjar-0.0.1-SNAPSHOT.jar`

You'll notice several things have been packaged into our jar to make an an uber runnable jar
You'll also notice another jar with a .original extension.

Examine the contents

`jar tvf candyjar-0.0.1-SNAPSHOT.jar.original`

You'll notice it's much smaller. This is what the jar looked like before modified by the `spring-boot-maven-plugin` in the `repackage` phase. 


### Summary

Congratulations you have just created your first spring boot application. 
Please cd into `02-starters` to prepare for the next module. 
