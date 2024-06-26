package com.aliya.studentkit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.aliya.studentkit.data.Item;
import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM Item LIMIT 500")
    List<Item> getAll();

    @Query("SELECT * FROM Item LIMIT 500")
    LiveData<List<Item>> liveGetAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrReplace(Item item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplaceAll(List<Item> listitem);

    @Delete
    void delete(Item item);

    @Update
    void update(Item item);

    @Query("DELETE FROM Item")
    void deleteAll();

    @Query("SELECT distinct(category) FROM item")
    List<String> getAllCats();
}