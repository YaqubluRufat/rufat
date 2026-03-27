package com.example.demo.Controller;

import com.example.demo.DTO.ProductTypeDto;
import com.example.demo.DTO.ProductTypeDtoIMPL;
import com.example.demo.Service.ProductTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api2")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }
    @PostMapping("/save")
    public ResponseEntity<ProductTypeDtoIMPL>addProductType(@RequestBody @Valid ProductTypeDto productTypeDto){
        ProductTypeDtoIMPL productTypeDtoIMPL = productTypeService.addProductType(productTypeDto);
        return ResponseEntity.ok().body(productTypeDtoIMPL);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<ProductTypeDtoIMPL>findById(@PathVariable Long id){
        ProductTypeDtoIMPL byId = productTypeService.findById(id);
        return ResponseEntity.ok().body(byId);
    }
    @GetMapping("/findByName")
    public ResponseEntity<ProductTypeDtoIMPL>findByName(@RequestParam String name){
        ProductTypeDtoIMPL byName = productTypeService.findByName(name);
        return ResponseEntity.ok().body(byName);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductType(@PathVariable Long id){
        productTypeService.deleteProductType(id);
        return ResponseEntity.ok().body("Deleted successfully");

    }
    @PutMapping("/updated/{id}")
    public ResponseEntity<ProductTypeDtoIMPL>updated(@PathVariable Long id,@RequestBody ProductTypeDto productTypeDto){
        ProductTypeDtoIMPL productTypeDtoIMPL = productTypeService.updatedProductType(id, productTypeDto);
        return ResponseEntity.ok().body(productTypeDtoIMPL);
    }
    @GetMapping("/list")
    public ResponseEntity<List<ProductTypeDtoIMPL>>getAll(){
        List<ProductTypeDtoIMPL> all = productTypeService.getAll();
        return ResponseEntity.ok().body(all);

    }

}
