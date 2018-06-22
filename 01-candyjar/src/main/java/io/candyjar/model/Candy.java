package io.candyjar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Candy {

    private long id;
    private String name;
    private int quantity;

    public Candy(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
