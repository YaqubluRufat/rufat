package com.example.demo.Mapper;

import com.example.demo.DTO.*;
import com.example.demo.Entity.ProductType;
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserProduct;
import com.example.demo.Entity.UserProfile;
import com.example.demo.Exception.ProductTypeNotFoundException;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Repository.ProductTypeRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProductMapper {
    private final UserRepository userRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductMapper productMapper;

    public UserProductMapper(UserRepository userRepository, ProductTypeRepository productTypeRepository,
                             ProductTypeMapper productTypeMapper, ProductMapper productMapper) {
        this.userRepository = userRepository;
        this.productTypeRepository = productTypeRepository;
        this.productMapper = productMapper;

    }

    public UserProduct toUserProduct(UserProductDto userProductDto) {
        UserProduct userProduct = new UserProduct();
        if (userProductDto.getUserId() != null) {
            User user = userRepository.findById(userProductDto.getUserId()).orElseThrow(() ->
                    new UserNotFoundException("User not found"));
            if (userProductDto.getProductTypeId() != null) {
                ProductType productType = productTypeRepository.findById(userProductDto.getProductTypeId()).orElseThrow(() ->
                        new ProductTypeNotFoundException("Product type not found exception"));
                userProduct.setUser(user);
                userProduct.setProductType(productType);
            }
        }

        return userProduct;

    }

    public UserProductDtoIMPL userProductDtoIMPL(UserProduct userProduct) {
        UserProductDtoIMPL dto = new UserProductDtoIMPL();
        if (userProduct.getUser() != null) {
            User user = userProduct.getUser();
            UserDtoIMPL userDtoIMPL = new UserDtoIMPL();
            userDtoIMPL.setId(user.getId());
            userDtoIMPL.setName(user.getName());

            if (user.getUserProfile() != null) {
                UserProfile userProfile = user.getUserProfile();
                UserProfileDtoIMPL userProfileDtoIMPL = new UserProfileDtoIMPL();
                userProfileDtoIMPL.setUserProfileId(userProfile.getId());
                userProfileDtoIMPL.setName(userProfile.getName());
                userProfileDtoIMPL.setUsername(userProfile.getUsername());
                userProfileDtoIMPL.setEmail(userProfile.getEmail());
                userProfileDtoIMPL.setActive(userProfile.isActive());


                userDtoIMPL.setUserProfile(userProfileDtoIMPL);
            }
            dto.setUser(userDtoIMPL);
        }
        if (userProduct.getProductType() != null) {
            ProductTypeDtoIMPL productTypeDtoIMPL = new ProductTypeDtoIMPL();
            productTypeDtoIMPL.setName(userProduct.getProductType().getName());
            if (userProduct.getProductType().getProducts() != null) {
                List<ProductDtoIMPL> list =
                        userProduct.getProductType().getProducts().stream()
                                .map(productMapper::productDtoIMPL).toList();
                productTypeDtoIMPL.setProducts(list);
            }
            dto.setProductType(productTypeDtoIMPL);
        }
        dto.setId(userProduct.getId());


        return dto;


    }


}


