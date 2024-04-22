package com.moozi.mooziweb.common.filter;

import com.moozi.mooziweb.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/mypage/*")
public class LoginCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.
        HttpSession session = req.getSession(false);
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("user"))) {
            res.sendError(401, "401 Unauthorized");
        }

        User user = (User) session.getAttribute("user");
        if (!user.getUserAuth().equals(User.Auth.ROLE_USER)) {
            res.sendError(403, "403 Forbidden");
        }

        chain.doFilter(req, res);
    }
}