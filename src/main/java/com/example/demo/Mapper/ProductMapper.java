package com.example.demo.Mapper;

import com.example.demo.DTO.ProductDto;
import com.example.demo.DTO.ProductDtoIMPL;
import com.example.demo.Entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public Product toProduct (ProductDto productDto){
        Product product =new Product();
        product.setName(productDto.getName());
        product.setColor(productDto.getColor());
        product.setPrice(productDto.getPrice());
        return product;
    }
    public ProductDtoIMPL productDtoIMPL (Product product){
        ProductDtoIMPL productDtoIMPL = new ProductDtoIMPL();
        productDtoIMPL.setId(product.getId());
        productDtoIMPL.setName(product.getName());
        productDtoIMPL.setColor(product.getColor());
        productDtoIMPL.setPrice(product.getPrice());
        productDtoIMPL.setCurrency(product.getCurrency());
        return productDtoIMPL;
    }
    public List<ProductDtoIMPL> toProductDtoIMPLS(List<Product>products) {


        return products.stream().map(p -> {
            ProductDtoIMPL dto = new ProductDtoIMPL();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setColor(p.getColor());
            dto.setPrice(p.getPrice());
            dto.setCurrency(p.getCurrency());
            return dto;
        }).toList();


    }
}
