package com.example.searchjobs.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.searchjobs.R;
import com.example.searchjobs.clases.Jobs;

import java.util.List;

public class JobsAdapters extends RecyclerView.Adapter<JobsAdapters.ViewHolderJobs> {

    private List<Jobs> jobsList;
    private Context context;

    public JobsAdapters(List<Jobs> jobsList) {
        this.jobsList = jobsList;
    }

    @NonNull
    @Override
    public ViewHolderJobs onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_jobs, viewGroup, false);
        return new ViewHolderJobs(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderJobs viewHolderJobs, int i) {
        viewHolderJobs.title.setText(jobsList.get(i).getTitle());
        viewHolderJobs.company.setText(jobsList.get(i).getCompany());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolderJobs.description.setText(Html.fromHtml(jobsList.get(i).getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            viewHolderJobs.description.setText(Html.fromHtml(jobsList.get(i).getDescription()));
        }
        Glide.with(context).load(jobsList.get(i).getCompanyLogo()).error(R.drawable.error).placeholder(R.drawable.time).into(viewHolderJobs.imageCompany);
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public class ViewHolderJobs extends RecyclerView.ViewHolder {

        private TextView title, company, description;
        private ImageView imageCompany;

        public ViewHolderJobs(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textTitle);
            company = itemView.findViewById(R.id.textCompany);
            description = itemView.findViewById(R.id.textDescription);
            imageCompany = itemView.findViewById(R.id.imageCompany);
            context = itemView.getContext();
        }
    }
}
