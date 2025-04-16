package com.example.project_work;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WaterIntakeEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WaterIntakeDao waterIntakeDao();
}