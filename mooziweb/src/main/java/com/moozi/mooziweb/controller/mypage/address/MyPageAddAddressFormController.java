package com.moozi.mooziweb.controller.mypage.address;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.domain.UserAddress;
import com.moozi.mooziweb.user.repository.impl.UserAddressRepositoryImpl;
import com.moozi.mooziweb.user.service.UserAddressService;
import com.moozi.mooziweb.user.service.impl.UserAddressServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/addAddress.do")
public class MyPageAddAddressFormController implements BaseController {
    private final UserAddressService userAddressService = new UserAddressServiceImpl(new UserAddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String zipcode = req.getParameter("zipcode");
        String address = req.getParameter("address");
        String addressDetail = req.getParameter("addressDetail");
        String phoneNumber = req.getParameter("phoneNumber");

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        UserAddress userAddress = new UserAddress(0, user.getUserId(), name, zipcode, address, addressDetail, phoneNumber);
        userAddressService.saveUserAddress(userAddress);

        return "redirect:/mypage/management_address.do";
    }
}
