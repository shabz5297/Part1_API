package com.example.safecheck.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.safecheck.model.SafetyCheck;
import com.example.safecheck.model.SafetyCheckSummary;
import com.example.safecheck.model.SafetyCheckWithDefects;

import java.util.List;

@Dao
public interface SafetyCheckDao {
    @Insert
    long insert(SafetyCheck safetyCheck);

    @Delete
    void delete(SafetyCheck safetyCheck);

    @Query("SELECT * FROM safety_checks WHERE checkId = :checkId LIMIT 1")
    SafetyCheck getCheckById(long checkId);

    @Query("SELECT sc.checkId, sc.date, sc.vehicleRegistration, COUNT(d.defectId) AS defectCount " +
            "FROM safety_checks sc LEFT JOIN defects d ON sc.checkId = d.checkId " +
            "GROUP BY sc.checkId, sc.date, sc.vehicleRegistration " +
            "ORDER BY sc.checkId DESC")
    LiveData<List<SafetyCheckSummary>> getAllCheckSummaries();

    @Transaction
    @Query("SELECT * FROM safety_checks WHERE checkId = :checkId LIMIT 1")
    LiveData<SafetyCheckWithDefects> getCheckWithDefects(long checkId);
}
