package com.example.demo.Service;

import com.example.demo.DTO.ProductDto;
import com.example.demo.DTO.ProductDtoIMPL;
import com.example.demo.Entity.Product;
import com.example.demo.Exception.ProductAlreadyExistsException;
import com.example.demo.Exception.ProductsNotFoundException;
import com.example.demo.Mapper.ProductMapper;
import com.example.demo.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }
    public ProductDtoIMPL addProduct(ProductDto productDto){


        if(productRepository.findByName(productDto.getName()).isPresent()){
            throw  new ProductAlreadyExistsException("This product already exists");
        }else {
            Product product = productMapper.toProduct(productDto);
            Product save = productRepository.save(product);
            return productMapper.productDtoIMPL(save);
        }
    }
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public ProductDtoIMPL findById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductsNotFoundException("Product not found "));
        return productMapper.productDtoIMPL(product);

    }
    public void deleteById(Long id){

        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductsNotFoundException("Product not found"));
         productRepository.delete(product);
    }
    public List<ProductDtoIMPL>getAll(){
        List<Product> all = productRepository.findAll();
        return productMapper.toProductDtoIMPLS(all);
    }


}
