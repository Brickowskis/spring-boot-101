package io.candyjar.service;

import io.candyjar.model.Candy;
import io.candyjar.repository.CandyJarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service("candyJarService")
public class CandyServiceServiceImpl implements CandyJarService {

	private final CandyJarRepository repository;

	@Override
	public Optional<Candy> findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public Candy save(Candy candy) {
		return repository.save(candy);
	}

	@Override
	public List<Candy> findAll() {
		return repository.findAll();
	}

	@Override
	public Candy findById(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Candy update(Candy candy) {
		Candy repoCandy = repository.getOne(candy.getId());
		if(null != repoCandy) {
			repoCandy.setName(candy.getName());
			repoCandy.setQuantity(candy.getQuantity());
			return repository.save(repoCandy);
		} else {
			return repository.save(candy);
		}
	}
}
