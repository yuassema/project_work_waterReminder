package com.example.project_work;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class WaterIntakeFragment extends Fragment {
    private float currentProgress = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water_intake, container, false);

        WaterProgressView progressView = view.findViewById(R.id.water_progress_view);
        Button addGlassButton = view.findViewById(R.id.btn_add_glass);

        addGlassButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getContext(), v);
            popup.getMenuInflater().inflate(R.menu.glass_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int volume;
                if (item.getItemId() == R.id.glass_200) volume = 200;
                else if (item.getItemId() == R.id.glass_250) volume = 250;
                else volume = 500;
                Toast.makeText(getContext(), "Added " + volume + " ml", Toast.LENGTH_SHORT).show();
                currentProgress += volume / 2000.0f * 100; // Assuming 2000 ml daily goal
                if (currentProgress > 100) currentProgress = 100;
                progressView.setProgress(currentProgress);
                return true;
            });
            popup.show();
        });

        return view;
    }
}