package com.example.demo.Controller;

import com.example.demo.Dto.ProductDto;
import com.example.demo.Dto.ProductDtoIMPL;
import com.example.demo.Entity.Product;
import com.example.demo.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/save")
    public ResponseEntity<ProductDtoIMPL>addProduct(@RequestBody ProductDto productDto){
        ProductDtoIMPL productDtoIMPL = productService.addProduct(productDto);
        return ResponseEntity.ok(productDtoIMPL);
    }
}
