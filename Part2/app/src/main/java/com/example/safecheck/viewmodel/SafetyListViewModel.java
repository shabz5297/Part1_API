package com.example.safecheck.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.safecheck.model.SafetyCheckSummary;
import com.example.safecheck.repository.SafetyRepository;

import java.util.List;

public class SafetyListViewModel extends AndroidViewModel {
    private final SafetyRepository repository;
    private final LiveData<List<SafetyCheckSummary>> checkSummaries;

    public SafetyListViewModel(@NonNull Application application) {
        super(application);
        repository = new SafetyRepository(application);
        checkSummaries = repository.getAllCheckSummaries();
    }

    public LiveData<List<SafetyCheckSummary>> getCheckSummaries() {
        return checkSummaries;
    }

    public void deleteCheck(long checkId) {
        repository.deleteCheck(checkId);
    }
}
