## Module 2

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