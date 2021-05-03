package com.thewyp.daggerpractice.ui.main.photo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.thewyp.daggerpractice.databinding.PhotoListItemBinding;
import com.thewyp.daggerpractice.models.Photo;

public class PhotoAdapter extends PagedListAdapter<Photo, PhotoAdapter.PhotoHolder> {

    public PhotoAdapter() {
        super(new DiffUtil.ItemCallback<Photo>() {
            @Override
            public boolean areItemsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
                return oldItem.getId() == newItem.getId() ;
            }
        });
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PhotoListItemBinding binding = PhotoListItemBinding.inflate(inflater, parent, false);
        return new PhotoHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        holder.bindData(getItem(position));

    }

    public static class PhotoHolder extends RecyclerView.ViewHolder {

        private final PhotoListItemBinding binding;

        public PhotoHolder(@NonNull View itemView, PhotoListItemBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        public void bindData(Photo photo) {
            binding.setPhoto(photo);
        }
    }
}
