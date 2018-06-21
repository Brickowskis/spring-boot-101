package io.candyjar.service;

import io.candyjar.model.Candy;

import java.util.List;
import java.util.Optional;

public interface CandyJarService {
	Optional<Candy> findByName(String name);

	Candy save(Candy candy);

	List<Candy> findAll();

	Candy findById(Long id);

	Candy update(Candy candy);
}
