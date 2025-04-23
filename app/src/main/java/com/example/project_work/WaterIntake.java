package com.example.project_work;

public class WaterIntake {
    private String time;
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

    public void setTime(String time) {
        this.time = time;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}