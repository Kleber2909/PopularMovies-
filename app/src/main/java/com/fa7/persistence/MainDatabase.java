package com.fa7.persistence;

import android.arch.persistence.room.Room;
import android.content.Context;

public class MainDatabase {
    private static AppDatabase appDatabase;
    public static AppDatabase getInstance(Context context){
        if (appDatabase == null){
            appDatabase = Room.databaseBuilder(context,
                    AppDatabase.class, "database-name").build();
        }

        return appDatabase;
    }
}
