package com.example.demo.Service;

import com.example.demo.Dto.ReviewDto;
import com.example.demo.Dto.ReviewDtoIMPL;
import com.example.demo.Entity.Restaurant;
import com.example.demo.Entity.Review;
import com.example.demo.Entity.User;
import com.example.demo.Exception.InvalidRole;
import com.example.demo.Exception.RestaurantNotFound;
import com.example.demo.Exception.RewierNotFound;
import com.example.demo.Mapper.ReviewMapper;
import com.example.demo.Repository.RestaurantRepository;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PreAuthorize("hasRole('USER')")
    public ReviewDtoIMPL addReview(ReviewDto reviewDtoL, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        Restaurant restaurant = restaurantRepository.findById(reviewDtoL.getRestaurantId()).orElseThrow(() ->
                new RestaurantNotFound("Restaurant not found"));
        Review review = reviewMapper.toReview(reviewDtoL);
        review.setUser(user);
        review.setRestaurant(restaurant);
        Review save = reviewRepository.save(review);
        return reviewMapper.toReviewDtoIMPL(save);

    }

    @PreAuthorize("hasRole('USER')")
    public ReviewDtoIMPL findById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RewierNotFound("Review not found"));
        return reviewMapper.toReviewDtoIMPL(review);
    }

    public List<ReviewDtoIMPL> findByRating(Long rating) {
        List<Review> reviews = reviewRepository.findByRating(rating);
        return reviewMapper.DTO_IMPL_LIST(reviews);
    }
    @PreAuthorize("hasRole('USER')")
    public void deleteById(Long id,Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RewierNotFound("Review not found"));
        if(!user.getId().equals(review.getUser().getId())){
            throw new InvalidRole("Its not your role");
        }
        reviewRepository.delete(review);
    }

    @PreAuthorize("hasRole('USER')")
    public ReviewDtoIMPL updateReview(Long id, Authentication authentication, ReviewDto reviewDto) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found exception"));
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RewierNotFound("Review not found"));
        if (!user.getId().equals(review.getUser().getId())) {
            throw new InvalidRole("Its not your role");
        }
        if (reviewDto.getCommentaries() != null) {
            review.setCommentaries(reviewDto.getCommentaries());
        }
        if (reviewDto.getRating() != null) {
            review.setRating(review.getRating());
        }
        Review save = reviewRepository.save(review);
        return reviewMapper.toReviewDtoIMPL(save);

    }
}
