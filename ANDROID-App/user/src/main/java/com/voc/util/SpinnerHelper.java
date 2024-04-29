package com.voc.util;

import android.widget.Spinner;

import com.voc.adapter.GenericSpinnerAdapter;
import com.voc.model.District;
import com.voc.model.Panchayath;
import com.voc.model.Taluk;

import java.util.List;

public class SpinnerHelper {

    public static void setDistricts(Spinner spinner, List<District> districtList) {
        spinner.setAdapter(
                new GenericSpinnerAdapter<>(
                        districtList,
                        (view, district, position) -> view.setText(district.getName())
                ));
    }

    public static void setTaluk(Spinner spinner, List<Taluk> districtList) {
        spinner.setAdapter(
                new GenericSpinnerAdapter<>(
                        districtList,
                        (view, taluk, position) -> view.setText(taluk.getName())
                ));
    }

    public static void setPanchayaths(Spinner spinner, List<Panchayath> districtList) {
        spinner.setAdapter(
                new GenericSpinnerAdapter<>(
                        districtList,
                        (view, panchayath, position) -> view.setText(panchayath.getGramaName())
                ));
    }
}
