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
public class Restaurant extends BaseEntity {
    private String name;
    private String location;
    private String telephonePhone;
    @OneToOne(mappedBy = "restaurant")
    private User user;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<MenuItem> menuItem;
    @OneToMany(mappedBy = "restaurant")
    private List<Order> order;
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    private List<Review> review;

}
