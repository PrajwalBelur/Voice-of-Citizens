package com.voc.panchayath.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.voc.panchayath.R;
import com.voc.panchayath.model.Complaint;

import java.util.ArrayList;
import java.util.List;

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ComplaintsHolder> {

    private List<Complaint> complaintsList = new ArrayList<>();
    private ClickListener clickListener;

    public ComplaintsAdapter() {
    }

    public void addComplaintsList(List<Complaint> complaintsList) {
        if (this.complaintsList.size() > 0) {
            this.complaintsList.clear();
        }

        this.complaintsList.addAll(complaintsList);
        notifyDataSetChanged();
    }

    public void addClickListener(ClickListener clickListener) {
        if (clickListener == null)
            throw new RuntimeException("Click listener requires non null parameter");
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ComplaintsHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaints_row, parent, false);
        return new ComplaintsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintsHolder holder, int position) {
        Complaint comp = complaintsList.get(holder.getAdapterPosition());
        holder.tvType.setText(comp.getType());
        holder.tvLocation.setText(comp.getLocation());
        holder.tvDateTime.setText(comp.getDateTime());
        holder.tvAcceptanceStatus.setText(comp.getComplaintStatus());
        holder.tvViewMore.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(holder.getAdapterPosition(), comp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return complaintsList == null ? 0 : complaintsList.size();
    }

    static class ComplaintsHolder extends RecyclerView.ViewHolder {

        private TextView tvType, tvLocation, tvDateTime, tvAcceptanceStatus, tvViewMore;

        private ComplaintsHolder(@NonNull View itemView) {
            super(itemView);

            tvType = itemView.findViewById(R.id.tv_complaint_type);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvDateTime = itemView.findViewById(R.id.tv_complaint_date_time);
            tvAcceptanceStatus = itemView.findViewById(R.id.tv_complaint_status);
            tvViewMore = itemView.findViewById(R.id.tv_view_more_details);
        }
    }

    public interface ClickListener {
        void onClick(int position, Complaint complaint);
    }
}
