package com.foodpark.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.foodpark.callback.OnItemClickListener;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Utils;
import com.foodpark.customviews.CustomViewPager;
import com.foodpark.FilterFrags.diet.DietaryFragment;
import com.foodpark.FilterFrags.price.PriceFragment;
import com.foodpark.FilterFrags.sort.SortFragment;

/**
 * Created by dennis on 7/5/18.
 */

public class FPFilterDialog extends DialogFragment implements View.OnClickListener ,OnItemClickListener{

    private FragmentManager fragmentManager;
    private Context context;
    private Dialog dialog;
    private TabLayout tabs;
    private ImageView fpCancel;
    private CustomViewPager viewPager;
    private Button fpBtnFinished;
    public FPFilterDialog() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FP_DIALOG);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            Utils.setStatuscolor(getActivity());
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DIALOG_ANIMATIONS;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("ResourceType")
    @Override
    public void onStart() {
        super.onStart();

        dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            int screenHeight = Utils.deviceDimensions(getActivity(), 100, AppConstants.WIDTH);
            dialog.getWindow().setLayout(width, height);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat
                    .getColor(getActivity(), R.color.colorTrans)));
            getDialog().getWindow().setGravity(Gravity.TOP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_dialog_layout, container);
        fpCancel = view.findViewById(R.id.fp_cancel);
        tabs = view.findViewById(R.id.fp_tabs);
        fpBtnFinished = view.findViewById(R.id.fp_btn_finished);
        viewPager = (CustomViewPager) view.findViewById(R.id.fp_viewpager);
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        Pager adapter = new Pager(getChildFragmentManager(), tabs.getTabCount());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        try {
            tabs.getTabAt(0).setText("Sort");
            tabs.getTabAt(1).setText("Price");
            tabs.getTabAt(2).setText("Dietary");
        } catch (Exception e) {
            e.printStackTrace();
        }

        fpCancel.setOnClickListener(this);
        fpBtnFinished.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        int Id = v.getId();
        if (Id == R.id.fp_cancel) {
            getDialog().dismiss();
        }

        if (Id == R.id.fp_btn_finished){
            dismiss();
        }
    }

    @Override
    public void OnClick(View view, int Position, boolean isLongClick) {
        Toast.makeText(context, "Position"+Position, Toast.LENGTH_SHORT).show();
    }


    private class Pager extends FragmentStatePagerAdapter {
        int tabCount;

        public Pager(android.support.v4.app.FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    SortFragment sort = new SortFragment();
                    return sort;
                case 1:
                    PriceFragment price = new PriceFragment();
                    return price;
                case 2:
                    DietaryFragment die = new DietaryFragment();
                    return die;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
}
