package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private Integer price;
    private Currency currency;
    private String color;
    @ManyToOne
    @JoinColumn(name = "productType_id")
    private ProductType productType;

}

