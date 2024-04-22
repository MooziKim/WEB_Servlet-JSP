package com.moozi.mooziweb.review.repository;

import com.moozi.mooziweb.review.domain.Review;
import com.moozi.mooziweb.common.page.Page;

public interface ReviewRepository {
    // 제품별 리뷰 조회, 리뷰 생성, 리뷰 수정, 리뷰 삭제
    Page<Review> findReviewByProductId(int productId, int page, int pageSize);
    int save(Review review);
    int update(Review review, String userId);
    int deleteById(int reviewId, String userId);

    int countByReviewId(int reviewId);
}
