package com.moozi.shoppingmall.check.review.repository.impl;

import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.review.domain.Review;
import com.moozi.mooziweb.review.repository.ReviewRepository;
import com.moozi.mooziweb.review.repository.impl.ReviewRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ReviewRepositoryImplTest {
    ReviewRepository reviewRepository = new ReviewRepositoryImpl();
    Review testReview;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testReview = Review.builder()
                .reviewId(1)
                .productId(1)
                .userId("user")
                .rating(5)
                .comments("test comments")
                .build();
        reviewRepository.save(testReview);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("find review by product id")
    void findReviewByProductId() {
        Page<Review> reviewPage = reviewRepository.findReviewByProductId(1, 1, 10);

        assertAll(
                () -> assertNotNull(reviewPage),
                () -> assertNotNull(reviewPage.getContent()),
                () -> assertFalse(reviewPage.getContent().isEmpty()),
                () -> assertEquals(1, reviewPage.getContent().size()),
                () -> assertEquals("test comments", reviewPage.getContent().get(0).getComments())
        );
    }

    @Test
    @DisplayName("find review by product id : not found review")
    void findReviewByProductId_not_found_review() {
        Page<Review> reviewPage = reviewRepository.findReviewByProductId(0, 1, 10);

        assertAll(
                () -> assertTrue(reviewPage.getContent().isEmpty())
        );
    }

    @Test
    @DisplayName("save")
    void save() {
        Review review = Review.builder()
                .reviewId(2)
                .productId(1)
                .userId("admin")
                .rating(4)
                .comments("review comment")
                .build();
        int result = reviewRepository.save(review);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(2, reviewRepository.findReviewByProductId(1, 1, 10).getContent().size()),
                () -> assertEquals(review.getComments(), reviewRepository.findReviewByProductId(1, 1, 10).getContent().get(1).getComments())
        );
    }

    @Test
    @DisplayName("update")
    void update() {
        Review review = reviewRepository.findReviewByProductId(1, 1, 10).getContent().get(0);
        review.setComments("new comments");
        int result = reviewRepository.update(review, review.getUserId());

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(review.getComments(), reviewRepository.findReviewByProductId(1, 1, 10).getContent().get(0).getComments())
        );
    }

    @Test
    @DisplayName("update : not allowed user")
    void update_not_allowed_user() {
        Review review = reviewRepository.findReviewByProductId(1, 1, 10).getContent().get(0);
        review.setComments("new comments");
        int result = reviewRepository.update(review, "notAllowedUser");

        assertAll(
                () -> assertEquals(0, result),
                () -> assertEquals(testReview.getComments(), reviewRepository.findReviewByProductId(1, 1, 10).getContent().get(0).getComments())
        );
    }

    @Test
    @DisplayName("deleteById")
    void deleteById() {
        Review review = reviewRepository.findReviewByProductId(1, 1, 10).getContent().get(0);
        int result = reviewRepository.deleteById(review.getReviewId(), review.getUserId());

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(0, reviewRepository.findReviewByProductId(1, 1, 10).getContent().size())
        );
    }

    @Test
    @DisplayName("deleteById : not allowed user")
    void deleteById_not_allowed_user() {
        Review review = reviewRepository.findReviewByProductId(1, 1, 10).getContent().get(0);
        int result = reviewRepository.deleteById(review.getReviewId(), "notAllowedUser");

        assertAll(
                () -> assertEquals(0, result),
                () -> assertEquals(1, reviewRepository.findReviewByProductId(1, 1, 10).getContent().size())
        );
    }

    @Test
    @DisplayName("countByReviewId")
    void countByReviewId() {
        Review review = reviewRepository.findReviewByProductId(1, 1, 10).getContent().get(0);
        int foundReviewCount = reviewRepository.countByReviewId(review.getReviewId());
        int notFoundReviewCount = reviewRepository.countByReviewId(0);

        assertAll(
                () -> assertEquals(1, foundReviewCount),
                () -> assertEquals(0, notFoundReviewCount)
        );
    }
}
