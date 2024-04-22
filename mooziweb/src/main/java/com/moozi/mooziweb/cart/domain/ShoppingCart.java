package com.moozi.mooziweb.cart.domain;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
public class ShoppingCart {
    int recordId;
    String userId;
    int productId;
    int quantity;
    LocalDateTime dateCreated;
    String modelName;
    String productImage;
    int maxQuantity;
    int unitCost;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public int getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return recordId == that.recordId && productId == that.productId && quantity == that.quantity && maxQuantity == that.maxQuantity && unitCost == that.unitCost && Objects.equals(userId, that.userId) && Objects.equals(modelName, that.modelName) && Objects.equals(productImage, that.productImage) && Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, userId, productId, modelName, productImage, quantity, maxQuantity, unitCost, dateCreated);
    }
}
