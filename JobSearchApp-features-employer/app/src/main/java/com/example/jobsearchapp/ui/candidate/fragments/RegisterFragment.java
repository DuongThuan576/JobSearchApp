package com.example.jobsearchapp.ui.candidate.fragments;

import android.view.View;
import android.widget.EditText;
import com.example.jobsearchapp.R;
import com.example.jobsearchapp.data.local.AppDatabase;
import com.example.jobsearchapp.data.models.User;
import com.example.jobsearchapp.ui.base.BaseFragment;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class RegisterFragment extends BaseFragment {
    private EditText edtEmail, edtPassword, edtFullName, edtPhone;
    private MaterialButtonToggleGroup toggleGroupRole;

    @Override
    protected int getLayoutId() { return R.layout.fragment_register; }

    @Override
    protected void initViews(View view) {
        edtFullName = view.findViewById(R.id.edtFullName);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        toggleGroupRole = view.findViewById(R.id.toggleGroupRole);
    }

    @Override
    protected void initListeners() {
        if (getView() == null) return;

        getView().findViewById(R.id.btnRegister).setOnClickListener(v -> {
            String fullName = edtFullName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();
            
            if (fullName.isEmpty() || phone.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                showToast("Vui lòng điền đầy đủ thông tin");
                return;
            }

            String role = "CANDIDATE";
            if (toggleGroupRole.getCheckedButtonId() == R.id.btnRoleEmployer) {
                role = "EMPLOYER";
            }

            try {
                User user = new User(email, pass, fullName, role);
                user.setPhone(phone);
                
                AppDatabase.getInstance(getContext()).userDao().register(user);
                showToast("Đăng ký thành công! Hãy đăng nhập.");
                
                // Quay lại màn hình đăng nhập
                if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                    getParentFragmentManager().popBackStack();
                }
            } catch (Exception e) {
                showToast("Lỗi đăng ký: " + e.getMessage());
            }
        });

        getView().findViewById(R.id.tvToLogin).setOnClickListener(v -> {
            if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                getParentFragmentManager().popBackStack();
            }
        });
    }
}