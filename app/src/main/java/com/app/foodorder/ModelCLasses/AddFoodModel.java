package com.app.foodorder.ModelCLasses;

public class AddFoodModel {
    String image1, image2, image3, dishName, dishDescription, dishPrice, categorie , key, time;

    public AddFoodModel(String image1, String image2, String image3, String dishName, String dishDescription, String dishPrice, String categorie, String key, String time) {
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.dishName = dishName;
        this.dishDescription = dishDescription;
        this.dishPrice = dishPrice;
        this.categorie = categorie;
        this.key = key;
        this.time = time;
    }

    public AddFoodModel(){}

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
