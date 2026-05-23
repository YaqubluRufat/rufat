package com.example.demo.Entity;

import com.example.demo.Dto.MarketDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Products extends BaseEntity {
@Column(unique = true,nullable = false)
    private String name;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;


}
