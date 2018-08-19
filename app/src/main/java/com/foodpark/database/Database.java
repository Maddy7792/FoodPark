package com.foodpark.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.foodpark.model.Favourites;
import com.foodpark.model.Food;
import com.foodpark.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis on 18/2/18.
 */

public class Database extends SQLiteAssetHelper {

    private final static String DB_NAME = "FoodParkDB.db";
    private final static int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public List<Order> getCarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))
                ));

            } while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');"
                , order.getProductId()
                , order.getProductName()
                , order.getQuantity()
                , order.getPrice()
                , order.getDiscount());

        db.execSQL(query);
    }

    public void deleteToCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }


    public void removeFavourite(String FoodId, String UserPhone) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Favorites WHERE FoodId='%s' and UserPhone='%s';", FoodId, UserPhone);
        db.execSQL(query);
    }

    public boolean isFavourite(String FoodId, String UserPhone) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM Favorites WHERE FoodId='%s' and UserPhone='%s';", FoodId, UserPhone);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<Favourites> getFavourites(String userPhone) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"FoodId","FoodName","FoodImage","FoodDescription","FoodPrice","FoodDiscount","FoodMenuId","UserPhone"};
        String sqlTable = "Favorites";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, "UserPhone=?", new String[]{userPhone}, null, null, null);
        final List<Favourites> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                result.add(new Favourites(c.getString(c.getColumnIndex("FoodId")),
                        c.getString(c.getColumnIndex("FoodName")),
                        c.getString(c.getColumnIndex("FoodImage")),
                        c.getString(c.getColumnIndex("FoodDescription")),
                        c.getString(c.getColumnIndex("FoodPrice")),
                                c.getString(c.getColumnIndex("FoodDiscount")),
                                c.getString(c.getColumnIndex("FoodMenuId")),
                                        c.getString(c.getColumnIndex("UserPhone"))));

            } while (c.moveToNext());
        }
        return result;
    }

    public void addToFavourite(Favourites favourites) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO Favorites(FoodId,FoodName,FoodImage,FoodDescription,FoodPrice,FoodDiscount,FoodMenuId,UserPhone) VALUES('%s','%s','%s','%s','%s','%s','%s','%s');"
                , favourites.getFoodId()
                , favourites.getFoodName()
                , favourites.getFoodImage()
                , favourites.getFoodDescription()
                , favourites.getFoodPrice(),
                favourites.getFoodDiscount(),
                favourites.getFoodMenuId(),
                favourites.getUserPhone());
        db.execSQL(query);
    }


    public int getCountCards(){
        int count =0;

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT(*) FROM OrderDetail");
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                count = cursor.getInt(0);
            }while (cursor.moveToNext());
        }

        return count;
    }
}
