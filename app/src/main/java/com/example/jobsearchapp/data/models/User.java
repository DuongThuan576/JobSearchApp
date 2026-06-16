package com.example.jobsearchapp.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "users")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String email;
    private String password;
    private String fullName;
    private String role; 
    private String phone;
    private String avatarPath;
    private String companyName; 
    private String cvPath; 
    
    // Các trường mới theo giao diện
    private String profession; // Ví dụ: Chuyên viên phát triển phần mềm
    private String location;   // Ví dụ: TP. Hồ Chí Minh
    private String skills;     // Lưu dạng chuỗi cách nhau bởi dấu phẩy
    private String experience; // Lưu thông tin kinh nghiệm làm việc

    public User() {}

    public User(String email, String password, String fullName, String role) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.profession = "Chuyên viên phát triển phần mềm";
        this.location = "TP. Hồ Chí Minh";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAvatarPath() { return avatarPath; }
    public void setAvatarPath(String avatarPath) { this.avatarPath = avatarPath; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCvPath() { return cvPath; }
    public void setCvPath(String cvPath) { this.cvPath = cvPath; }
    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
}