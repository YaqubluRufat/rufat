package com.example.productservice.Service;

import com.example.productservice.DTO.MarketDto;
import com.example.productservice.DTO.ProductDto;
import com.example.productservice.DTO.ProductDtoIMPL;
import com.example.productservice.Entity.Product;
import com.example.productservice.Exception.MarketNotFound;
import com.example.productservice.Exception.ProductNotFound;
import com.example.productservice.Mapper.ProductMapper;
import com.example.productservice.Repository.ProductRepository;

import feign.FeignException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final MarketClient marketClient;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, MarketClient marketClient) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.marketClient = marketClient;
    }

    @PreAuthorize("hasRole('ADMIN') or @productSecurity.isOwner(#productDto.marketId,authentication)")
    public ProductDtoIMPL addProduct(ProductDto productDto) {

        Product product = productMapper.toProduct(productDto);
        MarketDto byId;

        try {
            byId = marketClient.findById(productDto.getMarketId());
            product.setMarketId(byId.getId());
        } catch (FeignException.NotFound ex) {
            throw new MarketNotFound("Market not found");
        }
        Product save = productRepository.save(product);

        return productMapper.toProductDtoIMPL(save);


    }

    public ProductDtoIMPL findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found"));
        return productMapper.toProductDtoIMPL(product);

    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @productSecurity.isUser(#id,authentication)")
    public void deleteBtId(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @productSecurity.isUser(#id,authentication)")
    public ProductDtoIMPL updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found"));
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getProductCount() != null) {
            product.setProductCount(productDto.getProductCount());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        Product save = productRepository.save(product);
        return productMapper.toProductDtoIMPL(save);
    }

    public List<ProductDtoIMPL> findByMarketId(Long id) {
        List<ProductDtoIMPL> list = productRepository.findByMarketId(id).stream().
                map(productMapper::toProductDtoIMPL).toList();
        return list;
    }

    @Transactional
    public void updateCount(Long id) {
        int count = productRepository.updateCount(id);
        if (count == 0) {
            throw new RuntimeException("Count under 0");

        }
    }

}
