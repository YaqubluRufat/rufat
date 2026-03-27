package com.example.demo.Service;

import com.example.demo.DTO.UserProductDto;
import com.example.demo.DTO.UserProductDtoIMPL;
import com.example.demo.Entity.UserProduct;
import com.example.demo.Mapper.UserProductMapper;
import com.example.demo.Repository.UserProductRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProductService {
    private final UserProductMapper userProductMapper;
    private final UserProductRepository userProductRepository;

    public UserProductService(UserProductMapper userProductMapper, UserProductRepository userProductRepository) {
        this.userProductMapper = userProductMapper;
        this.userProductRepository = userProductRepository;
    }
    public UserProductDtoIMPL addUserProduct (UserProductDto userProductDto){
        UserProduct userProduct = userProductMapper.toUserProduct(userProductDto);
        UserProduct save = userProductRepository.save(userProduct);
        return userProductMapper.userProductDtoIMPL(save);

    }

}
