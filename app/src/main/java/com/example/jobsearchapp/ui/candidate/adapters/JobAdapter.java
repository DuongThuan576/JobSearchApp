package com.example.jobsearchapp.ui.candidate.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jobsearchapp.R;
import com.example.jobsearchapp.data.models.Job;
import com.example.jobsearchapp.ui.activities.JobDetailActivity;
import java.util.ArrayList;
import java.util.List;

import java.util.Collections;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    // Hàm sắp xếp
    public void sort(boolean newestFirst) {
        Collections.sort(jobList, (j1, j2) -> {
            if (newestFirst) {
                return Long.compare(j2.getTimestamp(), j1.getTimestamp());
            } else {
                return Long.compare(j1.getTimestamp(), j2.getTimestamp());
            }
        });
        notifyDataSetChanged();
    }

    private static final int TYPE_REGULAR = 0;
    private static final int TYPE_FEATURED = 1;

    private List<Job> jobList;
    private List<Job> jobListFull;

    public JobAdapter(List<Job> jobList) {
        this.jobList = jobList;
        this.jobListFull = new ArrayList<>(jobList);
    }

    @Override
    public int getItemViewType(int position) {
        return jobList.get(position).isFeatured() ? TYPE_FEATURED : TYPE_REGULAR;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = (viewType == TYPE_FEATURED) ? R.layout.candidate_item_job_featured : R.layout.candidate_item_job;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        
        // Kiểm tra an toàn trước khi gán dữ liệu để tránh NullPointerException
        if (holder.tvTitle != null) holder.tvTitle.setText(job.getTitle());
        if (holder.tvSalary != null) holder.tvSalary.setText(job.getSalary());
        if (holder.tvLocation != null) holder.tvLocation.setText(job.getLocation());
        if (holder.tvCompanyName != null) holder.tvCompanyName.setText(job.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), JobDetailActivity.class);
            intent.putExtra("JOB_DATA", job);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return jobList != null ? jobList.size() : 0;
    }

    public void filter(String query, String typeFilter) {
        jobList.clear();
        String finalQuery = query.toLowerCase().trim();
        for (Job item : jobListFull) {
            boolean matchesQuery = finalQuery.isEmpty() || 
                                 item.getTitle().toLowerCase().contains(finalQuery) || 
                                 item.getDescription().toLowerCase().contains(finalQuery);
            boolean matchesType = typeFilter == null || item.getType().equals(typeFilter);
            if (matchesQuery && matchesType) {
                jobList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSalary, tvLocation, tvCompanyName;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvJobTitle);
            tvSalary = itemView.findViewById(R.id.tvJobSalary);
            tvLocation = itemView.findViewById(R.id.tvJobLocation);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
        }
    }
}