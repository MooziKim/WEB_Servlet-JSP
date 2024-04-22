package com.moozi.mooziweb.controller.mypage.point;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.domain.UserPointInfo;
import com.moozi.mooziweb.user.repository.impl.UserPointInfoRepositoryImpl;
import com.moozi.mooziweb.user.service.UserPointInfoService;
import com.moozi.mooziweb.user.service.impl.UserPointInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/pointInfo.do")
public class PointInfoController implements BaseController {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private final UserPointInfoService pointInfoService = new UserPointInfoServiceImpl(new UserPointInfoRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();

        int page = getPageParameterOrDefault(req);

        int pageSize = 10;
        Page<UserPointInfo> pointInfoPage = pointInfoService.getUserPointInfo(userId, page, pageSize);
        List<UserPointInfo> pointInfoList = pointInfoPage.getContent();
        long pageCount = calculatePageCount(pointInfoPage.getTotalCount());

        req.setAttribute("pointInfoList", pointInfoList);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("currentPage", page);

        return "mypage/point_info";
    }

    private int getPageParameterOrDefault(HttpServletRequest req) {
        return Objects.nonNull(req.getParameter("page")) ? Integer.parseInt(req.getParameter("page")) : DEFAULT_PAGE;
    }

    private long calculatePageCount(long totalCount) {
        return (long) Math.ceil((double) totalCount / DEFAULT_PAGE_SIZE);
    }
}
