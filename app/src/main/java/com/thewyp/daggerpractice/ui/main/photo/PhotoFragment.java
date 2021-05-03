package com.thewyp.daggerpractice.ui.main.photo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thewyp.daggerpractice.databinding.FragmentPhotosBinding;
import com.thewyp.daggerpractice.models.Photo;
import com.thewyp.daggerpractice.utils.VerticalSpaceItemDecoration;
import com.thewyp.daggerpractice.viewmodels.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PhotoFragment extends DaggerFragment {

    private FragmentPhotosBinding binding;

    PhotoAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    private PhotoViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPhotosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, providerFactory).get(PhotoViewModel.class);
        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.getPagedListLiveData().observe(getViewLifecycleOwner(), new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(PagedList<Photo> photos) {
                adapter.submitList(photos);
            }
        });
    }

    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(15);
        binding.recyclerView.addItemDecoration(itemDecoration);
        binding.recyclerView.setAdapter(adapter = new PhotoAdapter());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
