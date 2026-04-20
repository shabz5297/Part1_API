package com.example.safecheck.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.safecheck.model.SafetyCheck;
import com.example.safecheck.repository.SafetyRepository;

public class AddCheckViewModel extends AndroidViewModel {
    private final SafetyRepository repository;

    public AddCheckViewModel(@NonNull Application application) {
        super(application);
        repository = new SafetyRepository(application);
    }

    public void saveCheck(SafetyCheck safetyCheck, SafetyRepository.InsertCheckCallback callback) {
        repository.insertCheck(safetyCheck, callback);
    }
}
