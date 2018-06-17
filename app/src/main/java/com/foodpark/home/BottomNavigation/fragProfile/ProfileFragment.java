package com.foodpark.home.BottomNavigation.fragProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Utils;
import com.foodpark.profile.FPFavouriteActivity;
import com.foodpark.profile.FPOtherSettingsActivity;
import com.foodpark.profile.FPPaymentActivity;
import com.foodpark.profile.FPSettingsActivity;

/**
 * Created by dennis on 10/5/18.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private LinearLayout fpLLFavourite;
    private LinearLayout fpLLPayment;
    private LinearLayout fpLLHelp;
    private LinearLayout fpLLFreeFood;
    private LinearLayout fpLLPromotions;
    private LinearLayout fpLLSettings;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profile_fragment, container, false);
        fpLLFavourite = view.findViewById(R.id.fp_ll_fav);
        fpLLPayment = view.findViewById(R.id.fp_ll_payment);
        fpLLHelp = view.findViewById(R.id.fp_ll_help);
        fpLLFreeFood = view.findViewById(R.id.fp_ll_freefood);
        fpLLPromotions = view.findViewById(R.id.fp_ll_promotions);
        fpLLSettings = view.findViewById(R.id.fp_ll_settings);
        fpLLPromotions.setOnClickListener(this);
        fpLLFavourite.setOnClickListener(this);
        fpLLPayment.setOnClickListener(this);
        fpLLHelp.setOnClickListener(this);
        fpLLFreeFood.setOnClickListener(this);
        fpLLSettings.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.fp_ll_fav) {
            Utils.setNormalIntent(getActivity(), FPFavouriteActivity.class);
        }

        if (Id == R.id.fp_ll_payment) {
            Utils.setNormalIntent(getActivity(), FPPaymentActivity.class);
        }
        if (Id == R.id.fp_ll_help) {
            Utils.setDataIntent(getActivity(), FPOtherSettingsActivity.class, AppConstants.HELP);
        }
        if (Id == R.id.fp_ll_freefood) {
            Utils.setDataIntent(getActivity(), FPOtherSettingsActivity.class, AppConstants.FREE_FOOD);
        }
        if (Id == R.id.fp_ll_promotions) {
            Utils.setDataIntent(getActivity(), FPOtherSettingsActivity.class, AppConstants.PROMOTIONS);
        }
        if (Id == R.id.fp_ll_settings) {
            Utils.setNormalIntent(getActivity(), FPSettingsActivity.class);
        }

    }
}
