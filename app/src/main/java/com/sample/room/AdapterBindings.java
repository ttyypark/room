package com.sample.room;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

// 사용되지 않음.
// ObservableArrayList 사용의 경우만 필요
//------------------------------------------------------
public class AdapterBindings {

    @BindingAdapter({"item"})
    public static void bindItem(RecyclerView recyclerView,
                                ObservableArrayList<UserProfile> userProfiles) {
        UserAdapter adapter = (UserAdapter) recyclerView.getAdapter();
        if(adapter != null){
            adapter.setItems(userProfiles);
        }
    }

//    @BindingAdapter({"imgRes"})
//    public static void imgload(ImageView imageView, String  resId) {
////        int drawId = this.getResources().getIdentifier(resId, "drawable", this.getPackageName());
//        imageView.setImageResource(Integer.parseInt(resId));
////        imageView.setImageResource(getResource().getIdentifier(resId));
//    }
}
