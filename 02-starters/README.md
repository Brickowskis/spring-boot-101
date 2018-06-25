## Module 2

In module 2 we'll take a closer look at the `spring-boot-starters` and [Actuators](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)
* We are going to experiment with our transitive dependencies by swapping out [logback](https://logback.qos.ch/) for [log4j2](https://logging.apache.org/log4j/2.x/).  
* We will turn on actuator support for diagnostics and management of our application. 

### Change logging implementation

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

Run `mvn dependency:tree`

You'll notice the logback dependencies are gone. 

### Turning on Actuator Support

Add the following to your `pom.xml`

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

For security it may not be ideal to have all possible actuators exposed. 
By default all actuators except for `shutdown` are enabled. 
Spring Boot has a mechanism to enable/disable the different endpoints. 

Add the following to `application.yml`

```yml
management:
  endpoints:
    web:
      exposure:
        include: beans, configprops, env, health, info, mappings, metrics, shutdown
```

#### Important concepts
* There are two ways to invoke actuators. `jmx` and `http`.
* `http` or web actuators are exposed as endpoints on your application. They can be invoked via curl or a web browser.
* The above config exposes only the actuators listed in the `include`. 
* All other are disabled. The `exclude` takes precedence over `include`

Add the following to `application-local.yml`

```yml
management:
  endpoint:
    shutdown:
      enabled: true
```

#### Important concepts
* This turns on the shutdown actuator. 

Run the application 

* `mvn spring-boot:run`

Actuators are available at the endpoint  `/actuator/<ID>`
Hit the various actuator endpoints

* [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

* [http://localhost:8080/actuator/beans](http://localhost:8080/actuator/beans)

* [http://localhost:8080/actuator/mappings](http://localhost:8080/actuator/mappings)

Hit an actuator that was not enabled. 

* [http://localhost:8080/actuator/threaddump](http://localhost:8080/actuator/threaddump)

You should get a 404 error. See if you can figure out how to turn it on.  


### Summary

Congratulations you have just created your first spring boot application. 
Please cd into `03-spring-data` to prepare for the next module. 
