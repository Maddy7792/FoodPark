package com.foodpark.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by dennis on 26/5/18.
 */

public class FPCheckBox extends CheckBox {

    public FPCheckBox(Context context) {
        super(context);
        init();
    }

    public FPCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FPCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/magettas_regular.otf");
        setTypeface(tf);
    }
}
