package com.example.demo.Service;

import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ProductSecurity {

    private final ProductRepository productRepository;

    public ProductSecurity (ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    public boolean isUser(Long id,Authentication authentication){
        if(! (authentication.getPrincipal() instanceof MyUserDetails user)){
            return false;
        }
       return productRepository.existsByIdAndDepartmentMarketUserId(id,user.getId());
    }
}
