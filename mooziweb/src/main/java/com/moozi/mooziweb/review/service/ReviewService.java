package com.moozi.mooziweb.review.service;

import com.moozi.mooziweb.review.domain.Review;
import com.moozi.mooziweb.common.page.Page;

public interface ReviewService {
    // 제품별 리뷰 가져오기, 리뷰 추가, 리뷰 수정, 리뷰 삭제
    Page<Review> getReviewByProductId(int productId, int page, int pageSize);
    void saveReview(Review review);
    void updateReview(Review review, String userId);
    void deleteReviewById(int reviewId, String userId);
}
