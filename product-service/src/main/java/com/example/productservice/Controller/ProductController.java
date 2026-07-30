package com.example.productservice.Controller;

import com.example.productservice.DTO.ProductDto;
import com.example.productservice.DTO.ProductDtoIMPL;
import com.example.productservice.Entity.Product;
import com.example.productservice.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/add")
    public ResponseEntity<ProductDtoIMPL>addProduct(@RequestBody ProductDto productDto){
        ProductDtoIMPL productDtoIMPL = productService.addProduct(productDto);
        return ResponseEntity.ok().body(productDtoIMPL);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<ProductDtoIMPL>findById(@PathVariable Long id){
        ProductDtoIMPL byId = productService.findById(id);
        return ResponseEntity.ok().body(byId);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteById(@PathVariable Long id){
        productService.deleteBtId(id);
        return ResponseEntity.ok().body("Deleted");
    }
    @PutMapping("/updates/{id}")
    public ResponseEntity<ProductDtoIMPL>updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
        ProductDtoIMPL productDtoIMPL = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(productDtoIMPL);
    }
    @GetMapping("/list/{id}")
    public ResponseEntity<List<ProductDtoIMPL>> findByMarketId(@PathVariable Long id){
        List<ProductDtoIMPL> list = productService.findByMarketId(id);
        return ResponseEntity.ok(list);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateCount(@PathVariable Long id){
        productService.updateCount(id);
        return ResponseEntity.ok("Updated");
    }
}
