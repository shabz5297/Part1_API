package com.example.safecheck.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "safety_checks")
public class SafetyCheck {
    @PrimaryKey(autoGenerate = true)
    public long checkId;

    public String date;
    public String vehicleRegistration;
    public String driverName;
    public String overallStatus;

    public SafetyCheck(String date, String vehicleRegistration, String driverName, String overallStatus) {
        this.date = date;
        this.vehicleRegistration = vehicleRegistration;
        this.driverName = driverName;
        this.overallStatus = overallStatus;
    }
}
