package com.example.jobsearchapp.ui.candidate.fragments;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jobsearchapp.R;
import com.example.jobsearchapp.data.local.AppDatabase;
import com.example.jobsearchapp.data.models.Category;
import com.example.jobsearchapp.data.models.Job;
import com.example.jobsearchapp.ui.activities.MainActivity;
import com.example.jobsearchapp.ui.base.BaseFragment;
import com.example.jobsearchapp.ui.candidate.adapters.CategoryAdapter;
import com.example.jobsearchapp.ui.candidate.adapters.JobAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private RecyclerView rvJobsMain, rvCategories;
    private ChipGroup cgTrending;
    private EditText edtSearch;
    private TextView tvViewAllCategories;

    @Override
    protected int getLayoutId() {
        return R.layout.candidate_fragment_home;
    }

    @Override
    protected void initViews(View view) {
        rvJobsMain = view.findViewById(R.id.rvJobsMain);
        rvCategories = view.findViewById(R.id.rvCategories);
        cgTrending = view.findViewById(R.id.cgTrending);
        edtSearch = view.findViewById(R.id.edtSearch);
        tvViewAllCategories = view.findViewById(R.id.tvViewAllCategories);
        
        setupCategories();
        setupRecyclerView();
        setupTrendingKeywords();
    }

    private void setupCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Công nghệ", android.R.drawable.ic_menu_today));
        categoryList.add(new Category("Tiếp thị", android.R.drawable.ic_menu_send));
        categoryList.add(new Category("Kinh doanh", android.R.drawable.ic_menu_agenda));
        categoryList.add(new Category("Thiết kế", android.R.drawable.ic_menu_edit));

        CategoryAdapter adapter = new CategoryAdapter(categoryList, category -> {
            // Khi nhấn vào một danh mục -> Chuyển sang Tìm kiếm với từ khóa đó
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToSearch(category.getName());
            }
        });
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(adapter);
    }

    private void setupTrendingKeywords() {
        String[] keywords = {"React Native", "Digital Marketing", "Python", "Project Management", "UI/UX Design"};
        cgTrending.removeAllViews();
        for (String keyword : keywords) {
            Chip chip = new Chip(getContext());
            chip.setText(keyword);
            chip.setChipBackgroundColorResource(R.color.white);
            chip.setChipStrokeWidth(1f);
            chip.setChipStrokeColorResource(R.color.gray_border);
            chip.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToSearch(keyword);
                }
            });
            cgTrending.addView(chip);
        }
    }

    private void setupRecyclerView() {
        List<Job> jobs = AppDatabase.getInstance(getContext()).jobDao().getAllJobs();
        JobAdapter adapter = new JobAdapter(jobs);
        rvJobsMain.setLayoutManager(new LinearLayoutManager(getContext()));
        rvJobsMain.setAdapter(adapter);
    }

    @Override
    protected void initListeners() {
        if (edtSearch != null) {
            edtSearch.setFocusable(false);
            edtSearch.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToSearch(null);
                }
            });
        }

        if (tvViewAllCategories != null) {
            tvViewAllCategories.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToSearch(null);
                }
            });
        }
    }
}