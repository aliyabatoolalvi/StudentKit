package com.aliya.studentkit.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aliya.studentkit.data.Item;
import com.aliya.studentkit.data.User;


@Database(entities = {Item.class, User.class}, version = 1)

//@TypeConverters(value = {MyTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    private static volatile AppDatabase INSTANCE;
    public abstract UserDao userDao();

    
    public static AppDatabase getDatabase(final Context context) {
        // this is using the singleton pattern that only gives 1 same instance after its 1st creation
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDatabase.class, "database_v3")
                            .fallbackToDestructiveMigration()
                            .fallbackToDestructiveMigrationOnDowngrade()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
