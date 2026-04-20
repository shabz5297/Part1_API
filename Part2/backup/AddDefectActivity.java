package com.example.safecheck;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.safecheck.viewmodel.AddDefectViewModel;

public class AddDefectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_defect);

        long checkId = getIntent().getLongExtra("checkId", -1);
        EditText editDescription = findViewById(R.id.editDefectDescription);
        AutoCompleteTextView dropdownSeverity = findViewById(R.id.dropdownSeverity);
        Button buttonSave = findViewById(R.id.buttonSaveDefect);

        String[] severities = {"Low", "High"};
        dropdownSeverity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, severities));
        dropdownSeverity.setText("Low", false);

        AddDefectViewModel viewModel = new ViewModelProvider(this).get(AddDefectViewModel.class);
        viewModel.getDefectDescription().observe(this, value -> {
            if (!editDescription.getText().toString().equals(value)) {
                editDescription.setText(value);
                editDescription.setSelection(editDescription.getText().length());
            }
        });

        editDescription.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setDefectDescription(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        buttonSave.setOnClickListener(v -> {
            String description = editDescription.getText().toString().trim();
            String severity = dropdownSeverity.getText().toString().trim();

            if (description.isEmpty()) {
                Toast.makeText(this, "Please enter defect details", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.saveDefect(checkId, description, severity);
            finish();
        });
    }
}
