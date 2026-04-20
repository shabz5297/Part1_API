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
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("checkId")}
)
public class Defect {
    @PrimaryKey(autoGenerate = true)
    public long defectId;

    public long checkId;
    public String description;
    public String severity;

    public Defect(long checkId, String description, String severity) {
        this.checkId = checkId;
        this.description = description;
        this.severity = severity;
    }
}
