package com.example.safecheck;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safecheck.model.Defect;
import com.example.safecheck.model.SafetyCheckWithDefects;
import com.example.safecheck.ui.DefectAdapter;
import com.example.safecheck.viewmodel.DetailViewModel;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private long checkId;
    private String vehicleReg = "";
    private DefectAdapter defectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        checkId = getIntent().getLongExtra("checkId", -1);

        TextView textDate = findViewById(R.id.textDate);
        TextView textVehicle = findViewById(R.id.textVehicle);
        TextView textDriver = findViewById(R.id.textDriver);
        TextView textStatus = findViewById(R.id.textStatus);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDefects);
        Button buttonAddDefect = findViewById(R.id.buttonAddDefect);
        Button buttonEmailReport = findViewById(R.id.buttonEmailReport);

        defectAdapter = new DefectAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(defectAdapter);

        DetailViewModel viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        viewModel.getCheckWithDefects(checkId).observe(this, result -> bindResult(result, textDate, textVehicle, textDriver, textStatus));

        buttonAddDefect.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddDefectActivity.class);
            intent.putExtra("checkId", checkId);
            startActivity(intent);
        });

        buttonEmailReport.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(android.net.Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Safety Defect Report: " + vehicleReg);
            emailIntent.putExtra(Intent.EXTRA_TEXT, buildEmailBody());
            startActivity(Intent.createChooser(emailIntent, "Send email using"));
        });
    }

    private void bindResult(SafetyCheckWithDefects result,
                            TextView textDate,
                            TextView textVehicle,
                            TextView textDriver,
                            TextView textStatus) {
        if (result == null || result.safetyCheck == null) {
            finish();
            return;
        }

        textDate.setText("Date: " + result.safetyCheck.date);
        textVehicle.setText("Vehicle: " + result.safetyCheck.vehicleRegistration);
        textDriver.setText("Driver: " + result.safetyCheck.driverName);
        textStatus.setText("Overall status: " + result.safetyCheck.overallStatus);
        vehicleReg = result.safetyCheck.vehicleRegistration;
        defectAdapter.submitList(result.defects);
    }

    private String buildEmailBody() {
        StringBuilder body = new StringBuilder();
        body.append("Please review the following defects for vehicle ")
                .append(vehicleReg)
                .append(":\n\n");

        List<Defect> currentDefects = defectAdapter.getCurrentItems();
        if (currentDefects.isEmpty()) {
            body.append("- No defects recorded\n");
        } else {
            for (Defect defect : currentDefects) {
                body.append("- ")
                        .append(defect.description)
                        .append(" (")
                        .append(defect.severity)
                        .append(")\n");
            }
        }
        return body.toString();
    }

}
