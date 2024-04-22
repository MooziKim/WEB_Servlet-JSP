package com.moozi.mooziweb.thread.request.impl;

import com.moozi.mooziweb.thread.request.ChannelRequest;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.user.domain.UserPointInfo;
import com.moozi.mooziweb.user.repository.impl.UserPointInfoRepositoryImpl;
import com.moozi.mooziweb.user.service.UserPointInfoService;
import com.moozi.mooziweb.user.service.impl.UserPointInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class PointChannelRequest extends ChannelRequest {
    private String userId;
    private int orderPrice;

    public PointChannelRequest(String userId, int orderPrice) {
        this.userId = userId;
        this.orderPrice = orderPrice;
    }

    @Override
    public void execute() {
        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
        DbConnectionThreadLocal.initialize();
        try {
            log.debug("pointChannel execute");
            UserPointInfoService pointInfoService = new UserPointInfoServiceImpl(new UserPointInfoRepositoryImpl());
            int pointsEarned = (int) (orderPrice * 0.1);
            UserPointInfo userPointInfo = new UserPointInfo(userId, UserPointInfo.PointInfo.SAVE, pointsEarned, LocalDateTime.now());
            pointInfoService.saveUserPointInfo(userPointInfo);
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }
}
