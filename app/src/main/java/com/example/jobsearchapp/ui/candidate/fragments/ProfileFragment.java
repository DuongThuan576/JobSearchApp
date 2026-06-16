package com.example.jobsearchapp.ui.candidate.fragments;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import com.example.jobsearchapp.R;
import com.example.jobsearchapp.data.local.AppDatabase;
import com.example.jobsearchapp.data.models.User;
import com.example.jobsearchapp.ui.activities.AuthActivity;
import com.example.jobsearchapp.ui.activities.EditProfileActivity;
import com.example.jobsearchapp.ui.base.BaseFragment;
import com.example.jobsearchapp.utils.SessionManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class ProfileFragment extends BaseFragment {
    private TextView tvName, tvEmail, tvProfession, tvLocation, tvCvName;
    private LinearLayout layoutLoggedIn, layoutGuest, layoutCvItem;
    private ChipGroup cgProfileSkills;
    private SessionManager sessionManager;
    private User currentUser;

    private final ActivityResultLauncher<String> cvPickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> { if (uri != null) handleSelectedCV(uri); }
    );

    @Override
    protected int getLayoutId() { return R.layout.candidate_fragment_profile; }

    @Override
    protected void initViews(View view) {
        tvName = view.findViewById(R.id.tvProfileName);
        tvEmail = view.findViewById(R.id.tvProfileEmail);
        tvProfession = view.findViewById(R.id.tvProfileProfession);
        tvLocation = view.findViewById(R.id.tvProfileLocation);
        tvCvName = view.findViewById(R.id.tvCvName);
        layoutLoggedIn = view.findViewById(R.id.layout_logged_in);
        layoutGuest = view.findViewById(R.id.layout_guest);
        layoutCvItem = view.findViewById(R.id.layout_cv_item);
        cgProfileSkills = view.findViewById(R.id.cgProfileSkills);
        sessionManager = new SessionManager(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        checkLoginStatus(); // Nạp lại dữ liệu mỗi khi quay lại tab này
    }

    private void checkLoginStatus() {
        int userId = sessionManager.getUserId();
        if (userId != -1) {
            layoutLoggedIn.setVisibility(View.VISIBLE);
            layoutGuest.setVisibility(View.GONE);
            loadUserData(userId);
        } else {
            layoutLoggedIn.setVisibility(View.GONE);
            layoutGuest.setVisibility(View.VISIBLE);
        }
    }

    private void loadUserData(int userId) {
        currentUser = AppDatabase.getInstance(getContext()).userDao().getUserById(userId);
        if (currentUser != null) {
            tvName.setText(currentUser.getFullName());
            tvEmail.setText(currentUser.getEmail());
            tvProfession.setText(currentUser.getProfession());
            tvLocation.setText(currentUser.getLocation());
            
            if (currentUser.getCvPath() != null && !currentUser.getCvPath().isEmpty()) {
                layoutCvItem.setVisibility(View.VISIBLE);
                tvCvName.setText(currentUser.getCvPath());
            } else {
                layoutCvItem.setVisibility(View.GONE);
            }
            loadSkills(currentUser.getSkills());
        }
    }

    private void loadSkills(String skillsStr) {
        cgProfileSkills.removeAllViews();
        if (skillsStr != null && !skillsStr.isEmpty()) {
            for (String skill : skillsStr.split(",")) {
                if (skill.trim().isEmpty()) continue;
                Chip chip = new Chip(getContext());
                chip.setText(skill.trim());
                chip.setCloseIconVisible(true);
                chip.setOnCloseIconClickListener(v -> removeSkill(skill.trim()));
                cgProfileSkills.addView(chip);
            }
        }
    }

    private void handleSelectedCV(Uri uri) {
        if (currentUser == null) return;
        String fileName = uri.getLastPathSegment();
        currentUser.setCvPath(fileName);
        AppDatabase.getInstance(getContext()).userDao().updateProfile(currentUser);
        loadUserData(currentUser.getId());
        showToast("Đã tải lên CV thành công");
    }

    private void addSkill(String skill) {
        if (currentUser == null) return;
        String current = currentUser.getSkills();
        String updated = (current == null || current.isEmpty()) ? skill : current + "," + skill;
        currentUser.setSkills(updated);
        AppDatabase.getInstance(getContext()).userDao().updateProfile(currentUser);
        loadUserData(currentUser.getId());
    }

    private void removeSkill(String skill) {
        if (currentUser == null || currentUser.getSkills() == null) return;
        StringBuilder sb = new StringBuilder();
        for (String s : currentUser.getSkills().split(",")) {
            if (!s.trim().equals(skill)) {
                if (sb.length() > 0) sb.append(",");
                sb.append(s.trim());
            }
        }
        currentUser.setSkills(sb.toString());
        AppDatabase.getInstance(getContext()).userDao().updateProfile(currentUser);
        loadUserData(currentUser.getId());
    }

    @Override
    protected void initListeners() {
        if (getView() == null) return;
        getView().findViewById(R.id.btnGoToAuth).setOnClickListener(v -> startActivity(new Intent(getActivity(), AuthActivity.class)));
        getView().findViewById(R.id.btnUploadCV).setOnClickListener(v -> cvPickerLauncher.launch("*/*"));
        getView().findViewById(R.id.btnEditProfile).setOnClickListener(v -> startActivity(new Intent(getActivity(), EditProfileActivity.class)));
        getView().findViewById(R.id.btnLogout).setOnClickListener(v -> {
            sessionManager.logout();
            checkLoginStatus();
        });
        getView().findViewById(R.id.ivAddSkill).setOnClickListener(v -> {
            EditText input = new EditText(getContext());
            new AlertDialog.Builder(getContext()).setTitle("Thêm kỹ năng").setView(input)
                .setPositiveButton("Thêm", (d, w) -> {
                    String s = input.getText().toString().trim();
                    if (!s.isEmpty()) addSkill(s);
                }).setNegativeButton("Hủy", null).show();
        });
    }
}