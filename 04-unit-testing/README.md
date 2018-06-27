## Module 4

In the final module we'll add unit tests for our applications. 

cd into `04-unit-tesing`

Add the following to your `pom.xml`

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
	<scope>test</scope>
</dependency>
```
Run a `mvn dependency:tree`. Inspect the additional libraries the starter pulled in. 

#### Important concepts
* The `spring-boot-starter-test` brings in commonly used testing libraries
  * [JUnit](https://junit.org/junit4/): The de-facto standard for unit testing Java applications.
  * [Spring Test & Spring Boot Test](https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/testing.html#integration-testing): Utilities and integration test support for Spring Boot applications.
  * [AssertJ](https://joel-costigliola.github.io/assertj/): A fluent assertion library.
  * [Hamcrest](http://hamcrest.org/JavaHamcrest/): A library of matcher objects (also known as constraints or predicates).
  * [Mockito](http://site.mockito.org/): A Java mocking framework.
  * [JSONassert](https://github.com/skyscreamer/JSONassert): An assertion library for JSON.
  * [JsonPath](https://github.com/json-path/JsonPath): XPath for JSON.


### Testing the Spring Context 

Add the following test `io.candyjar.CandyJarApplicationTests`

```java
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CandyJarApplicationTests {

	@Test
	public void contextLoads() {
	}
}
```
Run the test. It should pass. This test validates the spring context loads successfully. 

### Testing JPA Functionality

Add the following test `io.candyjar.repository.DataJpaExampleTest`

```java
import io.candyjar.model.Candy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DataJpaExampleTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CandyJarRepository repository;

    @Test
    public void testAddCandy(){

        Optional<Candy> missingCandy = this.repository.findByName("Krackel");
        assertThat(missingCandy.isPresent()).isFalse();

        this.entityManager.persist(new Candy("Krackel", 45));

        Candy actualCandy = this.repository.findByName("Krackel").get();
        assertThat(actualCandy).isEqualTo(expectedCandy());
    }

    private Candy expectedCandy() {
        Candy expectedCandy = new Candy("Krackel", 45);
        expectedCandy.setId(2);
        return expectedCandy;
    }
}
```
Run the test. It should pass. 

#### Important Concepts

* `@RunWith(SpringRunner.class)` tells JUnit to run using Spring's testing support.
* `@DataJpaTest` configures an in-memory database, scans for `@Entity` classes and confgirues the JPA repository.
* `TestEntityManager` is an alternative to the standard `EntityManager`. It's designed specifically for tests. 

### Testing REST Controllers

Add the following test `io.candyjar.controller.MockMvcSampleTest`

```java
import io.candyjar.model.Candy;
import io.candyjar.service.CandyJarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcSampleTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CandyJarService candyJarService;

    @Test
    public void testHomePage() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void testAllCandyApi() throws Exception {

        given(candyJarService.findAll()).willReturn(candy());

        String expectedContent = "[{\"id\":1,\"name\":\"Twizzlers\",\"quantity\":100}]";
        mvc.perform(get("/candy"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    public void testGetCandyByName() throws Exception {

        given(candyJarService.findByName("Twizzlers")).willReturn(twizzlers());

        String expectedContent = "{\"id\":1,\"name\":\"Twizzlers\",\"quantity\":100}";
        mvc.perform(get("/candy/name/Twizzlers"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    private List<Candy> candy() {
        return Collections.singletonList(twizzler());
    }

    private Candy twizzler() {
        Candy twizzlers = new Candy("Twizzlers", 100);
        twizzlers.setId(1);
        return twizzlers;
    }

    private Optional<Candy> twizzlers() {
        return Optional.of(twizzler());
    }

}
```
Run the test. It should pass. 

#### Important Concepts

* `@SpringBootTest` bootstraps a local Spring Boot environment for testing
* `@AutoConfigureMockMvc` configures a Mock MVC environment
* `@MockBean` injects a mock bean instance in place of a real bean. 
* `given(...)` is the BDD mockito style syntax to setup behavior on our mock objects. 

### Alternative way to Test the REST Controllers

Add the following test `io.candyjar.controller.RestTemplateExampleTest`

```java
import com.jayway.jsonpath.JsonPath;
import io.candyjar.model.Candy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateExampleTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAllCandy() {
        String body = this.restTemplate.getForObject("/candy", String.class);

        List<String> candy = JsonPath.read(body, "$..name");
        assertThat(candy)
                .isNotEmpty()
                .contains("Twix");
    }

    @Test
    public void testGetCandyById() {
        Candy actualCandy = this.restTemplate.getForObject("/candy/1", Candy.class);

        assertThat(actualCandy)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Twix")
                .hasFieldOrPropertyWithValue("quantity", 5);
    }

    @Test
    public void testGetCandyByName() {
        String body = this.restTemplate.getForObject("/candy/name/Twix", String.class);

        Map actualCandy = JsonPath.read(body, "$");
        assertThat(actualCandy)
                .containsEntry("id", 1)
                .containsEntry("name", "Twix")
                .containsEntry("quantity", 5);
    }

    @Test
    public void testUpdateCandy() {

        //add a new candy for this test so it's independent of the other tests.
        Candy gummyBears = this.restTemplate.postForObject("/candy", new Candy("Gummy Bears", 50), Candy.class);

        this.restTemplate.put("/candy/" + gummyBears.getId(), new Candy("Gummy Bears", 0));
        Candy updatedCandy = this.restTemplate.getForObject("/candy/name/Gummy Bears", Candy.class);
        assertThat(updatedCandy)
                .hasFieldOrPropertyWithValue("name", "Gummy Bears")
                .hasFieldOrPropertyWithValue("quantity", 0);
    }

    @Test
    public void testAddCandy() {

        this.restTemplate.postForObject("/candy", new Candy("Buckeyes", 20), Candy.class);

        Candy actualCandy = this.restTemplate.getForObject("/candy/name/Buckeyes", Candy.class);
        assertThat(actualCandy)
                .hasFieldOrPropertyWithValue("name", "Buckeyes")
                .hasFieldOrPropertyWithValue("quantity", 20);
    }
}
```
Run the test. It should pass. 

#### Important Concepts
* Sometimes it's more beneficial to execute the controllers as a client would. 
* 'TestRestTemplate' is made available by `@SpringBootTest` and pre-configured for local integration testing. 
* `SpringBootTest.WebEnvironment.RANDOM_PORT` configures a web environment running on a random port to avoid conflicts. 

### Summary

Congratulations you have just completed module 4. We hope you have enjoyed this introduction to Spring Boot

