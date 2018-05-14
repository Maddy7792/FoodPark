package com.foodpark.FilterFrags;

import com.foodpark.R;
import com.foodpark.model.Price;

import java.util.ArrayList;

/**
 * Created by dennis on 8/5/18.
 */

public class FilterList {

    public static ArrayList<String> getSortList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Recommended");
        list.add("Most popular");
        list.add("Rating");
        list.add("Delivery time");
        return list;
    }

    public static ArrayList<String> getDietList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Vegetarian");
        list.add("Vegan");
        list.add("Gluten-free");
        return list;
    }

    public static int[] getDietImages() {

        int[] list = {R.drawable.vegetarian,
                R.drawable.vegan,
                R.drawable.gluten_free};
        return list;
    }


    public static ArrayList<Price> getPrices() {
        ArrayList<Price> prices = new ArrayList<>();
        prices.add(new Price(R.string.single));
        prices.add(new Price(R.string.doublerupess));
        prices.add(new Price(R.string.triple));
        prices.add(new Price(R.string.four));
        return prices;
    }
}
