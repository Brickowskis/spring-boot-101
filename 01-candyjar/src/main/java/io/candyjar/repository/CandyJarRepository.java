package io.candyjar.repository;

import io.candyjar.model.Candy;
import org.springframework.stereotype.Repository;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class CandyJarRepository {

    public Optional<Candy> findById(long id) {
        Candy candy = new Candy("Twix", 30);
        candy.setId(1);
        return Optional.of(candy);
    }

    public Optional<Candy> findByName(String name) {
        Candy candy = new Candy("Twix", 30);
        candy.setId(1);
        return Optional.of(candy);
    }

    public Candy save(Candy candy) {
        return candy;
    }

    public Candy getOne(Long id) {
        Candy candy = new Candy("Twix", 30);
        candy.setId(1);
        return candy;
    }

    public List<Candy> findAll() {
        Candy candy = new Candy("Twix", 30);
        candy.setId(1);
        return Arrays.asList(candy);
    }
}