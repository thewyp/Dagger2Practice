package com.thewyp.daggerpractice.ui.main.todos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thewyp.daggerpractice.databinding.TodoListItemBinding;
import com.thewyp.daggerpractice.models.Todo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {


    @IntDef({ALL, DONE, NONDONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FilterType {
    }

    public static final int ALL = 0x00000000;

    public static final int DONE = 0x00000004;

    public static final int NONDONE = 0x00000008;

    @FilterType
    private int filterType = ALL;

    private List<Todo> filterTodos = new ArrayList<>();

    private List<Todo> todos = new ArrayList<>();

    public TodoAdapter() {
        filterType = ALL;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TodoListItemBinding todoListItemBinding = TodoListItemBinding.inflate(inflater, parent, false);
        return new TodoViewHolder(todoListItemBinding.getRoot(), todoListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.bind(filterTodos.get(position));
    }

    @Override
    public int getItemCount() {
        return filterTodos.size();
    }

    public void setFiltering(@FilterType int type) {
        filterType = type;
        filterTodos.clear();
        switch (filterType) {
            case ALL:
                filterTodos.addAll(todos);
                break;
            case DONE:
                todos.forEach(todo -> {
                    if (todo.isCompleted()) {
                        filterTodos.add(todo);
                    }
                });
                break;
            case NONDONE:
                todos.forEach(todo -> {
                    if (!todo.isCompleted()) {
                        filterTodos.add(todo);
                    }
                });
                break;
            default:
                break;
        }
        notifyDataSetChanged();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        setFiltering(filterType);
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {

        TodoListItemBinding binding;

        public TodoViewHolder(@NonNull View itemView, TodoListItemBinding todoListItemBinding) {
            super(itemView);
            binding = todoListItemBinding;
        }

        public void bind(Todo todo) {
            binding.setTodo(todo);
        }
    }
}
