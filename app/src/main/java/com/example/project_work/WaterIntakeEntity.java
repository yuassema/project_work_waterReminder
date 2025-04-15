package com.example.project_work;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "water_intake")
public class WaterIntakeEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "volume")
    public int volume;

    @ColumnInfo(name = "progress")
    public float progress;
}