package com.example.project_work;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WaterIntakeAdapter extends RecyclerView.Adapter<WaterIntakeAdapter.ViewHolder> {
    private List<WaterIntake> waterIntakes;

    public WaterIntakeAdapter(List<WaterIntake> waterIntakes) {
        this.waterIntakes = waterIntakes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_water_intake, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WaterIntake intake = waterIntakes.get(position);
        holder.dateText.setText(intake.getDate());
        holder.volumeText.setText(intake.getVolume() + " ml");
        holder.progressText.setText(String.format("%.0f%%", intake.getProgress()));
        holder.waterIcon.setImageResource(R.drawable.ic_water);
    }

    @Override
    public int getItemCount() {
        return waterIntakes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, volumeText, progressText;
        ImageView waterIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.text_date);
            volumeText = itemView.findViewById(R.id.text_volume);
            progressText = itemView.findViewById(R.id.text_progress);
            waterIcon = itemView.findViewById(R.id.image_water);
        }
    }
}