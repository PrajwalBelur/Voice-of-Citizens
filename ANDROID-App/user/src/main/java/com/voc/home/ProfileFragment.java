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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.voc.LoginActivity;
import com.voc.R;
import com.voc.api.ApiConstants;
import com.voc.api.ApiInteractor;
import com.voc.api.ApiResponseListener;
import com.voc.base.BaseFragment;
import com.voc.model.Citizen;
import com.voc.model.ResponseWrapper;
import com.voc.util.MySharedPreferences;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends BaseFragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    private TextView tvName, tvContact, tvEmail, tvAddress, tvGrama;
    private CircleImageView profileImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profile_image);
        tvName = view.findViewById(R.id.tv_name);
        tvContact = view.findViewById(R.id.tv_contact);
        tvEmail = view.findViewById(R.id.tv_email);
        tvAddress = view.findViewById(R.id.tv_address);
        tvGrama = view.findViewById(R.id.tv_grama);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
    }

    private void setData() {
        Citizen citizen = MySharedPreferences.getCitizenData(getActivity());
        if (citizen != null) {

            String imageUrl = ApiConstants.IMAGE_URL + citizen.getProfilePicUrl();
            Glide.with(requireActivity()).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(profileImage);

            tvName.setText(citizen.getName());
            tvContact.setText(citizen.getContact());
            tvEmail.setText(citizen.getEmail());
            tvAddress.setText(citizen.getAddress());
            tvGrama.setText(citizen.getGramaName());
        } else {
            shortToast("Session error. Please re-login...");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_profile:
                editProfile();
                return false;
            case R.id.menu_delete_profile:
                deleteProfileConfirmation();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editProfile() {

        Citizen citizen = MySharedPreferences.getCitizenData(getActivity());

        if (citizen == null) {
            shortToast("Session error. Please re-login");
            return;
        }

        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        intent.putExtra("citizen", citizen);
        startActivityForResult(intent, 101);
    }

    private void deleteProfileConfirmation() {
        showConfirmation(
                "Confirm",
                "Are you sure want to delete the account?",
                (dialog, which) -> {
                    deleteProfile();
                }
        );
    }

    private void deleteProfile() {

        int citizenRid = MySharedPreferences.getCitizenRid(requireActivity());

        if (citizenRid == 0) {
            shortToast("Session error. Please re-login...");
            return;
        }

        shortToast("Deleting. Please wait...");

        ApiInteractor.deleteProfile(
                citizenRid,
                new ApiResponseListener<ResponseWrapper<String>>() {
                    @Override
                    public void onSuccess(ResponseWrapper<String> response) {
                        displayDeleteMessage(response);
                    }

                    @Override
                    public void onError(Throwable err) {
                        longToast(err.getMessage());
                        Log.e(TAG, "onError: ", err);
                    }
                }
        );
    }

    private void displayDeleteMessage(ResponseWrapper<String> response) {
        if (response != null) {
            if (response.isSuccess()) {

                shortToast(response.getResponse());

                // clear all active login details and goto Login page
                MySharedPreferences.saveCitizenData(requireActivity(), null);
                startActivity(new Intent(requireActivity(), LoginActivity.class));
                requireActivity().finish();

            } else {
                longToast(response.getError());
            }
        }
    }
}
