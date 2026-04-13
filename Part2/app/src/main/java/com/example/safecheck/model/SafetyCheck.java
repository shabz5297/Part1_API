package com.example.safecheck.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "safety_checks")
public class SafetyCheck {

    @PrimaryKey(autoGenerate = true)
    public long checkId;

    public String date;                  // e.g. "12/04/2025"
    public String vehicleRegistration;   // e.g. "AB12 CDE"
    public String driverName;
    public String overallStatus;         // "Pass" or "Fail"

    public SafetyCheck() {}

    public SafetyCheck(String date, String vehicleRegistration, String driverName, String overallStatus) {
        this.date = date;
        this.vehicleRegistration = vehicleRegistration;
        this.driverName = driverName;
        this.overallStatus = overallStatus;
    }
}
