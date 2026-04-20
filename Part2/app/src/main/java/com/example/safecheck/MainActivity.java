package com.example.safecheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safecheck.ui.SafetyCheckAdapter;
import com.example.safecheck.viewmodel.SafetyListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private SafetyListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewChecks);
        TextView textEmpty = findViewById(R.id.textEmptyState);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddCheck);

        SafetyCheckAdapter adapter = new SafetyCheckAdapter(
                checkId -> {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("checkId", checkId);
                    startActivity(intent);
                },
                checkId -> {
                    viewModel.deleteCheck(checkId);
                    Snackbar.make(recyclerView, "Safety check deleted", Snackbar.LENGTH_SHORT).show();
                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SafetyListViewModel.class);
        viewModel.getCheckSummaries().observe(this, summaries -> {
            adapter.submitList(summaries);
            boolean isEmpty = summaries == null || summaries.isEmpty();
            textEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        });

        fabAdd.setOnClickListener(v -> startActivity(new Intent(this, AddSafetyCheckActivity.class)));
    }
}
