package com.example.safecheck.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.safecheck.model.Defect;
import com.example.safecheck.repository.SafetyRepository;

public class AddDefectViewModel extends AndroidViewModel {
    private final SafetyRepository repository;
    private final MutableLiveData<String> defectDescription = new MutableLiveData<>("");
    private final MutableLiveData<String> defectSeverity = new MutableLiveData<>("Low");

    public AddDefectViewModel(@NonNull Application application) {
        super(application);
        repository = new SafetyRepository(application);
    }

    public MutableLiveData<String> getDefectDescription() {
        return defectDescription;
    }

    public void setDefectDescription(String value) {
        defectDescription.setValue(value);
    }

    public MutableLiveData<String> getDefectSeverity() {
        return defectSeverity;
    }

    public void setDefectSeverity(String value) {
        defectSeverity.setValue(value);
    }

    public void saveDefect(long checkId, String description, String severity) {
        repository.insertDefect(new Defect(checkId, description, severity));
        defectDescription.setValue("");
        defectSeverity.setValue("Low");
    }
}
