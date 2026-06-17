package com.example.jobsearchapp.ui.activities;

import android.widget.EditText;
import com.example.jobsearchapp.R;
import com.example.jobsearchapp.data.local.AppDatabase;
import com.example.jobsearchapp.data.models.User;
import com.example.jobsearchapp.ui.base.BaseActivity;
import com.example.jobsearchapp.utils.SessionManager;

public class EditProfileActivity extends BaseActivity {
    private EditText edtFullName, edtPhone, edtCompany, edtProfession, edtLocation;
    private User currentUser;

    @Override
    protected int getLayoutId() { return R.layout.candidate_activity_edit_profile; }

    @Override
    protected void initViews() {
        edtFullName = findViewById(R.id.edtEditFullName);
        edtPhone = findViewById(R.id.edtEditPhone);
        edtCompany = findViewById(R.id.edtEditCompany);
        edtProfession = findViewById(R.id.edtEditProfession);
        edtLocation = findViewById(R.id.edtEditLocation);
        
        SessionManager sessionManager = new SessionManager(this);
        int userId = sessionManager.getUserId();
        
        // Lấy thông tin người dùng từ DB để điền vào các ô
        currentUser = AppDatabase.getInstance(this).userDao().getUserById(userId);
        if (currentUser != null) {
            edtFullName.setText(currentUser.getFullName());
            edtPhone.setText(currentUser.getPhone());
            edtCompany.setText(currentUser.getCompanyName());
            edtProfession.setText(currentUser.getProfession());
            edtLocation.setText(currentUser.getLocation());
        }
    }

    @Override
    protected void initListeners() {
        findViewById(R.id.ivBackEdit).setOnClickListener(v -> finish());
        
        findViewById(R.id.btnSaveProfile).setOnClickListener(v -> {
            if (currentUser != null) {
                // Lấy dữ liệu mới từ giao diện
                currentUser.setFullName(edtFullName.getText().toString().trim());
                currentUser.setPhone(edtPhone.getText().toString().trim());
                currentUser.setCompanyName(edtCompany.getText().toString().trim());
                currentUser.setProfession(edtProfession.getText().toString().trim());
                currentUser.setLocation(edtLocation.getText().toString().trim());
                
                // Cập nhật vào Database
                AppDatabase.getInstance(this).userDao().updateProfile(currentUser);
                showToast("Cập nhật thành công");
                
                // Đóng Activity ngay lập tức để quay về trang trước
                finish(); 
            } else {
                showToast("Lỗi: Không tìm thấy tài khoản để cập nhật");
            }
        });
    }
}