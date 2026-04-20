package com.example.safecheck.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.safecheck.model.SafetyCheckWithDefects;
import com.example.safecheck.repository.SafetyRepository;

public class DetailViewModel extends AndroidViewModel {
    private final SafetyRepository repository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        repository = new SafetyRepository(application);
    }

    public LiveData<SafetyCheckWithDefects> getCheckWithDefects(long checkId) {
        return repository.getCheckWithDefects(checkId);
    }
}
