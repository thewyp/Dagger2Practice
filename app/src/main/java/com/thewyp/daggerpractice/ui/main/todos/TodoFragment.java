package com.thewyp.daggerpractice.ui.main.todos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thewyp.daggerpractice.R;
import com.thewyp.daggerpractice.databinding.FragmentTodosBinding;
import com.thewyp.daggerpractice.models.Todo;
import com.thewyp.daggerpractice.ui.main.Resource;
import com.thewyp.daggerpractice.utils.VerticalSpaceItemDecoration;
import com.thewyp.daggerpractice.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class TodoFragment extends DaggerFragment {

    private static final String TAG = "TodoFragment";

    TodoAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    TodoViewModel todoViewModel;

    FragmentTodosBinding todosBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        todosBinding = FragmentTodosBinding.inflate(inflater, container, false);
        return todosBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        todoViewModel = new ViewModelProvider(this, providerFactory).get(TodoViewModel.class);
        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers() {
        todoViewModel.observeTodos().observe(getViewLifecycleOwner(), new Observer<Resource<List<Todo>>>() {
            @Override
            public void onChanged(Resource<List<Todo>> listResource) {
                if (listResource != null) {
                    switch (listResource.status) {
                        case LOADING: {
                            Log.d(TAG, "onChanged: PostsFragment: LOADING...");
                            break;
                        }

                        case SUCCESS: {
                            Log.d(TAG, "onChanged: PostsFragment: got posts.");
                            adapter.setTodos(listResource.data);
                            break;
                        }

                        case ERROR: {
                            Log.d(TAG, "onChanged: PostsFragment: ERROR... " + listResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        todosBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(15);
        todosBinding.recyclerView.addItemDecoration(itemDecoration);
        todosBinding.recyclerView.setAdapter(adapter = new TodoAdapter());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.todos_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_filter) {
            showFilteringPopUpMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.todos_fragment_menu_filter, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.active:
                    adapter.setFiltering(TodoAdapter.NONDONE);
                    break;
                case R.id.completed:
                    adapter.setFiltering(TodoAdapter.DONE);
                    break;
                default:
                    adapter.setFiltering(TodoAdapter.ALL);
                    break;
            }
            return true;
        });
        popup.show();
    }
}
