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
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/editAddress.do")
public class MypageEditAddressFormController implements BaseController {
    private final UserAddressService userAddressService = new UserAddressServiceImpl(new UserAddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int addressId = Integer.parseInt(req.getParameter("addressId"));
        String name = req.getParameter("name");
        String zipcode = req.getParameter("zipcode");
        String address = req.getParameter("address");
        String addressDetail = req.getParameter("addressDetail");
        String phoneNumber = req.getParameter("phoneNumber");

        UserAddress userAddress = userAddressService.getUserAddressById(addressId);
        userAddress.setName(name);
        userAddress.setZipcode(zipcode);
        userAddress.setAddress(address);
        userAddress.setAddressDetail(addressDetail);
        userAddress.setPhoneNumber(phoneNumber);

        userAddressService.updateUserAddress(userAddress);

        return "redirect:/mypage/management_address.do";
    }
}
