package com.foodpark.model;

public class Favourites {

    private String FoodId;
    private String FoodName;
    private String FoodImage;
    private String FoodDescription;
    private String FoodPrice;
    private String FoodDiscount;
    private String FoodMenuId;
    private String UserPhone;

    public Favourites(String foodId, String foodName, String foodImage,
                      String foodDescription, String foodPrice, String foodDiscount,
                      String foodMenuId,String userPhone) {
        FoodId = foodId;
        FoodName = foodName;
        FoodImage = foodImage;
        FoodDescription = foodDescription;
        FoodPrice = foodPrice;
        FoodDiscount = foodDiscount;
        FoodMenuId = foodMenuId;
        UserPhone = userPhone;
    }

    public Favourites() {
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(String foodImage) {
        FoodImage = foodImage;
    }

    public String getFoodDescription() {
        return FoodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        FoodDescription = foodDescription;
    }

    public String getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        FoodPrice = foodPrice;
    }

    public String getFoodDiscount() {
        return FoodDiscount;
    }

    public void setFoodDiscount(String foodDiscount) {
        FoodDiscount = foodDiscount;
    }

    public String getFoodMenuId() {
        return FoodMenuId;
    }

    public void setFoodMenuId(String foodMenuId) {
        FoodMenuId = foodMenuId;
    }
}
