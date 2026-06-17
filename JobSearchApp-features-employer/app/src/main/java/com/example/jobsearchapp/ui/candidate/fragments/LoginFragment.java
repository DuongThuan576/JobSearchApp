package com.example.jobsearchapp.ui.candidate.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.example.jobsearchapp.R;
import com.example.jobsearchapp.data.local.AppDatabase;
import com.example.jobsearchapp.data.models.User;
import com.example.jobsearchapp.ui.activities.EmployerActivity;
import com.example.jobsearchapp.ui.activities.MainActivity;
import com.example.jobsearchapp.ui.base.BaseFragment;
import com.example.jobsearchapp.utils.SessionManager;

public class LoginFragment extends BaseFragment {
    private EditText edtEmail, edtPassword;

    @Override
    protected int getLayoutId() { return R.layout.fragment_login; }

    @Override
    protected void initViews(View view) {
        edtEmail = view.findViewById(R.id.edtEmailLogin);
        edtPassword = view.findViewById(R.id.edtPasswordLogin);
    }

    @Override
    protected void initListeners() {
        if (getView() == null) return;

        getView().findViewById(R.id.btnLogin).setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                showToast("Vui lòng nhập email và mật khẩu");
                return;
            }

            // Lấy user từ DB
            User user = AppDatabase.getInstance(getContext()).userDao().login(email, pass);
            if (user != null) {
                // LƯU ID THẬT CỦA USER VÀO SESSION
                new SessionManager(getContext()).saveSession(user.getId(), user.getRole());

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                if (getActivity() != null) getActivity().finish();
            } else {
                showToast("Sai email hoặc mật khẩu");
            }
        });

        getView().findViewById(R.id.tvToRegister).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.auth_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}