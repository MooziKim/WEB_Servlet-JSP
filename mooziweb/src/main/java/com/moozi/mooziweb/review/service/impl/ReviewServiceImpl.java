package com.moozi.mooziweb.review.service.impl;

import com.moozi.mooziweb.review.domain.Review;
import com.moozi.mooziweb.review.exception.ReviewAlreadyExistException;
import com.moozi.mooziweb.review.exception.ReviewDeleteException;
import com.moozi.mooziweb.review.exception.ReviewNotFoundException;
import com.moozi.mooziweb.review.exception.ReviewUpdateException;
import com.moozi.mooziweb.review.repository.ReviewRepository;
import com.moozi.mooziweb.review.service.ReviewService;
import com.moozi.mooziweb.common.page.Page;

import java.util.Objects;

public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    @Override
    public Page<Review> getReviewByProductId(int productId, int page, int pageSize) {
        Page<Review> reviewPage = reviewRepository.findReviewByProductId(productId, page, pageSize);

        if (Objects.isNull(reviewPage) || Objects.isNull(reviewPage.getContent()) || reviewPage.getContent().isEmpty()) {
            throw new ReviewNotFoundException(String.valueOf(productId));
        }

        return reviewPage;
    }

    @Override
    public void saveReview(Review review) {
        int result = reviewRepository.save(review);

        if (result < 1) {
            throw new ReviewAlreadyExistException(String.valueOf(review.getReviewId()));
        }
    }

    @Override
    public void updateReview(Review review, String userId) {
        if (reviewRepository.countByReviewId(review.getReviewId()) < 1) {
            throw new ReviewNotFoundException(String.valueOf(review.getReviewId()));
        }

        int result = reviewRepository.update(review, userId);

        if (result < 1) {
            throw new ReviewUpdateException(String.valueOf(review.getReviewId()));
        }
    }

    @Override
    public void deleteReviewById(int reviewId, String userId) {
        if (reviewRepository.countByReviewId(reviewId) < 1) {
            throw new ReviewNotFoundException(String.valueOf(reviewId));
        }

        int result = reviewRepository.deleteById(reviewId, userId);

        if (result < 1) {
            throw new ReviewDeleteException(String.valueOf(reviewId));
        }
    }
}
