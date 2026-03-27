package com.example.demo.Service;

import com.example.demo.DTO.ProductTypeDto;
import com.example.demo.DTO.ProductTypeDtoIMPL;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.ProductType;
import com.example.demo.Exception.ProductTypeAlreadyExistsException;
import com.example.demo.Exception.ProductTypeNotFoundException;
import com.example.demo.Exception.ProductsNotFoundException;
import com.example.demo.Mapper.ProductTypeMapper;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.ProductTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeMapper productTypeMapper;
    private final ProductRepository productRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository, ProductTypeMapper productTypeMapper, ProductRepository productRepository) {
        this.productTypeRepository = productTypeRepository;
        this.productTypeMapper = productTypeMapper;
        this.productRepository = productRepository;
    }

    public ProductTypeDtoIMPL addProductType(ProductTypeDto productTypeDto) {
        if (productTypeRepository.findByName(productTypeDto.getName()).isPresent()) {
            throw new ProductTypeAlreadyExistsException("This productType already exists");

        } else {
            ProductType productType = productTypeMapper.toProductType(productTypeDto);
            ProductType save = productTypeRepository.save(productType);
            return productTypeMapper.productTypeDtoIMPL(save);
        }
    }

    public ProductTypeDtoIMPL findById(Long id) {
        ProductType productType = productTypeRepository.findById(id).orElseThrow(() ->
                new ProductTypeNotFoundException("The productType was not found"));
        return productTypeMapper.productTypeDtoIMPL(productType);
    }

    public ProductTypeDtoIMPL findByName(String name) {
        ProductType productType = productTypeRepository.findByName(name).orElseThrow(() ->
                new ProductTypeNotFoundException("The productType was not found"));
        return productTypeMapper.productTypeDtoIMPL(productType);

    }

    public void deleteProductType(Long id) {
        productTypeRepository.deleteById(id);
    }

    public ProductTypeDtoIMPL updatedProductType(Long id, ProductTypeDto productTypeDto) {
        ProductType productType = productTypeRepository.findById(id).orElseThrow(() ->
                new ProductTypeNotFoundException("The productType was not found"));
        productType.setName(productTypeDto.getName());


        ProductType save = productTypeRepository.save(productType);
        return productTypeMapper.productTypeDtoIMPL(save);

    }
    public List<ProductTypeDtoIMPL>getAll(){
        List<ProductType> all = productTypeRepository.findAll();
        return productTypeMapper.dtoIMPLList(all);
    }
}

