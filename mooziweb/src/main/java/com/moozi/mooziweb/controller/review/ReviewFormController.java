package com.moozi.mooziweb.controller.review;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.review.domain.Review;
import com.moozi.mooziweb.review.repository.impl.ReviewRepositoryImpl;
import com.moozi.mooziweb.review.service.ReviewService;
import com.moozi.mooziweb.review.service.impl.ReviewServiceImpl;
import com.moozi.mooziweb.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/review/submitReview.do")
public class ReviewFormController implements BaseController {
    private final ReviewService reviewService = new ReviewServiceImpl(new ReviewRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("user"))) {
            return "redirect:/login.do";
        }

        int productId = Integer.parseInt(req.getParameter("productId"));
        String userId = ((User) session.getAttribute("user")).getUserId();
        int rating = Integer.parseInt(req.getParameter("rating"));
        String comments = req.getParameter("comments");

        Review review = Review.builder()
                .productId(productId)
                .userId(userId)
                .rating(rating)
                .comments(comments)
                .build();
        reviewService.saveReview(review);

        return "redirect:/product/detail.do?productId=" + productId;
    }
}
