package com.sample.room;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// --------------------------------------------------
// set, get 과
// @BindingAdapter({"android:imageUrl"}) 사용방법
// --------------------------------------------------

@Entity             // room dataBase 사용 준비
public class UserProfile {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String phone;
    public String address;
    public int photo;

//    public UserProfile() {
//    }

    public UserProfile(String name, String phone, String address, int photo) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadImage(View view, String imageId){
        ImageView imageView = (ImageView) view;
        String imagName = "image" + imageId;
        int drawableResId = view.getResources().getIdentifier(imagName, "drawable",
                view.getContext().getPackageName());
//        Log.e("Draw id: ", "" + imagName + ":" + drawableResId);
        if(drawableResId == 0) {
            imageView.setImageResource(R.drawable.image2);
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(view.getContext(), drawableResId));
        }
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        if(photo == 0) {
            return "" ;
        } else {
            return String.valueOf(photo);
        }
    }

    public void setPhoto(String inphoto) {
        Log.e("inPhoto", inphoto);
        if(inphoto == "" || inphoto == null) {
            this.photo = 0;
        } else {
            try {
                this.photo = Integer.parseInt(inphoto);
            } catch (Exception e){
                Log.e("Try", e.toString());
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
