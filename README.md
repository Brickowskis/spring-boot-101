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

```
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.2.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
```

Next add the following dependencies

```
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

```
   <packaging>jar</packaging>
```

```
  <plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
  </plugin>
```

At this point the application should compile. 

Modify the `CandyJarApplication` so it looks like this

```
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

```
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("candy", candyJarService.findAll());
		modelAndView.setViewName("index");
		return modelAndView;
	}
```

Add the following methods to `CandyJarRestController`

```
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

### Module 3

### Module 4