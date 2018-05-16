package com.foodpark.Utils;

import android.util.Log;

import com.foodpark.model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by dennis on 16/5/18.
 */

public class UnUseCode {
    private void searchMoreCategories(final ArrayList<Category> searchList, String query, DatabaseReference refMoreCatories) {
        Query moreReference = refMoreCatories.orderByChild(AppConstants.SEARCH_KEY).startAt(query).endAt(query + "\uf8ff");
        Log.d("Query-1", "" + query);
        moreReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // searchList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    searchList.add(ds.getValue(Category.class));
                }
                Log.d("searchList", "" + searchList.size());
               /* setTopData(searchList);
                hideViews();*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
