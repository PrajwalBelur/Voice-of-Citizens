package com.voc.home;

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

import com.voc.R;
import com.voc.adapter.ComplaintsAdapter;
import com.voc.api.ApiInteractor;
import com.voc.api.ApiResponseListener;
import com.voc.base.BaseFragment;
import com.voc.model.Complaint;
import com.voc.model.ResponseWrapper;
import com.voc.util.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class TrackComplaintsFragment extends BaseFragment {

    private static final String TAG = TrackComplaintsFragment.class.getSimpleName();

    private RecyclerView rvComplaints;
    private ProgressBar progressBar;

    private List<Complaint> complaintsList = new ArrayList<>();
    private ComplaintsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_complaints, container, false);

        setHasOptionsMenu(true);

        progressBar = view.findViewById(R.id.progress_bar);
        rvComplaints = view.findViewById(R.id.recycler_view);
        rvComplaints.setVisibility(View.GONE);

        adapter = new ComplaintsAdapter();
        rvComplaints.setAdapter(adapter);

        return view;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadComplaints();
    }

    private void loadComplaints() {

        showProgress(true, rvComplaints, progressBar);

        int citizenRid = MySharedPreferences.getCitizenRid(getActivity());

        ApiInteractor.getComplaintsList(
                citizenRid,
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

                adapter.addClickListener((position, complaint) -> {
                    Intent intent = new Intent(getActivity(), ComplaintsDetailsActivity.class);
                    intent.putExtra("complaint", complaint);
                    startActivityForResult(intent, 102);
                });

            } else {
                shortToast(response.getError());
            }
        }
    }
}
