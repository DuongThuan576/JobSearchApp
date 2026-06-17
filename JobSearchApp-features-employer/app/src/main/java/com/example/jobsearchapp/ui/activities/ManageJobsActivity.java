package com.example.jobsearchapp.ui.activities;

import android.content.Intent;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jobsearchapp.R;
import com.example.jobsearchapp.data.local.AppDatabase;
import com.example.jobsearchapp.data.models.Job;
import com.example.jobsearchapp.ui.base.BaseActivity;
import com.example.jobsearchapp.ui.employer.adapters.ManageJobAdapter;
import com.example.jobsearchapp.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManageJobsActivity extends BaseActivity implements ManageJobAdapter.OnJobActionListener {

    private RecyclerView rvJobs;
    private ImageView ivBack;
    private FloatingActionButton fabAddJob;
    private ManageJobAdapter adapter;
    private List<Job> jobList = new ArrayList<>();
    private SessionManager sessionManager;

    @Override
    protected int getLayoutId() {
        return R.layout.employer_activity_manage_jobs;
    }

    @Override
    protected void initViews() {
        rvJobs = findViewById(R.id.rvJobs);
        ivBack = findViewById(R.id.ivBack);
        fabAddJob = findViewById(R.id.fabAddJob);

        sessionManager = new SessionManager(this);

        rvJobs.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ManageJobAdapter(jobList, this);
        rvJobs.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadJobs();
    }

    private void loadJobs() {
        int employerId = sessionManager.getUserId();
        if (employerId != -1) {
            new Thread(() -> {
                List<Job> jobs = AppDatabase.getInstance(this).jobDao().getJobsByEmployer(employerId);
                runOnUiThread(() -> {
                    jobList = jobs;
                    adapter.updateList(jobList);
                });
            }).start();
        }
    }

    @Override
    protected void initListeners() {
        ivBack.setOnClickListener(v -> finish());
        fabAddJob.setOnClickListener(v -> startActivity(new Intent(this, PostJobActivity.class)));
    }

    @Override
    public void onEdit(Job job) {
        // Có thể mở rộng thêm tính năng sửa
        showToast("Chức năng sửa đang phát triển");
    }

    @Override
    public void onDelete(Job job) {
        new Thread(() -> {
            AppDatabase.getInstance(this).jobDao().deleteJob(job);
            runOnUiThread(() -> {
                showToast("Đã xóa tin tuyển dụng");
                loadJobs();
            });
        }).start();
    }
}
