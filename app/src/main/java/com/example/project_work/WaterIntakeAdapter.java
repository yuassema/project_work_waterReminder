package com.example.project_work;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WaterIntakeAdapter extends RecyclerView.Adapter<WaterIntakeAdapter.ViewHolder> {
    private List<WaterIntake> waterIntakes;
    private OnItemActionListener actionListener;

    public interface OnItemActionListener {
        void onEdit(int position);
        void onDelete(int position);
    }

    public WaterIntakeAdapter(List<WaterIntake> waterIntakes, OnItemActionListener listener) {
        this.waterIntakes = waterIntakes;
        this.actionListener = listener;
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
        holder.timeText.setText(intake.getTime());
        holder.volumeText.setText(intake.getVolume() + " ml");
        holder.progressText.setText(String.format("%.0f%%", intake.getProgress()));
        holder.waterIcon.setImageResource(R.drawable.water);

        // Обработка долгого нажатия для вызова Context Menu
        holder.itemView.setOnLongClickListener(v -> {
            // Сохраняем позицию в теге элемента
            holder.itemView.setTag(position);
            // Показываем контекстное меню
            v.showContextMenu();
            return true;
        });

        holder.moreButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), holder.moreButton);
            popup.getMenuInflater().inflate(R.menu.item_actions_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.action_edit) {
                    actionListener.onEdit(position);
                    return true;
                } else if (id == R.id.action_delete) {
                    actionListener.onDelete(position);
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return waterIntakes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeText, volumeText, progressText;
        ImageView waterIcon;
        ImageButton moreButton;

        public ViewHolder(View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.text_date);
            volumeText = itemView.findViewById(R.id.text_volume);
            progressText = itemView.findViewById(R.id.text_progress);
            waterIcon = itemView.findViewById(R.id.image_water);
            moreButton = itemView.findViewById(R.id.btn_more);
        }
    }
}