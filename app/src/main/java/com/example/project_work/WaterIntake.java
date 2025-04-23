package com.example.project_work;

public class WaterIntake {
    private String time; // Changed from date to time for main screen
    private int volume;
    private float progress;

    public WaterIntake(String time, int volume, float progress) {
        this.time = time;
        this.volume = volume;
        this.progress = progress;
    }

    public String getTime() {
        return time;
    }

    public int getVolume() {
        return volume;
    }

    public float getProgress() {
        return progress;
    }
}