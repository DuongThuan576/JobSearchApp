package com.example.jobsearchapp.data.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_jobs",
        foreignKeys = {
            @ForeignKey(entity = Job.class, parentColumns = "id", childColumns = "jobId", onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE)
        })
public class FavoriteJob {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int jobId;

    public FavoriteJob(int userId, int jobId) {
        this.userId = userId;
        this.jobId = jobId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }
}