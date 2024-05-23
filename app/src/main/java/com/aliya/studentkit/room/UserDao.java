package com.aliya.studentkit.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aliya.studentkit.data.User;


@Dao
public interface UserDao {
    @Query("SELECT * FROM User limit 1")
    User getUser();
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrReplace(User CartItem);
    
    @Query("DELETE FROM User")
    void deleteAll();

}