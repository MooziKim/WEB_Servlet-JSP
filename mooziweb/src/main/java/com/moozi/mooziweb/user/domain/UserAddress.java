package com.moozi.mooziweb.user.domain;

import java.util.Objects;

public class UserAddress {
    int addressId;
    String userId;
    String name;
    String zipcode;
    String address;
    String addressDetail;
    String phoneNumber;

    public UserAddress(int addressId, String userId, String name, String zipcode, String address, String addressDetail, String phoneNumber) {
        this.addressId = addressId;
        this.userId = userId;
        this.name = name;
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phoneNumber = phoneNumber;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAddress that = (UserAddress) o;
        return Objects.equals(userId, that.userId) && Objects.equals(name, that.name) && Objects.equals(zipcode, that.zipcode) && Objects.equals(address, that.address) && Objects.equals(addressDetail, that.addressDetail) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, zipcode, address, addressDetail, phoneNumber);
    }
}
