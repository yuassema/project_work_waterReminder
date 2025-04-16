package com.example.project_work;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {
    private static final String ARG_LAYOUT = "layout_id";

    public static ContentFragment newInstance(int layoutId) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT, layoutId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getArguments() != null ? getArguments().getInt(ARG_LAYOUT) : R.layout.content_main;
        View view = inflater.inflate(layoutId, container, false);

        if (layoutId == R.layout.content_main) {
            setupMainScreen(view);
        } else if (layoutId == R.layout.activity_water_intake) {
            setupWaterIntakeScreen(view);
        } else if (layoutId == R.layout.activity_history) {
            setupHistoryScreen(view);
        } else if (layoutId == R.layout.activity_achievements) {
            setupAchievementsScreen(view);
        }

        return view;
    }

    private void setupMainScreen(View view) {
        Spinner genderSpinner = view.findViewById(R.id.spinner_gender);
        EditText weightEdit = view.findViewById(R.id.edit_weight);
        Button calculateButton = view.findViewById(R.id.btn_calculate);

        calculateButton.setOnClickListener(v -> {
            String gender = genderSpinner.getSelectedItem().toString();
            String weightStr = weightEdit.getText().toString();
            if (!weightStr.isEmpty()) {
                double weight = Double.parseDouble(weightStr);
                double waterIntake = gender.equals("Male") ? weight * 35 : weight * 31;
                Toast.makeText(getContext(), "Daily water intake: " + waterIntake + " ml", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Enter weight", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupWaterIntakeScreen(View view) {
        WaterProgressView progressView = view.findViewById(R.id.water_progress_view);
        Button addGlassButton = view.findViewById(R.id.btn_add_glass);
        float[] currentProgress = {0};

        addGlassButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getContext(), v);
            popup.getMenuInflater().inflate(R.menu.glass_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int volume;
                if (item.getItemId() == R.id.glass_200) volume = 200;
                else if (item.getItemId() == R.id.glass_250) volume = 250;
                else volume = 500;
                Toast.makeText(getContext(), "Added " + volume + " ml", Toast.LENGTH_SHORT).show();
                currentProgress[0] += volume / 2000.0f * 100; // Assuming 2000 ml daily goal
                if (currentProgress[0] > 100) currentProgress[0] = 100;
                progressView.setProgress(currentProgress[0]);
                return true;
            });
            popup.show();
        });
    }

    private void setupHistoryScreen(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<WaterIntake> intakes = new ArrayList<>();
        intakes.add(new WaterIntake("2025-04-14", 500, 25)); // Sample data
        recyclerView.setAdapter(new WaterIntakeAdapter(intakes));
    }

    private void setupAchievementsScreen(View view) {
        // Add achievements logic if needed
    }
}