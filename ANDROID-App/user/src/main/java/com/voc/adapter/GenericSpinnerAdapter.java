package com.voc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;

import java.util.List;

public class GenericSpinnerAdapter<T> extends BaseAdapter {

    private List<T> dataList;
    private BindSpinner<T> binder;

    public GenericSpinnerAdapter(List<T> dataList, @NonNull BindSpinner<T> binder) {
        this.dataList = dataList;
        this.binder = binder;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final SpinnerHolder holder;

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, viewGroup, false);
            holder = new SpinnerHolder(view);
            view.setTag(holder);
        } else {
            holder = (SpinnerHolder) view.getTag();
        }

        if (binder != null) {
            binder.onBind(holder.textView, getItem(position), position);
        } else {
            throw new RuntimeException("Please implement BindSpinner interface!");
        }

        return view;
    }

    static class SpinnerHolder {

        private CheckedTextView textView;

        public SpinnerHolder(View view) {
            textView = view.findViewById(android.R.id.text1);
        }
    }

    public interface BindSpinner<T> {
        void onBind(CheckedTextView view, T t, int position);
    }
}
