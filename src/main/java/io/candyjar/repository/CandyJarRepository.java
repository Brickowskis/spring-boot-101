package io.candyjar.repository;

import io.candyjar.model.Candy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandyJarRepository extends JpaRepository<Candy, Long> {
	Optional<Candy> findById(long id);
	Optional<Candy> findByName(String name);
}
