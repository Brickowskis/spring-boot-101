package io.candyjar.candyjar.io.candyjar.controller;

import io.candyjar.model.Candy;
import io.candyjar.repository.CandyJarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
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
