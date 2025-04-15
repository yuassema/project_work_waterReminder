package com.example.project_work;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

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

        return view;
    }
}