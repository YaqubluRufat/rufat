package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department")
public class Department extends BaseEntity {
    @Column(unique = true,nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;
    @OneToMany(mappedBy = "department")
    private List<Product> product;
    @Version
    private Long version;
}
