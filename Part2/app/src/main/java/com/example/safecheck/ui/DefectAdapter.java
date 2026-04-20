package com.example.safecheck.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safecheck.R;
import com.example.safecheck.model.Defect;

import java.util.ArrayList;
import java.util.List;

public class DefectAdapter extends RecyclerView.Adapter<DefectAdapter.DefectViewHolder> {
    private final List<Defect> defects = new ArrayList<>();

    public List<Defect> getCurrentItems() {
        return new ArrayList<>(defects);
    }

    public void submitList(List<Defect> newItems) {
        defects.clear();
        if (newItems != null) {
            defects.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DefectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_defect, parent, false);
        return new DefectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefectViewHolder holder, int position) {
        Defect defect = defects.get(position);
        holder.description.setText(defect.description);
        holder.severity.setText("Severity: " + defect.severity);
    }

    @Override
    public int getItemCount() {
        return defects.size();
    }

    static class DefectViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        TextView severity;

        public DefectViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.textDefectDescription);
            severity = itemView.findViewById(R.id.textDefectSeverity);
        }
    }
}
