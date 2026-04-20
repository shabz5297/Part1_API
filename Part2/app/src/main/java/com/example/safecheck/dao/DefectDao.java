package com.example.safecheck.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.safecheck.model.Defect;

@Dao
public interface DefectDao {
    @Insert
    void insert(Defect defect);
}
