package io.candyjar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Candy {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private int quantity;

	public Candy(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}
}
