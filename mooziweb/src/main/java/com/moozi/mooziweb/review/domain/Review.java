package com.moozi.mooziweb.review.domain;

import lombok.Builder;

import java.util.Objects;

@Builder
public class Review {
    private int reviewId;
    private int productId;
    private String userId;
    private int rating;
    private String comments;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return reviewId == review.reviewId && productId == review.productId && rating == review.rating && Objects.equals(userId, review.userId) && Objects.equals(comments, review.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, productId, userId, rating, comments);
    }
}
