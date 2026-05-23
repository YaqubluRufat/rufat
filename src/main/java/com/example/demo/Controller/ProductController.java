package com.example.demo.Controller;

import com.example.demo.Dto.MarketDtoIMPL;
import com.example.demo.Dto.ProductDtoIMPL;
import com.example.demo.Dto.ProductsDto;
import com.example.demo.Service.ProductService;
import org.springframework.data.domain.Page;
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

    @PostMapping("/save")
    public ResponseEntity<ProductDtoIMPL> addProduct(@RequestBody ProductsDto productsDto) {
        ProductDtoIMPL productDtoIMPL = productService.addProduct(productsDto);
        return ResponseEntity.ok(productDtoIMPL);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductDtoIMPL> findById(@PathVariable Long id) {
        ProductDtoIMPL byId = productService.findById(id);
        return ResponseEntity.ok(byId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBYId(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Deleted");

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDtoIMPL> updateProduct(@PathVariable Long id, @RequestBody ProductsDto productsDto) {
        ProductDtoIMPL productDtoIMPL = productService.updateProduct(id, productsDto);
        return ResponseEntity.ok(productDtoIMPL);
    }

    @GetMapping("/find")
    public ResponseEntity<List<ProductDtoIMPL>> findByName(@RequestParam String name) {
        List<ProductDtoIMPL> byName = productService.findByName(name);
        return ResponseEntity.ok(byName);
    }
    @GetMapping("/list")
    public ResponseEntity<Page<ProductDtoIMPL>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Page<ProductDtoIMPL> all = productService.getAll(page, size, sortBy, sortDir);
        return ResponseEntity.ok(all);
    }

}
