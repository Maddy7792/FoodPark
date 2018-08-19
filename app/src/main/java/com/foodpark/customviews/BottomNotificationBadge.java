package com.foodpark.customviews;

import android.content.Context;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.foodpark.R;
import com.foodpark.Utils.Logger;

public class BottomNotificationBadge {

    public static void setBottomNotificationBadge(Context context,int count,BottomNavigationView bottomNavigationView) {
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(3);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        View badge = LayoutInflater.from(context).inflate(R.layout.notification_badge,
                bottomNavigationMenuView, false);
        TextView tvNotificationCount = badge.findViewById(R.id.notificationsbadge);
        itemView.addView(badge);
        if (count>0){
            tvNotificationCount.setText(String.valueOf(count));
        }

    }

    public static void removeBottomNotificationBadge(Context context, BottomNavigationView bottomNavigationView) {
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(3);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        View badge = LayoutInflater.from(context).inflate(R.layout.notification_badge,
                bottomNavigationMenuView, false);
        Logger.d("Notification",""+badge.isFocusable() +""+badge.isEnabled());
        itemView.removeView(itemView.getChildAt(2));
    }

}
