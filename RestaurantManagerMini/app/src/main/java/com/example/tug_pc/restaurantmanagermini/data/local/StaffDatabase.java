package com.example.tug_pc.restaurantmanagermini.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.tug_pc.restaurantmanagermini.model.Staff;

import static com.example.tug_pc.restaurantmanagermini.data.local.StaffDatabase.DATABASE_VERSION;

@Database(entities = Staff.class, version = DATABASE_VERSION)
public abstract class StaffDatabase extends RoomDatabase {
    private static StaffDatabase sStaffDatabase;

    static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "Room-database";

    public abstract StaffDAO staffDAO();

    public static StaffDatabase getInstance(Context context) {
        if (sStaffDatabase == null) {
            sStaffDatabase = Room.databaseBuilder(context, StaffDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sStaffDatabase;
    }
}
