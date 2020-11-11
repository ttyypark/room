package com.sample.room;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.sample.room.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private UserProfileViewModel model;     // View Model
    private ActivityMainBinding mainBinding;    // View Binding , Data Binding
    public String userListText;

    public ObservableArrayList<UserProfile> userProfiles;
    private UserAdapter mAdapter;
    private UserProfileDatabase db;
    private ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // 다른 Binding 방법
//        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(mainBinding.getRoot());
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//      Data Binding, recyclerView에 연결
        mainBinding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        mainBinding.recyclerView.setHasFixedSize(true);

        mainBinding.setLifecycleOwner(this);    // Binding(ViewModel)에 Live Data 사용 가능

//  userList 화면에 직접 출력하는 예비 방법, ObservableArrayList 사용 --------------------------------------
//        userProfiles = new ObservableArrayList<>();
//        prepareUserData();
//        mAdapter = new UserAdapter(userProfiles, this);
//        mainBinding.recyclerView.setAdapter(mAdapter);
//        updateUserProfileList(userProfileList)
//  userList 화면에 직접 출력하는 예비 방법 ---------------------------------------------------------------

////      옛날 방식 ===== ViewModel 사용으로 대체 =================================
//        db = Room.databaseBuilder(this, UserProfileDatabase.class, "userprofile")
//                .allowMainThreadQueries()  //DB 작업은 비동기 처리 Async로 함. (ViewModel에 포함)
//                .build();
//
//        // UI,   Adapter data 와 recyclerView 연결
//        // Lambda 사용  ----------------
//        db.getUserProfileDao().getAll().observe(this, userProfiles -> {
//            mAdapter = new UserAdapter(userProfiles, getApplicationContext());
//            mainBinding.recyclerView.setAdapter(mAdapter);
//        });
////      옛날 방식 ==============================================================

////      ViewModel 사용 ========================================================
////      모든 로직이 ViewModel 로 들어가고, DB 도 들어감.
        model = new ViewModelProvider(this).get(UserProfileViewModel.class);
        mainBinding.setModel(model);    // Data Binding - ViewModel이 xml화일로 들오감

        // Live Data 사용. 관찰(observe) : Dao 화일에서 LiveData<> 설정!
        // Lambda 함수 사용 : Project Structure -> Modules -> Source Compatibility 1.8이상 필요
        model.getAll().observe(this, userProfileList -> {
            mAdapter = new UserAdapter(userProfileList, getApplicationContext());
            mainBinding.recyclerView.setAdapter(mAdapter);
//            mainBinding.setUserProfiles(userProfiles);  // ObservableArrayList 를 사용한 Adapter Binding
//-----------------------------------------------------------------
////            LiveData의 .observe를 사용하는 방법과            (DB 에는 어느것이 유리?)
////            ObservableArrayList 를 사용하여 recyclerView의 Adapter Binding을 사용하는 방법
//-----------------------------------------------------------------

            // ItemTouchHelper 생성
            helper = new ItemTouchHelper(new ItemTouchHelperCallback(mAdapter));
            helper.attachToRecyclerView(mainBinding.recyclerView);

            mAdapter.setOnItemClickListener(user -> {
                Toast.makeText(MainActivity.this, "선택됨: " + user.name, Toast.LENGTH_SHORT).show();
                model.mUserProfile = user;
                userViewSet();
            });

        });
////      ViewModel 사용 ========================================================

////    // Insert -------------------------------------------------------------
//        // Lambda 사용 가능  ----------------
        mainBinding.add.setOnClickListener(v -> {
            model.insert(new UserProfile(
                    model.mUserProfile.name, model.mUserProfile.phone,
                    model.mUserProfile.address, model.mUserProfile.photo), mainBinding);
        });

        mainBinding.update.setOnClickListener(v -> {
            model.update(model.mUserProfile, mainBinding);
        });

        mainBinding.delete.setOnClickListener(v -> {
            model.delete(model.mUserProfile, mainBinding);
        });

////// insert button에 따른 addUserProfile() 함수는 XML 화일에서 android:onClick="addUserProfile"로 대체가능!
// 혹은
//     양방향 Binding을 위용한 Insert button click 을 xml 화일에 넣는 방법
//      android:onClick="@{() -> model.insert(model.mUserProfile)}"/>
//
////    // Insert -------------------------------------------------------------

//       // Lambda 함수 사용방법 : Project Structure -> Modules -> Source Compatibility 1.8이상 필요
//        model.userProfileList.observe(this, userProfileList -> updateUserProfileList(userProfileList));
//        model.userProfileList.observe(this, this::updateUserProfileList);

    }

    private void userViewSet(){
        Log.e("User:",model.mUserProfile.toString());
        mainBinding.name.setText(model.mUserProfile.getName());
        mainBinding.phone.setText(model.mUserProfile.getPhone());
        mainBinding.address.setText(model.mUserProfile.getAddress());
        mainBinding.photo.setText(model.mUserProfile.getPhoto());
    }

//    private void prepareUserData() {
//        userProfiles.add(new UserProfile("Mad Max: Fury Road", "111-222-3333", "서울", 1));
//        userProfiles.add(new UserProfile("Mad Max:  Road", "111-222-3333", "서울", 2));
//        userProfiles.add(new UserProfile("Mad Max: Fury ", "111-222-3333", "서울", 3));
//        userProfiles.add(new UserProfile("MNN TTT: Fury ", "111-332-3333", "서울", 4));
//    }
//
//    private void updateUserProfileList(List<UserProfile> userProfileList) {
//        userListText = "사용자 목록";
//        for (UserProfile userProfile : userProfileList) {
//            userListText += "\n" + userProfile.id + ", " + userProfile.name + ", " + userProfile.phone + ", " + userProfile.address;
//        }
//
//        // Data Binding으로?
//        mainBinding.userList.setText(userListText);
//    }
//
//    public void addUserProfile(View view) {
//        UserProfile userProfile = new UserProfile(mainBinding.name.getText().toString(),
//                mainBinding.phone.getText().toString(),
//                mainBinding.address.getText().toString());
//        model.insert(userProfile, mainBinding);
////        model.insert(userProfile);
//    }
}
