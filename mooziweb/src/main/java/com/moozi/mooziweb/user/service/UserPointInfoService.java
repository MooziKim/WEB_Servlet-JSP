package com.moozi.mooziweb.user.service;

import com.moozi.mooziweb.user.domain.UserPointInfo;
import com.moozi.mooziweb.common.page.Page;

import java.util.List;

public interface UserPointInfoService {
    List<UserPointInfo> getUserPointInfo(String userId);
    Page<UserPointInfo> getUserPointInfo(String userId, int page, int pageSize);
    int saveUserPointInfo(UserPointInfo info);
}
