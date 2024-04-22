package com.moozi.mooziweb.controller.mypage.info;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.repository.impl.UserRepositoryImpl;
import com.moozi.mooziweb.user.service.UserService;
import com.moozi.mooziweb.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/changePasswordAction.do")
public class MypageChangePasswordFormController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userPassword = req.getParameter("userPassword");
        String userPasswordCheck = req.getParameter("userPasswordCheck");

        if (!userPasswordCheck.equals(userPassword)) {
            return "mypage/change_password";
        }

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        user.setUserPassword(userPassword);

        userService.updateUser(user);

        return "redirect:/mypage/index.do";
    }
}
