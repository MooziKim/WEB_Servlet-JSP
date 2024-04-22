package com.moozi.mooziweb.controller.auth;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.domain.UserPointInfo;
import com.moozi.mooziweb.user.repository.impl.UserPointInfoRepositoryImpl;
import com.moozi.mooziweb.user.repository.impl.UserRepositoryImpl;
import com.moozi.mooziweb.user.service.UserPointInfoService;
import com.moozi.mooziweb.user.service.UserService;
import com.moozi.mooziweb.user.service.impl.UserPointInfoServiceImpl;
import com.moozi.mooziweb.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/signupAction.do")
public class SignupPostController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final UserPointInfoService pointInfoService = new UserPointInfoServiceImpl(new UserPointInfoRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("user_id");
        String userPassword = req.getParameter("user_password");
        String userPasswordCheck = req.getParameter("user_password_check");
        String userName = req.getParameter("user_name");
        String userBirth = req.getParameter("user_birth");
        LocalDateTime createdAt = LocalDateTime.now();

        try {
            if (!userPassword.equals(userPasswordCheck)) {
                log.error("Password and password check do not match.");
                return "shop/signup/signup_form";
            }

            User user = new User(userId, userName, userPassword, userBirth, User.Auth.ROLE_USER, createdAt, null);
            userService.saveUser(user);

            UserPointInfo userPointInfo = new UserPointInfo(userId, UserPointInfo.PointInfo.SAVE, 1000000, LocalDateTime.now());
            pointInfoService.saveUserPointInfo(userPointInfo);
        } catch (Exception e) {
            log.error("Sign up error: {}", e.getMessage());
            return "shop/signup/signup_form";
        }
        return "redirect:/login.do";
    }
}
