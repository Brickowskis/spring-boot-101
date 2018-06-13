package io.candyjar.controller;

import io.candyjar.model.Candy;
import io.candyjar.repository.CandyJarRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CandyJarController {

    private CandyJarRepository repository;

    public CandyJarController(CandyJarRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/candy")
    public List<Candy> getCandy() {
        return repository.findAll();
    }

    @GetMapping("/candy/{id}")
    public Optional<Candy> getCandyById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping("/candy/name/{name}")
    public Optional<Candy> getCandyByName(@PathVariable String name) {
        return repository.findByName(name);
    }

    @PostMapping("/candy")
    public Candy addCandy(@RequestBody Candy candy) {
        return repository.save(candy);
    }

    @PutMapping("/candy/{id}")
    public Candy updateCandy(@PathVariable Long id, @RequestBody Candy candy) {
        Candy repoCandy = repository.getOne(id);
        repoCandy.setName(candy.getName());
        repoCandy.setQuantity(candy.getQuantity());
        return repository.save(repoCandy);
    }
}
