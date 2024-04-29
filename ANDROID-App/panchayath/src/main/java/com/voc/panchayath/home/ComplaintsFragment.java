package com.voc.panchayath.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.voc.panchayath.R;
import com.voc.panchayath.adapter.ComplaintsAdapter;
import com.voc.panchayath.api.ApiInteractor;
import com.voc.panchayath.api.ApiResponseListener;
import com.voc.panchayath.base.BaseFragment;
import com.voc.panchayath.model.Complaint;
import com.voc.panchayath.model.ResponseWrapper;
import com.voc.panchayath.util.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class ComplaintsFragment extends BaseFragment implements ComplaintsAdapter.ClickListener {

    private static final String TAG = "HomeActivity";

    private RecyclerView rvComplaints;
    private ProgressBar progressBar;

    private ComplaintsAdapter adapter;
    private List<Complaint> complaintsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_complaints, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        progressBar = view.findViewById(R.id.progress_bar);
        rvComplaints = view.findViewById(R.id.recycler_view);
        adapter = new ComplaintsAdapter();
        rvComplaints.setAdapter(adapter);

        loadComplaints();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            loadComplaints();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadComplaints() {

        showProgress(true, rvComplaints, progressBar);

        int panchayathRid = MySharedPreferences.getPanchayathRid(requireActivity());

        ApiInteractor.getComplaintsList(
                panchayathRid,
                new ApiResponseListener<ResponseWrapper<List<Complaint>>>() {
                    @Override
                    public void onSuccess(ResponseWrapper<List<Complaint>> response) {
                        showProgress(false, rvComplaints, progressBar);
                        displayComplaints(response);
                    }

                    @Override
                    public void onError(Throwable err) {
                        showProgress(false, rvComplaints, progressBar);
                        longToast(err.getMessage());
                        Log.e(TAG, "onError: ", err);
                    }
                }
        );

    }

    private void displayComplaints(ResponseWrapper<List<Complaint>> response) {
        if (response != null) {
            if (response.isSuccess()) {

                if (response.getResponse().size() <= 0) {
                    shortToast("Complaints list is empty...");
                    return;
                }

                if (complaintsList.size() > 0) {
                    complaintsList.clear();
                }

                complaintsList.addAll(response.getResponse());
                adapter.addComplaintsList(complaintsList);

                adapter.addClickListener(ComplaintsFragment.this);

            } else {
                shortToast(response.getError());
            }
        }
    }

    @Override
    public void onClick(int position, Complaint complaint) {
        Intent intent = new Intent(requireActivity(), ComplaintsDetailsActivity.class);
        intent.putExtra("complaint", complaint);
        startActivityForResult(intent, 102);
    }
}
