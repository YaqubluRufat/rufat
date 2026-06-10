package com.example.demo.Service;

import com.example.demo.Dto.ProductDto;
import com.example.demo.Dto.ProductDtoIMPL;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.Product;
import com.example.demo.Mapper.ProductMapper;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.ServiceKafka.Product.ProductDeletedEvent;
import com.example.demo.ServiceKafka.Product.ProductProducerService;
import com.example.demo.ServiceKafka.Product.ProductSavedEvent;
import com.example.demo.ServiceKafka.Product.ProductUpdatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductMapper productMapper;
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductService productService;
    @Mock
    ProductProducerService productProducerService;
    @Mock
    DepartmentRepository departmentRepository;

    @Test
    void addProduct() {
        Product product = new Product();

        Department department = new Department();
        department.setId(1L);

        ProductDto productDto = new ProductDto();
        productDto.setDepartmentId(1L);

        Product savedProduct=new Product();

        ProductDtoIMPL productDtoIMPL = new ProductDtoIMPL();

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(productMapper.toProduct(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        doNothing().when(productProducerService).onPublishedSavedProduct(any(ProductSavedEvent.class));
        when(productMapper.toProductDtoIMPL(savedProduct)).thenReturn(productDtoIMPL);

        ProductDtoIMPL productDtoIMPL1 = productService.addProduct(productDto);

        assertEquals(productDtoIMPL1,productDtoIMPL);

        verify(departmentRepository).findById(1L);
        verify(productMapper).toProduct(productDto);

    }

    @Test
    void findById() {
        Product product = new Product();
        product.setId(1L);

        ProductDtoIMPL productDtoIMPL = new ProductDtoIMPL();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toProductDtoIMPL(product)).thenReturn(productDtoIMPL);

        ProductDtoIMPL byId = productService.findById(1L);

        assertEquals(byId,productDtoIMPL);

        verify(productRepository).findById(1L);
        verify(productMapper).toProductDtoIMPL(product);
    }

    @Test
    void deleteById() {
        Product product = new Product();
        product.setId(1L);



        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productProducerService).onPublishDeleteProduct(any(ProductDeletedEvent.class));


        productService.deleteById(1L);

        verify(productRepository).findById(1L);
    }

    @Test
    void updateProduct() {
        Product product = new Product();
        product.setId(1L);

        Department department = new Department();
        department.setId(1L);

        ProductDto productDto = new ProductDto();
        productDto.setDepartmentId(1L);

        ProductDtoIMPL productDtoIMPL = new ProductDtoIMPL();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(departmentRepository.findDepartmentById(1L)).thenReturn(Optional.of(department));
        doNothing().when(productProducerService).onPublishUpdateProduct(any(ProductUpdatedEvent.class));
        when(productMapper.toProductDtoIMPL(product)).thenReturn(productDtoIMPL);

        ProductDtoIMPL productDtoIMPL1 = productService.updateProduct(1L, productDto);

        assertEquals(productDtoIMPL1,productDtoIMPL);

        verify(productRepository).findById(1L);
        verify(departmentRepository).findDepartmentById(1L);

    }
}