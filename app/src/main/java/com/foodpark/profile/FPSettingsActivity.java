package com.foodpark.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpark.application.App;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Utils;
import com.foodpark.auth.SignInActivity;
import com.foodpark.model.User;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class FPSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar fpToolBar;
    private TextView fpTVEdit;
    private ImageView fpIVBack;
    private EditText etName;
    private EditText etEmail;
    private EditText etSurName;
    private EditText etPhoneNumber;
    private TextView fpSignOut;
    private TextView fpHomeAddress;
    private TextView fpWorkAddress;
    private FirebaseDatabase database;
    private DatabaseReference userReference;
    private PlaceAutocompleteFragment placeAutocompleteFragment;
    private Place address;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp_settings);
        Paper.init(this);
        database = FirebaseDatabase.getInstance();
        userReference = database.getReference(AppConstants.KEY_USER);
        Utils.hideKeyboard(this);
        fpToolBar = findViewById(R.id.fp_settings_toolbar);
        fpIVBack = fpToolBar.findViewById(R.id.fp_iv_tb_settings_back);
        fpTVEdit = fpToolBar.findViewById(R.id.fp_tv_setting_edit);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        etName = findViewById(R.id.et_setting_Name);
        etEmail = findViewById(R.id.et_setting_email);
        etPhoneNumber = findViewById(R.id.et_setting_phone_number);
        etSurName = findViewById(R.id.et_setting_SurName);
        fpSignOut = findViewById(R.id.fp_tv_signout);
        fpWorkAddress = findViewById(R.id.fp_tv_address_work);
        fpHomeAddress = findViewById(R.id.fp_tv_address_home);
        if (App.getInstance().getUser() != null) {
            User user = App.getInstance().getUser();
            etName.setText(user.getName());
            etSurName.setText(user.getSurName());
            if (App.getInstance().getPhoneNumber() != null) {
                etPhoneNumber.setText(user.getPhone());
                Log.d("Phone", "" + user.getPhone());
            }
            etEmail.setText(user.getEmail());
            fpHomeAddress.setText(user.getHomeAddress());
            fpWorkAddress.setText(user.getOfficeAddress());
        }

        fpIVBack.setOnClickListener(this);
        fpTVEdit.setOnClickListener(this);
        fpSignOut.setOnClickListener(this);
        fpHomeAddress.setOnClickListener(this);
        fpWorkAddress.setOnClickListener(this);
        // getUser();
    }


    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.fp_iv_tb_settings_back) {
            finish();
        }

        if (Id == R.id.fp_tv_setting_edit) {
            Utils.setNormalIntent(this, FPProfileEditActivity.class);
        }

        if (Id == R.id.fp_tv_signout) {
            Paper.book().delete(AppConstants.KEY_PAPER_USER);
            Paper.book().delete(AppConstants.KEY_PAPER_PASSWORD);
            Utils.signOut(this, SignInActivity.class);
        }

        if (Id == R.id.fp_tv_address_home) {
            callPlaces(AppConstants.PLACE_AUTOCOMPLETE_REQUEST_CODE_HOME);
        }

        if (Id == R.id.fp_tv_address_work) {
            callPlaces(AppConstants.PLACE_AUTOCOMPLETE_REQUEST_CODE_OFC);
        }
    }

    private void callPlaces(int constantType) {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build();
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(this);


            startActivityForResult(intent, constantType);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.PLACE_AUTOCOMPLETE_REQUEST_CODE_HOME && data != null) {
            if (resultCode == RESULT_OK) {
                place = PlaceAutocomplete.getPlace(this, data);
                fpHomeAddress.setText(place.getAddress());
                if (place != null) {
                    addPlace(place, AppConstants.KEY_HOME);
                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("STATUS", status.getStatusMessage());
            }
        } else if (requestCode == AppConstants.PLACE_AUTOCOMPLETE_REQUEST_CODE_OFC && data != null) {
            if (resultCode == RESULT_OK) {
                place = PlaceAutocomplete.getPlace(this, data);
                fpWorkAddress.setText(place.getAddress());
                if (place != null) {
                    addPlace(place, AppConstants.KEY_OFC);
                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("STATUS", status.getStatusMessage());
            }
        }
    }

    private void addPlace(final Place place, final String type) {
        userReference.child(App.getInstance().getPhoneNumber())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            if (type.equals(AppConstants.KEY_HOME)) {
                                user.setHomeAddress(place.getAddress().toString());
                            } else if (type.equals(AppConstants.KEY_OFC)) {
                                user.setOfficeAddress(place.getAddress().toString());
                            }

                            userReference.getRef().child(App.getInstance().getPhoneNumber()).setValue(user);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void getUser() {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                App.getInstance().setUser(user);
                userReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
