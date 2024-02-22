package com.example.reviewservice.service;

import com.example.reviewservice.model.Review;
import com.example.reviewservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductServiceRestClient restClient;


    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ProductServiceRestClient restClient) {
        this.reviewRepository = reviewRepository;
        this.restClient = restClient;
    }

    public void createReview(Review review) {
        Long productId = review.getProductId();
        System.out.println(productId);
        String productName = restClient.getProductTitleById(productId);
        System.out.println(productName);
        review.setProductName(productName);
        reviewRepository.save(review);
    }

    public List<Review> getAllReviewsByProductId(Long productId) {
        return reviewRepository.findAllByProductId(productId);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
    public String getAllReviewsWithProductNames() {
        List<Review> reviews = reviewRepository.findAll();
        StringBuilder result = new StringBuilder();
        for (Review review : reviews) {
            result.append("Review for product ").append(restClient.getProductTitleById(review.getProductId())).append(":\n")
                    .append("Rating: ").append(review.getRating()).append("\n")
                    .append("Review: ").append(review.getDescription()).append("\n\n");
        }
        return result.toString();
    }

}
