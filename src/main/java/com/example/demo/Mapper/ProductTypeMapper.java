package com.example.demo.Mapper;

import com.example.demo.DTO.ProductDto;
import com.example.demo.DTO.ProductDtoIMPL;
import com.example.demo.DTO.ProductTypeDto;
import com.example.demo.DTO.ProductTypeDtoIMPL;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.ProductType;
import com.example.demo.Exception.ProductsNotFoundException;
import com.example.demo.Repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static ch.qos.logback.core.joran.spi.ConsoleTarget.findByName;
import static java.util.stream.Collectors.toList;

@Component
public class ProductTypeMapper {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductTypeMapper(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }


    public ProductType toProductType(ProductTypeDto productTypeDto) {
        ProductType productType = new ProductType();
        productType.setName(productTypeDto.getName());
        if (productTypeDto.getProductsId() != null && !productTypeDto.getProductsId().isEmpty()) {
            List<Product> list = productTypeDto.getProductsId().stream()
                    .map(id -> productRepository.findById(id).orElseThrow(() ->
                            new ProductsNotFoundException("Product not found"))).toList();
            list.forEach(p->p.setProductType(productType));

            productType.setProducts(list);
        }

        return productType;
    }

    public ProductTypeDtoIMPL productTypeDtoIMPL(ProductType productType) {
        ProductTypeDtoIMPL dto = new ProductTypeDtoIMPL();
        dto.setId(productType.getId());
        dto.setName(productType.getName());

        if (productType.getProducts() != null && !productType.getProducts().isEmpty()) {

            List<ProductDtoIMPL> list = productType.getProducts().stream()
                    .map(productMapper::productDtoIMPL)
                    .toList();

            dto.setProducts(list);


        }

        return dto;
    }
    public List<ProductTypeDtoIMPL>dtoIMPLList(List<ProductType>productTypes){
        return productTypes.stream().map(productType -> {
            ProductTypeDtoIMPL dto = new ProductTypeDtoIMPL();
            dto.setId(productType.getId());
            dto.setName(productType.getName());
            if(productType.getProducts() != null && !productType.getProducts().isEmpty()){
                List<ProductDtoIMPL> list = productType.getProducts().stream().
                        map(productMapper::productDtoIMPL).toList();

                dto.setProducts(list);
            }

            return dto;
        }).toList();




    }

}
