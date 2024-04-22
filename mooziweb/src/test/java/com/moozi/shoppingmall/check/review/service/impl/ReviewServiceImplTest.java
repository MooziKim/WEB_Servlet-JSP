package com.moozi.shoppingmall.check.review.service.impl;

import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.review.domain.Review;
import com.moozi.mooziweb.review.exception.ReviewAlreadyExistException;
import com.moozi.mooziweb.review.exception.ReviewNotFoundException;
import com.moozi.mooziweb.review.repository.ReviewRepository;
import com.moozi.mooziweb.review.service.ReviewService;
import com.moozi.mooziweb.review.service.impl.ReviewServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {
    ReviewRepository reviewRepository = Mockito.mock(ReviewRepository.class);
    ReviewService reviewService = new ReviewServiceImpl(reviewRepository);

    static Review testReview = Review.builder()
            .reviewId(1)
            .productId(1)
            .userId("user")
            .rating(5)
            .comments("review comments")
            .build();

    static List<Review> reviewList = new ArrayList<>();
    static Page<Review> reviewPage;

    @BeforeAll
    static void setUp() {
        reviewList.add(testReview);
        reviewPage = new Page<>(reviewList, 1);
    }

    @Test
    @DisplayName("get review by product id")
    void getReviewByProductId() {
        Mockito.when(reviewRepository.findReviewByProductId(anyInt(), anyInt(), anyInt())).thenReturn(reviewPage);
        reviewService.getReviewByProductId(1, 1, 10);
        Mockito.verify(reviewRepository, Mockito.times(1)).findReviewByProductId(anyInt(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("get review by product id : review not found")
    void getReviewByProductId_review_not_found() {
        Mockito.when(reviewRepository.findReviewByProductId(anyInt(), anyInt(), anyInt())).thenReturn(null);
        Throwable throwable = assertThrows(ReviewNotFoundException.class, () -> reviewService.getReviewByProductId(0, 1, 10));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("save review")
    void saveReview() {
        Mockito.when(reviewRepository.save(any())).thenReturn(1);
        reviewService.saveReview(testReview);
        Mockito.verify(reviewRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("save review : review already exist")
    void saveReview_review_already_exist() {
        Mockito.when(reviewRepository.save(any())).thenReturn(0);
        Throwable throwable = assertThrows(ReviewAlreadyExistException.class, () -> reviewService.saveReview(testReview));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("update review")
    void updateReview() {
        Mockito.when(reviewRepository.countByReviewId(anyInt())).thenReturn(1);
        Mockito.when(reviewRepository.update(any(), anyString())).thenReturn(1);
        reviewService.updateReview(testReview, testReview.getUserId());
        Mockito.verify(reviewRepository, Mockito.times(1)).countByReviewId(anyInt());
        Mockito.verify(reviewRepository, Mockito.times(1)).update(any(), anyString());
    }

    @Test
    @DisplayName("update review : review not found")
    void updateReview_review_not_found() {
        Mockito.when(reviewRepository.countByReviewId(anyInt())).thenReturn(0);
        Throwable throwable = assertThrows(ReviewNotFoundException.class, () -> reviewService.updateReview(testReview, testReview.getUserId()));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("delete review by id")
    void deleteReviewById() {
        Mockito.when(reviewRepository.countByReviewId(anyInt())).thenReturn(1);
        Mockito.when(reviewRepository.deleteById(anyInt(), anyString())).thenReturn(1);
        reviewService.deleteReviewById(testReview.getReviewId(), testReview.getUserId());
        Mockito.verify(reviewRepository, Mockito.times(1)).countByReviewId(anyInt());
        Mockito.verify(reviewRepository, Mockito.times(1)).deleteById(anyInt(), anyString());
    }

    @Test
    @DisplayName("delete review by id : review not found")
    void deleteReviewById_review_not_found() {
        Mockito.when(reviewRepository.countByReviewId(anyInt())).thenReturn(0);
        Throwable throwable = assertThrows(ReviewNotFoundException.class, () -> reviewService.deleteReviewById(testReview.getReviewId(), testReview.getUserId()));
        log.debug("error: {}", throwable.getMessage());
    }
}
