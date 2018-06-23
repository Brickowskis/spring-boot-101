package io.candyjar.controller;

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
