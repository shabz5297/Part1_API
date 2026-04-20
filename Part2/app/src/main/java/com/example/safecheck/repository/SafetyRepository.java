package com.example.safecheck.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.safecheck.dao.DefectDao;
import com.example.safecheck.dao.SafetyCheckDao;
import com.example.safecheck.database.AppDatabase;
import com.example.safecheck.model.Defect;
import com.example.safecheck.model.SafetyCheck;
import com.example.safecheck.model.SafetyCheckSummary;
import com.example.safecheck.model.SafetyCheckWithDefects;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafetyRepository {
    private final SafetyCheckDao safetyCheckDao;
    private final DefectDao defectDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public SafetyRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        safetyCheckDao = database.safetyCheckDao();
        defectDao = database.defectDao();
    }

    public LiveData<List<SafetyCheckSummary>> getAllCheckSummaries() {
        return safetyCheckDao.getAllCheckSummaries();
    }

    public LiveData<SafetyCheckWithDefects> getCheckWithDefects(long checkId) {
        return safetyCheckDao.getCheckWithDefects(checkId);
    }

    public void insertCheck(SafetyCheck safetyCheck, InsertCheckCallback callback) {
        executorService.execute(() -> {
            long newId = safetyCheckDao.insert(safetyCheck);
            if (callback != null) {
                callback.onInserted(newId);
            }
        });
    }

    public void insertDefect(Defect defect) {
        executorService.execute(() -> defectDao.insert(defect));
    }

    public void deleteCheck(long checkId) {
        executorService.execute(() -> {
            SafetyCheck check = safetyCheckDao.getCheckById(checkId);
            if (check != null) {
                safetyCheckDao.delete(check);
            }
        });
    }

    public interface InsertCheckCallback {
        void onInserted(long newId);
    }
}
