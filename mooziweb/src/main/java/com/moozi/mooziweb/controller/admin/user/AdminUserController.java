package com.moozi.mooziweb.controller.admin.user;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.repository.impl.UserRepositoryImpl;
import com.moozi.mooziweb.user.service.UserService;
import com.moozi.mooziweb.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/user_processing.do")
public class AdminUserController implements BaseController {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int adminPage = getPageParameterOrDefault(req, "adminPage");
        int rolePage = getPageParameterOrDefault(req, "rolePage");

        Page<User> adminUserPage = userService.getUserByUserAuth(User.Auth.ROLE_ADMIN.name(), adminPage, DEFAULT_PAGE_SIZE);
        List<User> adminUserList = adminUserPage.getContent();
        long adminPageCount = calculatePageCount(adminUserList.size());

        req.setAttribute("adminUserList", adminUserList);
        req.setAttribute("adminPageCount", adminPageCount);
        req.setAttribute("adminCurrentPage", adminPage);

        Page<User> roleUserPage = userService.getUserByUserAuth(User.Auth.ROLE_USER.name(), rolePage, DEFAULT_PAGE_SIZE);
        List<User> roleUserList = roleUserPage.getContent();
        long rolePageCount = calculatePageCount(roleUserList.size());

        req.setAttribute("roleUserList", roleUserList);
        req.setAttribute("rolePageCount", rolePageCount);
        req.setAttribute("roleCurrentPage", rolePage);

        return "admin/user/user_processing";
    }

    private int getPageParameterOrDefault(HttpServletRequest req, String page) {
        return Objects.nonNull(req.getParameter(page)) ? Integer.parseInt(req.getParameter(page)) : DEFAULT_PAGE;
    }

    private long calculatePageCount(long totalCount) {
        return (long) Math.ceil((double) totalCount / DEFAULT_PAGE_SIZE);
    }
}
