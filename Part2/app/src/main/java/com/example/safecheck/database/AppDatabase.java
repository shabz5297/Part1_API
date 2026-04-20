package com.example.safecheck.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.safecheck.dao.DefectDao;
import com.example.safecheck.dao.SafetyCheckDao;
import com.example.safecheck.model.Defect;
import com.example.safecheck.model.SafetyCheck;

@Database(entities = {SafetyCheck.class, Defect.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract SafetyCheckDao safetyCheckDao();
    public abstract DefectDao defectDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "safecheck_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
