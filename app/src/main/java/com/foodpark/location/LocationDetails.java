package com.foodpark.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationDetails {

    public static Address getAddress(Location location, Context context){
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        final int maxResult =5;
        Address address = new Address(Locale.ENGLISH);
        try{
            if (Geocoder.isPresent()) {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return address;
    }


    public static List<Address> getAddressFromLatLng(double latitude,double longitude ,Context context){
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        List<Address> addressList=null;
        try{
            if (Geocoder.isPresent()) {
                addressList = geocoder.getFromLocation(latitude, longitude, 5);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return addressList;
    }
}
