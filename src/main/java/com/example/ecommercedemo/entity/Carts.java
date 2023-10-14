package com.example.ecommercedemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carts")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carts")
    private List<CartsItems> cartItems;
    private Integer cartTotal;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;
}
