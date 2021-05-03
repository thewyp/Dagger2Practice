package com.thewyp.daggerpractice.ui.main.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thewyp.daggerpractice.databinding.PostListItemBinding;
import com.thewyp.daggerpractice.models.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> posts = new ArrayList<>();

    private final OnItemClickListener onItemClickListener;

    public PostAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PostListItemBinding binding = PostListItemBinding.inflate(inflater, parent, false);
        return new PostViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PostViewHolder) holder).bind(posts.get(position),  onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        private final PostListItemBinding binding;

        public PostViewHolder(@NonNull View itemView, PostListItemBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        public void bind(Post post, OnItemClickListener onItemClickListener) {
            binding.setPost(post);
            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(post);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Post item);
    }
}
