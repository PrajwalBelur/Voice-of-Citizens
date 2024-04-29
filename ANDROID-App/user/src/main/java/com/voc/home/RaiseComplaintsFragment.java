package com.voc.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.voc.R;
import com.voc.base.BaseFragment;
import com.voc.model.Complaint;

public class RaiseComplaintsFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = RaiseComplaintsFragment.class.getSimpleName();

    private Button btnWater, btnSewage, btnStreetLight, btnRoad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_raise_complaints, container, false);

        btnWater = view.findViewById(R.id.btn_water);
        btnSewage = view.findViewById(R.id.btn_sewage);
        btnStreetLight = view.findViewById(R.id.btn_street_light);
        btnRoad = view.findViewById(R.id.btn_road);

        btnWater.setOnClickListener(RaiseComplaintsFragment.this);
        btnSewage.setOnClickListener(RaiseComplaintsFragment.this);
        btnStreetLight.setOnClickListener(RaiseComplaintsFragment.this);
        btnRoad.setOnClickListener(RaiseComplaintsFragment.this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_water:
                openComplaintsEditor(Complaint.Type.WATER);
                break;
            case R.id.btn_sewage:
                openComplaintsEditor(Complaint.Type.SEWAGE);
                break;
            case R.id.btn_street_light:
                openComplaintsEditor(Complaint.Type.STREET_LIGHT);
                break;
            case R.id.btn_road:
                openComplaintsEditor(Complaint.Type.ROAD);
                break;
        }
    }

    private void openComplaintsEditor(Complaint.Type type) {
        Intent intent = new Intent(getActivity(), AddComplaintsActivity.class);
        intent.putExtra("compType", type);
        startActivityForResult(intent, 101);
    }
}
