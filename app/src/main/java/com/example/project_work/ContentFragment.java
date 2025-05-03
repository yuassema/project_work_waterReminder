package com.example.project_work;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class ContentFragment extends Fragment implements WaterIntakeAdapter.OnItemActionListener {
    private static final String ARG_LAYOUT = "layout_id";
    private int dailyGoal = 0;
    private float currentProgress = 0;
    private List<WaterIntake> intakes = new ArrayList<>();
    private WaterIntakeAdapter adapter;
    private TextView textProgressMl;
    private WaterProgressView progressView;

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
        textProgressMl = view.findViewById(R.id.text_progress_ml);
        progressView = view.findViewById(R.id.water_progress_view);
        ImageButton addGlassButton = view.findViewById(R.id.btn_add_glass);
        ImageButton resetButton = view.findViewById(R.id.btn_reset);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_history_main);

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WaterIntakeAdapter(intakes, this);
        recyclerView.setAdapter(adapter);

        // Регистрация RecyclerView для Context Menu
        registerForContextMenu(recyclerView);

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
                updateProgress();
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

                updateProgress();
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
            updateProgress();
            Toast.makeText(getContext(), "Progress reset", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateProgress() {
        int currentMl = (int) (currentProgress * dailyGoal / 100);
        textProgressMl.setText(currentMl + "/" + dailyGoal + "ml");
        progressView.setProgress(currentProgress);
    }

    @Override
    public void onEdit(int position) {
        final WaterIntake[] intake = {intakes.get(position)};
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_edit_water);

        TextView title = dialog.findViewById(R.id.text_dialog_title);
        title.setText("Intake at " + intake[0].getTime());

        Button btn50ml = dialog.findViewById(R.id.btn_50ml);
        Button btn100ml = dialog.findViewById(R.id.btn_100ml);
        Button btn150ml = dialog.findViewById(R.id.btn_150ml);
        Button btn200ml = dialog.findViewById(R.id.btn_200ml);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnOk = dialog.findViewById(R.id.btn_ok);

        final int[] newVolume = {intake[0].getVolume()};
        btn50ml.setOnClickListener(v -> newVolume[0] = 50);
        btn100ml.setOnClickListener(v -> newVolume[0] = 100);
        btn150ml.setOnClickListener(v -> newVolume[0] = 150);
        btn200ml.setOnClickListener(v -> newVolume[0] = 200);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnOk.setOnClickListener(v -> {
            // Recalculate progress by removing the old volume and adding the new one
            int oldVolume = intake[0].getVolume();
            currentProgress -= (oldVolume * 100.0f) / dailyGoal;
            currentProgress += (newVolume[0] * 100.0f) / dailyGoal;
            if (currentProgress > 100) currentProgress = 100;
            if (currentProgress < 0) currentProgress = 0;

            // Update the intake entry
            intake[0] = new WaterIntake(intake[0].getTime(), newVolume[0], currentProgress);
            intakes.set(position, intake[0]);
            adapter.notifyItemChanged(position);

            // Update the progress bar
            updateProgress();

            Toast.makeText(getContext(), "Updated to " + newVolume[0] + " ml", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onDelete(int position) {
        WaterIntake intake = intakes.get(position);
        currentProgress -= (intake.getVolume() * 100.0f) / dailyGoal;
        if (currentProgress < 0) currentProgress = 0;

        intakes.remove(position);
        adapter.notifyItemRemoved(position);

        updateProgress();
        Toast.makeText(getContext(), "Entry deleted", Toast.LENGTH_SHORT).show();
    }



    private void setupWaterIntakeScreen(View view) {
        // Keep existing logic or update as needed
    }

    private void setupHistoryScreen(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<WaterIntake> historyIntakes = new ArrayList<>();
        historyIntakes.add(new WaterIntake("2025-04-14", 500, 25));
        recyclerView.setAdapter(new WaterIntakeAdapter(historyIntakes, this));
    }

    private void setupAchievementsScreen(View view) {
        // Add achievements logic if needed
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.recycler_history_main || v.getId() == R.id.recycler_history) {
            getActivity().getMenuInflater().inflate(R.menu.item_actions_menu, menu);
            menu.setHeaderTitle("Select Action");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Получаем позицию из тега элемента
        View view = item.getActionView();
        if (view != null) {
            Integer position = (Integer) view.getTag();
            if (position != null && position >= 0 && position < intakes.size()) {
                int id = item.getItemId();
                if (id == R.id.action_edit) {
                    onEdit(position);
                    return true;
                } else if (id == R.id.action_delete) {
                    onDelete(position);
                    return true;
                }
            }
        }
        return super.onContextItemSelected(item);
    }
}
