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
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/loginAction.do")
public class LoginPostController implements BaseController {
    private final UserPointInfoService pointInfoService = new UserPointInfoServiceImpl(new UserPointInfoRepositoryImpl());
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.
        String userId = req.getParameter("user_id");
        String userPassword = req.getParameter("user_password");

        try {
            LocalDateTime current = LocalDateTime.now();
            User user = userService.doLogin(userId, userPassword);

            if (Objects.nonNull(user.getLatestLoginAt()) && user.getLatestLoginAt().getDayOfMonth() != current.getDayOfMonth()) {
                pointInfoService.saveUserPointInfo(new UserPointInfo(user.getUserId(), UserPointInfo.PointInfo.SAVE, 10000, LocalDateTime.now()));
            }

            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            if (user.getUserAuth().equals(User.Auth.ROLE_ADMIN)) {
                return "redirect:/admin/index.do";
            } else {
                return "redirect:/";
            }
        } catch (RuntimeException e) {
            log.error("Login error: {}", e.getMessage());
            return "shop/login/login_form";
        }
    }
}
