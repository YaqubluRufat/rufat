package com.example.demo.Service;

import com.example.demo.Dto.ProductDtoIMPL;
import com.example.demo.Dto.ProductsDto;
import com.example.demo.Entity.Market;
import com.example.demo.Entity.Products;
import com.example.demo.Exception.InvalidRole;
import com.example.demo.Exception.InvalidSort;
import com.example.demo.Exception.MarketNotFound;
import com.example.demo.Exception.ProductNotFound;
import com.example.demo.Mapper.ProductMapper;
import com.example.demo.Repository.MarketRepository;
import com.example.demo.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final MarketRepository marketRepository;

    public ProductService(ProductMapper productMapper, ProductRepository productRepository, MarketRepository marketRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.marketRepository = marketRepository;
    }
    @PreAuthorize("hasRole('ADMIN') or @productSecurity.isOwner(#productsDto.marketId,authentication)")
    public ProductDtoIMPL addProduct (ProductsDto productsDto){
        Market market = marketRepository.findById(productsDto.getMarketId()).orElseThrow(() -> new MarketNotFound("Market not found"));
        Products products = productMapper.toProducts(productsDto);
        products.setMarket(market);
        Products save = productRepository.save(products);
        return productMapper.toDtoIMPL(save);

    }
    public ProductDtoIMPL findById(Long id){
        Products products = productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found"));
        return productMapper.toDtoIMPL(products);
    }
    @PreAuthorize("hasRole('ADMIN') or @productSecurity.owner(#id,authentication)")
    public void deleteById(Long id){
        Products products = productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found"));
        productRepository.delete(products);

    }
    @PreAuthorize("hasRole('ADMIN') or @productSecurity.owner(#id,authentication)")
    public ProductDtoIMPL updateProduct(Long id,ProductsDto productsDto){
        Products products = productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found"));
        if(productsDto.getName()!=null){
            products.setName(productsDto.getName());
        }
        if(productsDto.getPrice()!=null){
            products.setPrice(productsDto.getPrice());
        }
        Products save = productRepository.save(products);
        return productMapper.toDtoIMPL(save);
    }

    public List<ProductDtoIMPL> findByName(String name){
        List<Products> byNameContaining = productRepository.findByNameContaining(name);
        return productMapper.toList(byNameContaining);

    }
    public Page<ProductDtoIMPL>getAll(int page ,int size,String sortBy,String sortDir){
        List<String> name = List.of("name", "price");
        if(!name.contains(sortBy)){
            throw new InvalidSort("Sort is invalid");
        }
        List<String> asc = List.of("asc", "desc");
        if(!asc.contains(sortDir)){
            throw new InvalidSort("Incorrect sort");
        }
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageRequest).map(productMapper::toDtoIMPL);

    }
}
