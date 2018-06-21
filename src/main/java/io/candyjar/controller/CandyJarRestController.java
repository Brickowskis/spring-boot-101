package io.candyjar.controller;

import io.candyjar.model.Candy;
import io.candyjar.service.CandyJarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CandyJarRestController {

	private final CandyJarService candyJarService;

	@GetMapping("/candy")
	public List<Candy> getCandy() {
		return candyJarService.findAll();
	}

	@GetMapping("/candy/{id}")
	public Candy getCandyById(@PathVariable Long id) {
		return candyJarService.findById(id);
	}

	@GetMapping("/candy/name/{name}")
	public Optional<Candy> getCandyByName(@PathVariable String name) {
		return candyJarService.findByName(name);
	}

	@PostMapping("/candy")
	public Candy addCandy(@RequestBody Candy candy) {
		return candyJarService.save(candy);
	}

	@PutMapping("/candy/{id}")
	public Candy updateCandy(@PathVariable Long id, @RequestBody Candy candy) {
		return candyJarService.update(candy);
	}
}
