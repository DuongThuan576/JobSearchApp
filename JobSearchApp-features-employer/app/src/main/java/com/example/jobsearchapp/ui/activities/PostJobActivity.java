package com.example.jobsearchapp.ui.activities;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jobsearchapp.R;
import com.example.jobsearchapp.data.local.AppDatabase;
import com.example.jobsearchapp.data.models.Job;
import com.example.jobsearchapp.data.models.User;
import com.example.jobsearchapp.ui.base.BaseActivity;
import com.example.jobsearchapp.utils.SessionManager;

public class PostJobActivity extends BaseActivity {

    private EditText edtJobName, edtSalary, edtLocation, edtDescription;
    private Button btnSave;
    private ImageView ivBack;
    private SessionManager sessionManager;
    private User currentUser;

    @Override
    protected int getLayoutId() {
        return R.layout.employer_activity_post_job;
    }

    @Override
    protected void initViews() {
        edtJobName = findViewById(R.id.edtJobName);
        edtSalary = findViewById(R.id.edtSalary);
        edtLocation = findViewById(R.id.edtLocation);
        edtDescription = findViewById(R.id.edtDescription);
        btnSave = findViewById(R.id.btnSave);
        ivBack = findViewById(R.id.ivBack);

        sessionManager = new SessionManager(this);
        loadUserInfo();
    }

    private void loadUserInfo() {
        int userId = sessionManager.getUserId();
        if (userId != -1) {
            new Thread(() -> {
                currentUser = AppDatabase.getInstance(this).userDao().getUserById(userId);
            }).start();
        }
    }

    @Override
    protected void initListeners() {
        ivBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String jobTitle = edtJobName.getText().toString().trim();
            String salary = edtSalary.getText().toString().trim();
            String location = edtLocation.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();

            if (jobTitle.isEmpty() || salary.isEmpty() || location.isEmpty()) {
                showToast("Vui lòng điền đầy đủ các trường bắt buộc");
                return;
            }

            if (currentUser == null) {
                showToast("Lỗi xác thực người dùng");
                return;
            }

            Job job = new Job(
                    currentUser.getId(),
                    jobTitle,
                    salary,
                    location,
                    currentUser.getCompanyName() != null ? currentUser.getCompanyName() : "Công ty chưa cập nhật",
                    "Toàn thời gian",
                    false
            );
            job.setDescription(description);

            new Thread(() -> {
                AppDatabase.getInstance(this).jobDao().insertJob(job);
                runOnUiThread(() -> {
                    showToast("Đăng tin thành công!");
                    finish();
                });
            }).start();
        });
    }
}
