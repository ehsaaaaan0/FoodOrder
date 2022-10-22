package com.app.foodorder.ModelCLasses;


public class PlaceOrderModel {
    String orderBy, orderName,orderQuantity,  orderPrice ,orderAddress,prepairTime, dishKey ,key, orderStatus;
    String rating;


    public PlaceOrderModel(String orderBy, String orderName, String orderQuantity, String orderPrice, String orderAddress, String prepairTime, String dishKey, String key, String orderStatus, String rating) {
        this.orderBy = orderBy;
        this.orderName = orderName;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
        this.orderAddress = orderAddress;
        this.prepairTime = prepairTime;
        this.dishKey = dishKey;
        this.key = key;
        this.orderStatus = orderStatus;
        this.rating = rating;
    }
    public PlaceOrderModel(String orderBy, String orderName, String orderQuantity, String orderPrice, String orderAddress,  String dishKey, String key, String orderStatus) {
        this.orderBy = orderBy;
        this.orderName = orderName;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
        this.orderAddress = orderAddress;
        this.prepairTime = prepairTime;
        this.dishKey = dishKey;
        this.key = key;
        this.orderStatus = orderStatus;
    }

    public PlaceOrderModel(String orderBy, String orderName, String orderQuantity, String orderPrice, String orderAddress, String key) {
        this.orderBy = orderBy;
        this.orderName = orderName;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
        this.orderAddress = orderAddress;
        this.key = key;
    }
    public PlaceOrderModel(){}

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPrepairTime() {
        return prepairTime;
    }

    public void setPrepairTime(String prepairTime) {
        this.prepairTime = prepairTime;
    }

    public String getDishKey() {
        return dishKey;
    }

    public void setDishKey(String dishKey) {
        this.dishKey = dishKey;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
