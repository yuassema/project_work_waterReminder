package com.example.project_work;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ContentFragment extends Fragment {
    private static final String ARG_LAYOUT = "layout_id";
    private int dailyGoal = 0; // Will be calculated based on weight and gender
    private float currentProgress = 0;
    private List<WaterIntake> intakes = new ArrayList<>();
    private WaterIntakeAdapter adapter;

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
        // Input Section
        Spinner genderSpinner = view.findViewById(R.id.spinner_gender);
        EditText weightEdit = view.findViewById(R.id.edit_weight);
        Button calculateButton = view.findViewById(R.id.btn_calculate);
        View inputCard = view.findViewById(R.id.input_card);

        // Progress Section
        View progressSection = view.findViewById(R.id.progress_section);
        TextView textProgressMl = view.findViewById(R.id.text_progress_ml);
        WaterProgressView progressView = view.findViewById(R.id.water_progress_view);
        ImageButton addGlassButton = view.findViewById(R.id.btn_add_glass);
        ImageButton resetButton = view.findViewById(R.id.btn_reset);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_history_main);

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WaterIntakeAdapter(intakes);
        recyclerView.setAdapter(adapter);

        // Calculate Button Listener
        calculateButton.setOnClickListener(v -> {
            String gender = genderSpinner.getSelectedItem().toString();
            String weightStr = weightEdit.getText().toString();
            if (!weightStr.isEmpty()) {
                double weight = Double.parseDouble(weightStr);
                dailyGoal = (int) (gender.equals("Male") ? weight * 35 : weight * 31);
                Toast.makeText(getContext(), "Daily water intake: " + dailyGoal + " ml", Toast.LENGTH_LONG).show();

                // Hide input section, show progress section
                inputCard.setVisibility(View.GONE);
                progressSection.setVisibility(View.VISIBLE);

                // Update progress bar with the new goal
                updateProgress(textProgressMl, progressView);
            } else {
                Toast.makeText(getContext(), "Enter weight", Toast.LENGTH_SHORT).show();
            }
        });

        // Add Glass Button Listener
        addGlassButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getContext(), v);
            popup.getMenuInflater().inflate(R.menu.glass_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int volume;
                if (item.getItemId() == R.id.glass_200) volume = 200;
                else if (item.getItemId() == R.id.glass_250) volume = 250;
                else volume = 500;

                currentProgress += (volume * 100.0f) / dailyGoal;
                if (currentProgress > 100) currentProgress = 100;

                // Add to history
                String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                intakes.add(0, new WaterIntake(time, volume, currentProgress));
                adapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);

                updateProgress(textProgressMl, progressView);
                Toast.makeText(getContext(), "Added " + volume + " ml", Toast.LENGTH_SHORT).show();
                return true;
            });
            popup.show();
        });

        // Reset Button Listener
        resetButton.setOnClickListener(v -> {
            currentProgress = 0;
            intakes.clear();
            adapter.notifyDataSetChanged();
            updateProgress(textProgressMl, progressView);
            Toast.makeText(getContext(), "Progress reset", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateProgress(TextView textProgressMl, WaterProgressView progressView) {
        int currentMl = (int) (currentProgress * dailyGoal / 100);
        textProgressMl.setText(currentMl + "/" + dailyGoal + "ml");
        progressView.setProgress(currentProgress);
    }

    private void setupWaterIntakeScreen(View view) {
        // Keep existing logic or update as needed
    }

    private void setupHistoryScreen(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<WaterIntake> historyIntakes = new ArrayList<>();
        historyIntakes.add(new WaterIntake("2025-04-14", 500, 25)); // Sample data
        recyclerView.setAdapter(new WaterIntakeAdapter(historyIntakes));
    }

    private void setupAchievementsScreen(View view) {
        // Add achievements logic if needed
    }
}