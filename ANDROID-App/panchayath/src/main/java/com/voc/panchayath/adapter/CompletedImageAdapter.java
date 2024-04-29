package com.voc.panchayath.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.voc.panchayath.R;
import com.voc.panchayath.api.ApiConstants;

import java.util.ArrayList;
import java.util.List;

public class CompletedImageAdapter extends RecyclerView.Adapter<CompletedImageAdapter.ImageHolder> {

    private List<String> imageList = new ArrayList<>();

    public CompletedImageAdapter() {
    }

    public void addImageList(List<String> imageList) {
        this.imageList.clear();
        this.imageList.addAll(imageList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        String path = ApiConstants.IMAGE_URL + imageList.get(position);
        Glide.with(holder.itemView).load(path).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList == null ? 0 : imageList.size();
    }

    static class ImageHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        private ImageHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
        }
    }
}
