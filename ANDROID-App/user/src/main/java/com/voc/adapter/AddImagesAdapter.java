package com.voc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.voc.R;

import java.util.ArrayList;
import java.util.List;

public class AddImagesAdapter extends RecyclerView.Adapter<AddImagesAdapter.ImageHolder> {

    private List<String> imagesList = new ArrayList<>();

    public AddImagesAdapter() {
    }

    public void addImages(List<String> pathList) {
        imagesList.clear();
        imagesList.addAll(pathList);
        notifyDataSetChanged();
    }

    public void addImage(String path) {
        imagesList.add(path);
        notifyItemInserted(imagesList.size() - 1);
    }

    public void removeImage(int position) {
        imagesList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        String path = imagesList.get(position);
        Glide.with(holder.itemView).load(path).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagesList == null ? 0 : imagesList.size();
    }

    static class ImageHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        private ImageHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
        }
    }
}
