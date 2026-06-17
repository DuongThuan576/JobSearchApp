package com.example.jobsearchapp.ui.activities;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobsearchapp.R;
import com.example.jobsearchapp.data.local.AppDatabase;
import com.example.jobsearchapp.data.models.User;
import com.example.jobsearchapp.ui.base.BaseActivity;
import com.example.jobsearchapp.utils.SessionManager;
import com.google.android.material.card.MaterialCardView;

public class EmployerActivity extends BaseActivity {

    private MaterialCardView cardPostJob, cardManageJob, cardApplicants;
    private ImageView ivLogout;
    private TextView tvWelcomeName;
    private SessionManager sessionManager;

    @Override
    protected int getLayoutId() {
        return R.layout.employer_activity;
    }

    @Override
    protected void initViews() {
        cardPostJob = findViewById(R.id.cardPostJob);
        cardManageJob = findViewById(R.id.cardManageJob);
        cardApplicants = findViewById(R.id.cardApplicants);
        ivLogout = findViewById(R.id.ivLogout);
        tvWelcomeName = findViewById(R.id.tvWelcomeName);

        sessionManager = new SessionManager(this);
        loadUserInfo();
    }

    private void loadUserInfo() {
        int userId = sessionManager.getUserId();
        if (userId != -1) {
            new Thread(() -> {
                User user = AppDatabase.getInstance(this).userDao().getUserById(userId);
                if (user != null) {
                    runOnUiThread(() -> tvWelcomeName.setText("Chào, " + user.getFullName() + "!"));
                }
            }).start();
        }
    }

    @Override
    protected void initListeners() {
        cardPostJob.setOnClickListener(v ->
                startActivity(new Intent(this, PostJobActivity.class)));

        cardManageJob.setOnClickListener(v ->
                startActivity(new Intent(this, ManageJobsActivity.class)));

        cardApplicants.setOnClickListener(v ->
                startActivity(new Intent(this, ViewApplicantsActivity.class)));

        ivLogout.setOnClickListener(v -> logout());
    }

    private void logout() {
        sessionManager.logout();
        Intent intent = new Intent(this, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
