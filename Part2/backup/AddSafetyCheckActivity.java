package com.example.safecheck;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.safecheck.model.SafetyCheck;
import com.example.safecheck.viewmodel.AddCheckViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddSafetyCheckActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_safety_check);

        EditText editVehicle = findViewById(R.id.editVehicleRegistration);
        EditText editDriver = findViewById(R.id.editDriverName);
        AutoCompleteTextView dropdownStatus = findViewById(R.id.dropdownOverallStatus);
        Button buttonSave = findViewById(R.id.buttonSaveCheck);

        String[] statuses = {"Pass", "Fail"};
        dropdownStatus.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, statuses));
        dropdownStatus.setText("Pass", false);

        AddCheckViewModel viewModel = new ViewModelProvider(this).get(AddCheckViewModel.class);

        buttonSave.setOnClickListener(v -> {
            String vehicleReg = editVehicle.getText().toString().trim();
            String driverName = editDriver.getText().toString().trim();
            String overallStatus = dropdownStatus.getText().toString().trim();

            if (vehicleReg.isEmpty()) {
                Toast.makeText(this, "Please enter vehicle details", Toast.LENGTH_SHORT).show();
                return;
            }

            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(new Date());
            SafetyCheck safetyCheck = new SafetyCheck(currentDate, vehicleReg, driverName, overallStatus);
            viewModel.saveCheck(safetyCheck, newId -> runOnUiThread(this::finish));
        });
    }
}
