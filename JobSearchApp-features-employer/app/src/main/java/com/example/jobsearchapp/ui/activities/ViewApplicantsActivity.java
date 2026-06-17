package com.example.jobsearchapp.ui.activities;

import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jobsearchapp.R;
import com.example.jobsearchapp.data.local.AppDatabase;
import com.example.jobsearchapp.data.models.Applicant;
import com.example.jobsearchapp.data.models.Application;
import com.example.jobsearchapp.data.models.Job;
import com.example.jobsearchapp.data.models.User;
import com.example.jobsearchapp.ui.base.BaseActivity;
import com.example.jobsearchapp.ui.employer.adapters.ApplicantAdapter;
import com.example.jobsearchapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ViewApplicantsActivity extends BaseActivity implements ApplicantAdapter.OnApplicantActionListener {

    private RecyclerView rvApplicants;
    private ImageView ivBack;
    private ApplicantAdapter adapter;
    private List<Applicant> applicantList = new ArrayList<>();
    private SessionManager sessionManager;

    @Override
    protected int getLayoutId() {
        return R.layout.employer_activity_view_applicants;
    }

    @Override
    protected void initViews() {
        rvApplicants = findViewById(R.id.rvApplicants);
        ivBack = findViewById(R.id.ivBack);

        sessionManager = new SessionManager(this);

        rvApplicants.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApplicantAdapter(applicantList, this);
        rvApplicants.setAdapter(adapter);

        loadApplicants();
    }

    private void loadApplicants() {
        int employerId = sessionManager.getUserId();
        if (employerId != -1) {
            new Thread(() -> {
                List<Application> applications = AppDatabase.getInstance(this).applicationDao().getApplicationsForEmployer(employerId);
                List<Applicant> applicants = new ArrayList<>();

                for (Application app : applications) {
                    User user = AppDatabase.getInstance(this).userDao().getUserById(app.getCandidateId());
                    Job job = AppDatabase.getInstance(this).jobDao().getJobById(app.getJobId());

                    if (user != null) {
                        Applicant applicant = new Applicant();
                        applicant.setApplicationId(app.getId());
                        applicant.setId(String.valueOf(user.getId()));
                        applicant.setName(user.getFullName());
                        applicant.setEmail(user.getEmail());
                        applicant.setJobTitle(job != null ? job.getTitle() : "N/A");
                        applicant.setStatus(app.getStatus());
                        applicants.add(applicant);
                    }
                }

                runOnUiThread(() -> {
                    applicantList = applicants;
                    adapter.updateList(applicantList);
                });
            }).start();
        }
    }

    @Override
    protected void initListeners() {
        ivBack.setOnClickListener(v -> finish());
    }

    @Override
    public void onAccept(Applicant applicant) {
        updateApplicationStatus(applicant.getApplicationId(), "Accepted");
    }

    @Override
    public void onReject(Applicant applicant) {
        updateApplicationStatus(applicant.getApplicationId(), "Rejected");
    }

    private void updateApplicationStatus(int applicationId, String status) {
        new Thread(() -> {
            AppDatabase.getInstance(this).applicationDao().updateStatusById(applicationId, status);

            runOnUiThread(() -> {
                showToast("Đã " + (status.equals("Accepted") ? "chấp nhận" : "từ chối") + " đơn ứng tuyển");
                loadApplicants();
            });
        }).start();
    }
}
