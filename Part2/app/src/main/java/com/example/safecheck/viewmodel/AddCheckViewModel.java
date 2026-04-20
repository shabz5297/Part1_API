package com.example.safecheck.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.safecheck.model.SafetyCheck;
import com.example.safecheck.repository.SafetyRepository;

public class AddCheckViewModel extends AndroidViewModel {
    private final SafetyRepository repository;
    private final MutableLiveData<String> vehicleRegistration = new MutableLiveData<>("");
    private final MutableLiveData<String> driverName = new MutableLiveData<>("");
    private final MutableLiveData<String> overallStatus = new MutableLiveData<>("Pass");

    public AddCheckViewModel(@NonNull Application application) {
        super(application);
        repository = new SafetyRepository(application);
    }

    public MutableLiveData<String> getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(String value) {
        vehicleRegistration.setValue(value);
    }

    public MutableLiveData<String> getDriverName() {
        return driverName;
    }

    public void setDriverName(String value) {
        driverName.setValue(value);
    }

    public MutableLiveData<String> getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(String value) {
        overallStatus.setValue(value);
    }

    public void saveCheck(SafetyCheck safetyCheck, SafetyRepository.InsertCheckCallback callback) {
        repository.insertCheck(safetyCheck, callback);
        vehicleRegistration.setValue("");
        driverName.setValue("");
        overallStatus.setValue("Pass");
    }
}
