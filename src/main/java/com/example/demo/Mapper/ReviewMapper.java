package com.example.demo.Mapper;

import com.example.demo.Dto.ReviewDto;
import com.example.demo.Dto.ReviewDtoIMPL;
import com.example.demo.Entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {RestaurantMapper.class})
public interface ReviewMapper {
    @Mapping(source = "restaurantId",target = "restaurant.id")
    Review toReview(ReviewDto reviewDto);

    ReviewDtoIMPL toReviewDtoIMPL(Review review);

    List<ReviewDtoIMPL>DTO_IMPL_LIST(List<Review>reviews);
}
