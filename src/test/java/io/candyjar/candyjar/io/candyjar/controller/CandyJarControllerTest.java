package io.candyjar.candyjar.io.candyjar.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CandyJarControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCandyApi() {
        String body = this.restTemplate.getForObject("/candy", String.class);
        assertThat(body).contains("Gummy Bear", "Swedish Fish", "Twix", "Sweettarts");
    }
}
