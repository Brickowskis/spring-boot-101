package io.candyjar.candyjar.io.candyjar.controller;


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
import org.springframework.test.web.servlet.RequestBuilder;

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
