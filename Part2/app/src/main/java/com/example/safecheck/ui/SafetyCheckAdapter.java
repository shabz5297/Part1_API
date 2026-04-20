package com.example.safecheck.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safecheck.R;
import com.example.safecheck.model.SafetyCheckSummary;

import java.util.ArrayList;
import java.util.List;

public class SafetyCheckAdapter extends RecyclerView.Adapter<SafetyCheckAdapter.SafetyCheckViewHolder> {
    private final List<SafetyCheckSummary> items = new ArrayList<>();
    private final OnCheckClickListener clickListener;
    private final OnDeleteClickListener deleteClickListener;

    public SafetyCheckAdapter(OnCheckClickListener clickListener, OnDeleteClickListener deleteClickListener) {
        this.clickListener = clickListener;
        this.deleteClickListener = deleteClickListener;
    }

    public void submitList(List<SafetyCheckSummary> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SafetyCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_safety_check, parent, false);
        return new SafetyCheckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SafetyCheckViewHolder holder, int position) {
        SafetyCheckSummary item = items.get(position);
        holder.rowText.setText(item.date + " - " + item.vehicleRegistration + " - " + item.defectCount + " Defects");
        holder.itemView.setOnClickListener(v -> clickListener.onCheckClicked(item.checkId));
        holder.deleteButton.setOnClickListener(v -> deleteClickListener.onDeleteClicked(item.checkId));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class SafetyCheckViewHolder extends RecyclerView.ViewHolder {
        TextView rowText;
        ImageButton deleteButton;

        public SafetyCheckViewHolder(@NonNull View itemView) {
            super(itemView);
            rowText = itemView.findViewById(R.id.textSummary);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }

    public interface OnCheckClickListener {
        void onCheckClicked(long checkId);
    }

    public interface OnDeleteClickListener {
        void onDeleteClicked(long checkId);
    }
}
