package com.moozi.mooziweb.user.repository;

import com.moozi.mooziweb.user.domain.UserPointInfo;
import com.moozi.mooziweb.common.page.Page;

import java.util.List;

public interface UserPointInfoRepository {
    List<UserPointInfo> findById(String userId);
    Page<UserPointInfo> findById(String userId, int page, int pageSize);
    int save(UserPointInfo info);
}
