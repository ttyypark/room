package com.sample.room;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.sample.room.databinding.ActivityMainBinding;

import java.util.List;

public class UserProfileViewModel extends AndroidViewModel {
//    public List<UserProfile> userProfileList;
    public LiveData<List<UserProfile>> userProfileList;
    public UserProfile mUserProfile;
    private final UserProfileDao userProfileDao;
    private final UserProfileDatabase db;
    private ActivityMainBinding mainBinding;

    public UserProfileViewModel(@NonNull Application application) {
        super(application);

        db = Room.databaseBuilder(application, UserProfileDatabase.class, "user")
//                .allowMainThreadQueries()  //DB 작업은 Async로 함.
                .build();
        userProfileDao = db.getUserProfileDao();
        userProfileList = userProfileDao.getAll();
        mUserProfile = new UserProfile("", "", "", 0);
    }

    public LiveData<List<UserProfile>> getAll(){
        return db.getUserProfileDao().getAll();
    }

    private void userViewClear(){
        mainBinding.name.setText("");
        mainBinding.phone.setText("");
        mainBinding.address.setText("");
        mainBinding.photo.setText("");
    }


    public void insert(UserProfile userProfile, ActivityMainBinding binding) {
        mainBinding = binding;
        new InsertUserProfileAsyncTask().execute(userProfile);
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertUserProfileAsyncTask extends AsyncTask<UserProfile, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            userViewClear();
        }

        @Override
        protected Void doInBackground(UserProfile... userProfiles) {
            userProfileDao.insert(userProfiles[0]);
            return null;
        }
    }

    public void update(UserProfile userProfile, ActivityMainBinding binding) {
        mainBinding = binding;
        new UpdateUserProfileAsyncTask().execute(userProfile);
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateUserProfileAsyncTask extends AsyncTask<UserProfile, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            userViewClear();
        }

        @Override
        protected Void doInBackground(UserProfile... userProfiles) {
            userProfileDao.update(userProfiles[0]);
            return null;
        }
    }

    public void delete(UserProfile userProfile, ActivityMainBinding binding) {
        mainBinding = binding;
        new DeleteUserProfileAsyncTask().execute(userProfile);
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteUserProfileAsyncTask extends AsyncTask<UserProfile, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            userViewClear();
        }

        @Override
        protected Void doInBackground(UserProfile... userProfiles) {
            userProfileDao.delete(userProfiles[0]);
            return null;
        }
    }

}
