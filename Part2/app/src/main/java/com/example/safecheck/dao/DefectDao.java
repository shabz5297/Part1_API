package com.example.safecheck.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.safecheck.model.Defect;

import java.util.List;

@Dao
public interface DefectDao {

    // Insert a new Defect linked to a SafetyCheck
    @Insert
    void insert(Defect defect);

    // Retrieve all Defects belonging to a specific SafetyCheck
    @Query("SELECT * FROM defects WHERE checkId = :checkId")
    List<Defect> getDefectsForCheck(long checkId);

    // Delete all defects for a check — used as a manual fallback if CASCADE is not sufficient
    @Query("DELETE FROM defects WHERE checkId = :checkId")
    void deleteDefectsForCheck(long checkId);
}
