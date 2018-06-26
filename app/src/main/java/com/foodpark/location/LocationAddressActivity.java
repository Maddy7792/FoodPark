package com.foodpark.location;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.foodpark.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationAddressActivity extends AppCompatActivity {


    List<Address> list ;
    List<Address> addresses = null;
    FPLocationAdressAdapter adressAdapter;
    private RecyclerView rvLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_address);
        rvLocation = findViewById(R.id.fp_rv_location_address);
        rvLocation.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvLocation.setHasFixedSize(true);
        list = new ArrayList<>();
        addresses = LocationDetails.getAddressFromLatLng(12.9184636, 77.6242842, LocationAddressActivity.this);
        String[] addressList = new String[addresses.size()] ;
        int j =0;
        for(Address address : addresses) {
            ArrayList<String> addressInfo = new ArrayList<>();
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressInfo.add(address.getAddressLine(i));
            }
            addressList[j] = TextUtils.join(System.getProperty("line.separator"),
                    addressInfo);
            Log.d("AddressList-position", addressList[j]);
            j++;
       /* for (int i =0;i<5;i++){
            String address = list.get(i).getAddressLine(0);
            Log.d("AddressList", "" + address);
        }*/
        }

        setAddressData(addressList);
    }

    private void setAddressData(String[] addressList) {
        adressAdapter = new FPLocationAdressAdapter(this);
        adressAdapter.setLocationData(addressList);
        rvLocation.setAdapter(adressAdapter);
        adressAdapter.notifyDataSetChanged();
    }
}