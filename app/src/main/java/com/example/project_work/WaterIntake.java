package com.example.project_work;


public class WaterIntake {
    private String date;
    private int volume;
    private float progress;

    public WaterIntake(String date, int volume, float progress) {
        this.date = date;
        this.volume = volume;
        this.progress = progress;
    }

    public String getDate() {
        return date;
    }

    public int getVolume() {
        return volume;
    }

    public float getProgress() {
        return progress;
    }
}