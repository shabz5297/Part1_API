package com.example.safecheck.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.safecheck.model.SafetyCheck;

import java.util.List;

@Dao
public interface SafetyCheckDao {

    // Insert a new SafetyCheck, returns the generated checkId
    @Insert
    long insert(SafetyCheck safetyCheck);

    // Query all SafetyChecks, observed as LiveData so UI updates automatically
    @Query("SELECT * FROM safety_checks ORDER BY checkId DESC")
    LiveData<List<SafetyCheck>> getAllChecks();

    // Query a single SafetyCheck by its ID
    @Query("SELECT * FROM safety_checks WHERE checkId = :checkId")
    SafetyCheck getCheckById(long checkId);

    // Delete a specific SafetyCheck (cascades to Defects via @ForeignKey)
    @Delete
    void delete(SafetyCheck safetyCheck);

    // Count defects associated with a check — used for the RecyclerView row display
    @Query("SELECT COUNT(*) FROM defects WHERE checkId = :checkId")
    int getDefectCount(long checkId);
}
