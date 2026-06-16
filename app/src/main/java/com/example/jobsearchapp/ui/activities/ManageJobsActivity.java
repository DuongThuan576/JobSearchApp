package com.example.jobsearchapp.ui.activities;

import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jobsearchapp.R;

import com.example.jobsearchapp.ui.base.BaseActivity;

import android.view.View;

public class ManageJobsActivity extends BaseActivity {

    private RecyclerView rvJobs;
    private Button btnBack;

    @Override
    protected int getLayoutId() {
        return R.layout.employer_activity_manage_jobs;
    }

    @Override
    protected void initViews() {
        rvJobs = findViewById(R.id.rvJobs);
        btnBack = findViewById(R.id.btnBack);
        
        if (rvJobs != null) {
            rvJobs.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    protected void initListeners() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}