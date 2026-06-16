package com.example.jobsearchapp.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "jobs")
public class Job implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int employerId;
    private String title;
    private String salary;
    private String location;
    private String description;
    private String companyName;
    private String experience;
    private String type;
    private String deadline;
    private String requirements;
    private String benefits;
    private long timestamp;
    private boolean isFeatured;

    public Job() {}

    public Job(int employerId, String title, String salary, String location, String companyName, String type, boolean isFeatured) {
        this.employerId = employerId;
        this.title = title;
        this.salary = salary;
        this.location = location;
        this.companyName = companyName;
        this.type = type;
        this.isFeatured = isFeatured;
        this.timestamp = System.currentTimeMillis();
        this.experience = "1 - 3 năm";
        this.deadline = "30/12/2023";
        this.description = "Mô tả công việc...";
        this.requirements = "Yêu cầu...";
        this.benefits = "Quyền lợi...";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEmployerId() { return employerId; }
    public void setEmployerId(int employerId) { this.employerId = employerId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }
    public String getBenefits() { return benefits; }
    public void setBenefits(String benefits) { this.benefits = benefits; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }
}