package com.foodpark.model;

/**
 * Created by dennis on 9/5/18.
 */

public class Price {

    private int rupee;
    private boolean isSelected=false;

    public Price(int rupee) {
        this.rupee = rupee;
    }

    public int getRupee() {
        return rupee;
    }

    public void setRupee(int rupee) {
        this.rupee = rupee;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
