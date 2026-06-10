package com.example.demo.Service;

import com.example.demo.Dto.ProductDto;
import com.example.demo.Dto.ProductDtoIMPL;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.Product;
import com.example.demo.Exception.DepartmentNotFound;
import com.example.demo.Exception.InvalidSort;
import com.example.demo.Exception.ProductNotFound;
import com.example.demo.Mapper.ProductMapper;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.ProductRepository;

import com.example.demo.ServiceKafka.Product.ProductDeletedEvent;
import com.example.demo.ServiceKafka.Product.ProductProducerService;
import com.example.demo.ServiceKafka.Product.ProductSavedEvent;
import com.example.demo.ServiceKafka.Product.ProductUpdatedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final DepartmentRepository departmentRepository;
    private final ProductProducerService productProducerService;


    public ProductService(ProductMapper productMapper, ProductRepository productRepository,
                          DepartmentRepository departmentRepository, ProductProducerService productProducerService) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.departmentRepository = departmentRepository;


        this.productProducerService = productProducerService;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @departmentSecurity.isOWNER(#productDto.departmentId,authentication)")
    public ProductDtoIMPL addProduct(ProductDto productDto) {
        Department department = departmentRepository.findById(productDto.getDepartmentId()).orElseThrow(() ->
                new DepartmentNotFound("Department not found"));
        Product product = productMapper.toProduct(productDto);
        product.setDepartment(department);
        Product save = productRepository.save(product);
        ProductSavedEvent productSavedEvent = new ProductSavedEvent(save.getId(),save.getName(),save.getPrice(), LocalDateTime.now());
        productProducerService.onPublishedSavedProduct(productSavedEvent);
        return productMapper.toProductDtoIMPL(save);

    }

    @Transactional(readOnly = true)
    public ProductDtoIMPL findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found"));
        return productMapper.toProductDtoIMPL(product);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @productSecurity.isUser(#id,authentication)")
    public void deleteById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found"));
        ProductDeletedEvent productDeletedEvent =new ProductDeletedEvent(product.getId(),product.getName(),product.getPrice(),
                LocalDateTime.now());
        productProducerService.onPublishDeleteProduct(productDeletedEvent);
        productRepository.delete(product);

    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @productSecurity.isUser(#id,authentication) and @departmentSecurity.isOWNER(#productDto.departmentId,authentication)")
    public ProductDtoIMPL updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found"));
        if (productDto.getDepartmentId() != null) {
            Department department = departmentRepository.findDepartmentById(productDto.getDepartmentId()).orElseThrow(() ->
                    new DepartmentNotFound("Department not found"));
            product.setDepartment(department);
        }
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        ProductUpdatedEvent productUpdatedEvent = new ProductUpdatedEvent(product.getId(),product.getName(),product.getPrice(),
                LocalDateTime.now());
        productProducerService.onPublishUpdateProduct(productUpdatedEvent);
        return productMapper.toProductDtoIMPL(product);

    }

    public Page<ProductDtoIMPL> getAll(int page, int size, String sortBy, String sortDir) {
        List<String> strings = List.of("id", "name", "price");
        if (!strings.contains(sortBy)) {
            throw new InvalidSort("Sort is invalid");
        }
        List<String> asc = List.of("asc", "desc");
        if (!asc.contains(sortDir)) {
            throw new InvalidSort("Incorrect sort");
        }
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageRequest).map(productMapper::toProductDtoIMPL);

    }
}
