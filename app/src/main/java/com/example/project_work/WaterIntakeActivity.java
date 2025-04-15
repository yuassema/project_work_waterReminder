package com.example.project_work;
import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WaterIntakeActivity extends AppCompatActivity {
    private float currentProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake);

        WaterProgressView progressView = findViewById(R.id.water_progress_view);
        Button addGlassButton = findViewById(R.id.btn_add_glass);

        addGlassButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.glass_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int volume;
                if (item.getItemId() == R.id.glass_200) volume = 200;
                else if (item.getItemId() == R.id.glass_250) volume = 250;
                else volume = 500;
                // Update water intake
                Toast.makeText(this, "Added " + volume + " ml", Toast.LENGTH_SHORT).show();
                currentProgress += volume / 2000.0f * 100; // Assuming 2000 ml daily goal
                if (currentProgress > 100) currentProgress = 100;
                progressView.setProgress(currentProgress);
                return true;
            });
            popup.show();
        });
    }
}
