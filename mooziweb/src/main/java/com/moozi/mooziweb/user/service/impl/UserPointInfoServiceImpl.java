package com.moozi.mooziweb.user.service.impl;

import com.moozi.mooziweb.user.domain.UserPointInfo;
import com.moozi.mooziweb.user.exception.PointInfoNotFoundException;
import com.moozi.mooziweb.user.exception.PointInfoSaveException;
import com.moozi.mooziweb.user.repository.UserPointInfoRepository;
import com.moozi.mooziweb.user.service.UserPointInfoService;
import com.moozi.mooziweb.common.page.Page;

import java.util.List;
import java.util.Objects;

public class UserPointInfoServiceImpl implements UserPointInfoService {
    UserPointInfoRepository pointInfoRepository;

    public UserPointInfoServiceImpl(UserPointInfoRepository pointInfoRepository) {
        this.pointInfoRepository = pointInfoRepository;
    }
    @Override
    public List<UserPointInfo> getUserPointInfo(String userId) {
        List<UserPointInfo> pointInfos = pointInfoRepository.findById(userId);
        if (Objects.isNull(pointInfos) || pointInfos.isEmpty()) {
            throw new PointInfoNotFoundException(userId);
        }
        return pointInfos;
    }

    @Override
    public Page<UserPointInfo> getUserPointInfo(String userId, int page, int pageSize) {
        Page<UserPointInfo> pointInfoPage = pointInfoRepository.findById(userId, page, pageSize);
        if (Objects.isNull(pointInfoPage) || Objects.isNull(pointInfoPage.getContent()) || pointInfoPage.getContent().isEmpty()) {
            throw new PointInfoNotFoundException(userId);
        }
        return pointInfoPage;
    }

    @Override
    public int saveUserPointInfo(UserPointInfo info) {
        int result = pointInfoRepository.save(info);
        if (result < 1) {
            throw new PointInfoSaveException();
        }
        return result;
    }
}
