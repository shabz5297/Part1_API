package com.example.safecheck.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "defects",
    foreignKeys = @ForeignKey(
        entity = SafetyCheck.class,
        parentColumns = "checkId",
        childColumns = "checkId",
        onDelete = ForeignKey.CASCADE  // Deleting a SafetyCheck deletes all its Defects
    ),
    indices = {@Index("checkId")}      // Index for efficient foreign key lookups
)
public class Defect {

    @PrimaryKey(autoGenerate = true)
    public long defectId;

    public long checkId;           // Foreign key linking to SafetyCheck

    public String description;     // e.g. "Cracked Mirror"
    public String severity;        // "Low" or "High"

    public Defect() {}

    public Defect(long checkId, String description, String severity) {
        this.checkId = checkId;
        this.description = description;
        this.severity = severity;
    }
}
