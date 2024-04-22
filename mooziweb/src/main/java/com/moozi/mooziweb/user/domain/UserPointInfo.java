package com.moozi.mooziweb.user.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserPointInfo {
    public enum PointInfo {
        USE, SAVE
    }
    String userId;
    PointInfo pointInfo;
    int point;
    LocalDateTime pointDate;

    public UserPointInfo(String userId, PointInfo pointInfo, int point, LocalDateTime pointDate) {
        this.userId = userId;
        this.pointInfo = pointInfo;
        this.point = point;
        this.pointDate = pointDate;
    }

    public String getUserId() {
        return userId;
    }

    public PointInfo getPointInfo() {
        return pointInfo;
    }

    public int getPoint() {
        return point;
    }

    public LocalDateTime getPointDate() {
        return pointDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPointInfo(PointInfo pointInfo) {
        this.pointInfo = pointInfo;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setPointDate(LocalDateTime pointDate) {
        this.pointDate = pointDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, pointInfo, point, pointDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o) || getClass() != o.getClass()) return false;
        UserPointInfo userPointInfo = (UserPointInfo) o;
        return Objects.equals(userId, userPointInfo.userId) &&
                Objects.equals(pointInfo, userPointInfo.pointInfo) &&
                point == userPointInfo.point;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
