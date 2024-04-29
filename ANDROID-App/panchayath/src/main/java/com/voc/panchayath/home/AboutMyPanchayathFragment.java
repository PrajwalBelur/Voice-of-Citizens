package com.voc.panchayath.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.voc.panchayath.R;
import com.voc.panchayath.api.ApiInteractor;
import com.voc.panchayath.api.ApiResponseListener;
import com.voc.panchayath.base.BaseFragment;
import com.voc.panchayath.model.Panchayath;
import com.voc.panchayath.model.ResponseWrapper;
import com.voc.panchayath.util.MySharedPreferences;

public class AboutMyPanchayathFragment extends BaseFragment {

    private static final String TAG = AboutMyPanchayathFragment.class.getSimpleName();

    private TextView tvGrama, tvPresident, tvPresidentContact, tvTelephone, tvEmail, tvDescription;
    private CardView cardPanchayathDetails;
    private ProgressBar progressBar;

    private Panchayath panchayath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_panchayath, container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        cardPanchayathDetails = view.findViewById(R.id.card_panchayath_details);
        cardPanchayathDetails.setVisibility(View.GONE);
        tvGrama = view.findViewById(R.id.tv_grama);
        tvPresident = view.findViewById(R.id.tv_president_name);
        tvPresidentContact = view.findViewById(R.id.tv_president_contact);
        tvTelephone = view.findViewById(R.id.tv_telephone);
        tvEmail = view.findViewById(R.id.tv_email);
        tvDescription = view.findViewById(R.id.tv_description);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadPanchayathDetails();
    }

    private void loadPanchayathDetails() {

        int panchayathRid = MySharedPreferences.getPanchayathData(getActivity()).getRid();

        if (panchayathRid <= 0) {
            shortToast("Session error. Please re-login...");
            return;
        }

        showProgress(true, cardPanchayathDetails, progressBar);

        ApiInteractor.loadPanchayathDetails(
                panchayathRid,
                new ApiResponseListener<ResponseWrapper<Panchayath>>() {
                    @Override
                    public void onSuccess(ResponseWrapper<Panchayath> response) {
                        showProgress(false, cardPanchayathDetails, progressBar);
                        displayDetails(response);
                    }

                    @Override
                    public void onError(Throwable err) {
                        showProgress(false, cardPanchayathDetails, progressBar);
                        longToast(err.getMessage());
                        Log.e(TAG, "onError: ", err);
                    }
                }
        );
    }

    private void displayDetails(ResponseWrapper<Panchayath> response) {
        if (response != null) {
            if (response.isSuccess()) {

                panchayath = response.getResponse();

                if (panchayath != null) {
                    tvPresident.setText(panchayath.getPresident());
                    tvGrama.setText(panchayath.getGramaName());
                    tvPresidentContact.setText(panchayath.getContact());
                    tvTelephone.setText(panchayath.getTelePhone());
                    tvEmail.setText(panchayath.getEmail());
                    tvDescription.setText(panchayath.getDescription());
                }

            } else {
                longToast(response.getError());
            }
        }
    }
}
