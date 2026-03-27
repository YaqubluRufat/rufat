package com.example.demo.Controller;

import com.example.demo.DTO.ProductDto;
import com.example.demo.DTO.ProductDtoIMPL;
import com.example.demo.Service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api3")

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/save")
    public ResponseEntity<ProductDtoIMPL>addProduct(@RequestBody@Valid ProductDto productDto){
        ProductDtoIMPL productDtoIMPL = productService.addProduct(productDto);
        return ResponseEntity.ok().body(productDtoIMPL);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/{id}")
    public ResponseEntity<ProductDtoIMPL>findById(@PathVariable Long id){
        ProductDtoIMPL byId = productService.findById(id);
        return ResponseEntity.ok().body(byId);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.ok().body("Deleted successfully");
    }
    @GetMapping("/list")
    public ResponseEntity<List<ProductDtoIMPL>>getAll(){
        List<ProductDtoIMPL> all = productService.getAll();
        return ResponseEntity.ok().body(all);
    }



}
