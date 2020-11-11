package com.sample.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserProfileDao {
    @Insert
    void insert(UserProfile userProfile);

    @Update
    void update(UserProfile userProfile);

    @Delete
    void delete(UserProfile userProfile);

    @Query("SELECT * FROM UserProfile")
    LiveData<List<UserProfile>> getAll();
    // Live Data 사용. 관찰(observe)...

    @Query("SELECT * FROM UserProfile")
    List<UserProfile> getAlls();
}
