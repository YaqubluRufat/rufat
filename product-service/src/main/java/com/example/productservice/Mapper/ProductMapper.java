package com.example.productservice.Mapper;

import com.example.productservice.DTO.ProductDto;
import com.example.productservice.DTO.ProductDtoIMPL;
import com.example.productservice.Entity.Product;
import org.mapstruct.Mapper;
import org.yaml.snakeyaml.error.Mark;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct (ProductDto productDto);

    ProductDtoIMPL toProductDtoIMPL(Product product);
}
