package com.example.demo.Mapper;

import com.example.demo.Dto.ProductDto;
import com.example.demo.Dto.ProductDtoIMPL;
import com.example.demo.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  @Mapping(source = "departmentId",target = "department.id")
    Product toProduct(ProductDto productDto);

    ProductDtoIMPL toProductDtoIMPL(Product product);

}
