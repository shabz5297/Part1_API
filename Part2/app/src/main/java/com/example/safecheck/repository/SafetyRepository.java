package com.example.safecheck.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.safecheck.dao.DefectDao;
import com.example.safecheck.dao.SafetyCheckDao;
import com.example.safecheck.database.AppDatabase;
import com.example.safecheck.model.Defect;
import com.example.safecheck.model.SafetyCheck;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafetyRepository {

    private final SafetyCheckDao safetyCheckDao;
    private final DefectDao defectDao;

    // ExecutorService runs database writes on a background thread
    // Using a single-thread executor ensures writes are sequential and thread-safe
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public SafetyRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        safetyCheckDao = db.safetyCheckDao();
        defectDao = db.defectDao();
    }

    // --- SafetyCheck operations ---

    // INSERT: runs on background thread via ExecutorService
    public void insertCheck(SafetyCheck safetyCheck) {
        executorService.execute(() -> safetyCheckDao.insert(safetyCheck));
    }

    // INSERT check and return its generated ID (needed to link Defects)
    // Uses a callback interface since we cannot return values from background threads directly
    public void insertCheckWithCallback(SafetyCheck safetyCheck, InsertCallback callback) {
        executorService.execute(() -> {
            long newId = safetyCheckDao.insert(safetyCheck);
            callback.onInserted(newId);
        });
    }

    // READ: returns LiveData — Room handles this on a background thread automatically
    public LiveData<List<SafetyCheck>> getAllChecks() {
        return safetyCheckDao.getAllChecks();
    }

    // READ single check by ID (call from background thread or AsyncTask if needed)
    public SafetyCheck getCheckById(long checkId) {
        return safetyCheckDao.getCheckById(checkId);
    }

    // DELETE: runs on background thread via ExecutorService
    public void deleteCheck(SafetyCheck safetyCheck) {
        executorService.execute(() -> safetyCheckDao.delete(safetyCheck));
    }

    // GET defect count for a check (for RecyclerView row display)
    public int getDefectCount(long checkId) {
        return safetyCheckDao.getDefectCount(checkId);
    }

    // --- Defect operations ---

    // INSERT defect: runs on background thread via ExecutorService
    public void insertDefect(Defect defect) {
        executorService.execute(() -> defectDao.insert(defect));
    }

    // READ defects for a check (call from background thread)
    public List<Defect> getDefectsForCheck(long checkId) {
        return defectDao.getDefectsForCheck(checkId);
    }

    // Callback interface for insert operations that need the generated ID
    public interface InsertCallback {
        void onInserted(long newId);
    }
}
