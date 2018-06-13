package io.candyjar.candyjar.io.candyjar.controller;

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
public class CandyJarControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAllCandy() {
        String body = this.restTemplate.getForObject("/candy", String.class);

        List<String> candy = JsonPath.read(body, "$..name");
        assertThat(candy).containsOnly("Gummy Bear", "Swedish Fish", "Twix", "Sweettarts");
    }

    @Test
    public void testGetCandyById() {
        String body = this.restTemplate.getForObject("/candy/1", String.class);

        Map actualCandy = JsonPath.read(body, "$");
        assertThat(actualCandy)
                .containsEntry("id", 1)
                .containsEntry("name", "Gummy Bear")
                .containsEntry("quantity", 25);
    }

    @Test
    public void testGetCandyByName() {
        String body = this.restTemplate.getForObject("/candy/name/Twix", String.class);

        Map actualCandy = JsonPath.read(body, "$");
        assertThat(actualCandy)
                .containsEntry("id", 3)
                .containsEntry("name", "Twix")
                .containsEntry("quantity", 25);
    }
}
