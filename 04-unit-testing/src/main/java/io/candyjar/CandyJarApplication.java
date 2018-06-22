package io.candyjar;

import io.candyjar.model.Candy;
import io.candyjar.repository.CandyJarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class CandyJarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandyJarApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CandyJarRepository candyJarRepository) {
		return (evt) -> Arrays.asList(
				"Twix".split(","))
				.forEach(c -> candyJarRepository.save(new Candy( c, 5)));
	}
}
