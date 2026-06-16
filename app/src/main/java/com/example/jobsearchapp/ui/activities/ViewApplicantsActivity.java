package com.example.jobsearchapp.ui.activities;

import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jobsearchapp.R;
import com.example.jobsearchapp.ui.base.BaseActivity;

public class ViewApplicantsActivity extends BaseActivity {

    private RecyclerView rvApplicants;

    @Override
    protected int getLayoutId() {
        return R.layout.employer_activity_view_applicants;
    }

    @Override
    protected void initViews() {
        rvApplicants = findViewById(R.id.rvApplicants);
        
        if (rvApplicants != null) {
            rvApplicants.setLayoutManager(new LinearLayoutManager(this));
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