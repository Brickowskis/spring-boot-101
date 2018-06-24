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
Add the following test `io.candyjar.CandyJarApplicationTests`

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class CandyJarApplicationTests {

	@Test
	public void contextLoads() {
	}
}
```

Add the following test `io.candyjar.repository.DataJpaExampleTest`

```java
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

Add the following test `io.candyjar.controller.MockMvcSampleTest`

```java

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

Add the following test `io.candyjar.controller.RestTemplateExampleTest`

```java
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
