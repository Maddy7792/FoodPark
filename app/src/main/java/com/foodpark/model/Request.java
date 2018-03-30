package com.foodpark.model;

import com.foodpark.Utils.AppConstants;

import java.util.List;

/**
 * Created by dennis on 18/3/18.
 */

public class Request {

    private String phone;
    private String name;
    private String address;
    private List<Order> foods;
    private String total;
    private String status= AppConstants.KEY_ORDER_PLACED;


    public Request() {
    }

    public Request(String phone, String name, String address, List<Order> foods, String total) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.foods = foods;
        this.total = total;

        /*
        *
        * 0 - placed
        * 1- shipping
        * 2- shipped
        *
        * */
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
