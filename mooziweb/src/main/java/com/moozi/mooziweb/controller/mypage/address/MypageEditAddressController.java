package com.moozi.mooziweb.controller.mypage.address;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.user.domain.UserAddress;
import com.moozi.mooziweb.user.repository.impl.UserAddressRepositoryImpl;
import com.moozi.mooziweb.user.service.UserAddressService;
import com.moozi.mooziweb.user.service.impl.UserAddressServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/editAddress.do")
public class MypageEditAddressController implements BaseController {
    private final UserAddressService userAddressService = new UserAddressServiceImpl(new UserAddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int addressId = Integer.parseInt(req.getParameter("addressId"));

        UserAddress address = userAddressService.getUserAddressById(addressId);

        req.setAttribute("address", address);
        return "mypage/edit_address_form";
    }
}
