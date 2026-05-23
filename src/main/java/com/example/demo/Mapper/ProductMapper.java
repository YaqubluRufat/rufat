package com.example.demo.Mapper;

import com.example.demo.Dto.ProductDtoIMPL;
import com.example.demo.Dto.ProductsDto;
import com.example.demo.Entity.Products;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "marketId",target = "market.id")
    Products toProducts (ProductsDto productsDto);

    ProductDtoIMPL toDtoIMPL(Products products);
    List<ProductDtoIMPL>toList(List<Products>products);

}
