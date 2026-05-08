package com.example.demo.Controller;

import com.example.demo.Dto.ReviewDto;
import com.example.demo.Dto.ReviewDtoIMPL;
import com.example.demo.Service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping("/save")
    public ResponseEntity<ReviewDtoIMPL> addReview(ReviewDto reviewDto, Authentication authentication){
        ReviewDtoIMPL reviewDtoIMPL = reviewService.addReview(reviewDto, authentication);
        return ResponseEntity.ok(reviewDtoIMPL);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<ReviewDtoIMPL>findById(Long id){
        ReviewDtoIMPL byId = reviewService.findById(id);
        return ResponseEntity.ok(byId);
    }
}
