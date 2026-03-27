package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "product_type")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL)
    private List<Product> products;
    @OneToMany(mappedBy = "productType",cascade = CascadeType.ALL)
    private List<UserProduct>userProducts;



}
