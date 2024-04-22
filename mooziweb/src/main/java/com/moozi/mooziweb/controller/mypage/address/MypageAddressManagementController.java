package com.moozi.mooziweb.controller.mypage.address;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.domain.UserAddress;
import com.moozi.mooziweb.user.exception.UserAddressNotFoundException;
import com.moozi.mooziweb.user.repository.impl.UserAddressRepositoryImpl;
import com.moozi.mooziweb.user.service.UserAddressService;
import com.moozi.mooziweb.user.service.impl.UserAddressServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/management_address.do")
public class MypageAddressManagementController implements BaseController {
    private final UserAddressService addressService = new UserAddressServiceImpl(new UserAddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        List<UserAddress> userAddressList = new ArrayList<>();
        try{
            userAddressList = addressService.getUserAddress(user.getUserId());
        } catch (UserAddressNotFoundException e) {
            log.error("error: {}", e.getMessage());
        }

        req.setAttribute("userAddressList", userAddressList);

        return "mypage/management_address";
    }
}
