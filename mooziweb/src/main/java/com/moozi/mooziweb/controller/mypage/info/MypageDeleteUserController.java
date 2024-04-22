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
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/deleteUser.do")
public class MypageDeleteUserController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        userService.deleteUser(user.getUserId());

        return "redirect:/logout.do";
    }
}
