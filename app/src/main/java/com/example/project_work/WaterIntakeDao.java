package com.example.project_work;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface WaterIntakeDao {
    @Query("SELECT * FROM water_intake")
    List<WaterIntakeEntity> getAll();

    @Insert
    void insert(WaterIntakeEntity intake);
}