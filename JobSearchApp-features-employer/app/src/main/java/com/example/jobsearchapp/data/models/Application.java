package com.example.jobsearchapp.data.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "applications",
        foreignKeys = {
            @ForeignKey(entity = Job.class, parentColumns = "id", childColumns = "jobId", onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "candidateId", onDelete = ForeignKey.CASCADE)
        })
public class Application implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int jobId;
    private int candidateId;
    private long appliedAt;
    private String status; // Pending, Accepted, Rejected

    public Application() {}

    public Application(int jobId, int candidateId) {
        this.jobId = jobId;
        this.candidateId = candidateId;
        this.appliedAt = System.currentTimeMillis();
        this.status = "Pending";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }
    public int getCandidateId() { return candidateId; }
    public void setCandidateId(int candidateId) { this.candidateId = candidateId; }
    public long getAppliedAt() { return appliedAt; }
    public void setAppliedAt(long appliedAt) { this.appliedAt = appliedAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}