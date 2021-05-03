package com.thewyp.daggerpractice.ui.main.todos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.thewyp.daggerpractice.SessionManager;
import com.thewyp.daggerpractice.models.Todo;
import com.thewyp.daggerpractice.network.todo.TodoApi;
import com.thewyp.daggerpractice.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TodoViewModel extends ViewModel {

    private static final String TAG = "TodoViewModel";

    private MediatorLiveData<Resource<List<Todo>>> todos;


    private SessionManager sessionManager;
    private TodoApi todoApi;

    @Inject
    public TodoViewModel(SessionManager sessionManager, TodoApi todoApi) {
        this.sessionManager = sessionManager;
        this.todoApi = todoApi;
    }

    public LiveData<Resource<List<Todo>>> observeTodos() {
        if (todos == null) {
            todos = new MediatorLiveData<>();
            todos.setValue(Resource.loading(null));
            final LiveData<Resource<List<Todo>>> source = LiveDataReactiveStreams.fromPublisher(
                    todoApi.getTodosFromUser(sessionManager.getAuthUser().getValue().data.getId())
                            .onErrorReturn(throwable -> {
                                Todo todo = new Todo();
                                todo.setId(-1);
                                List<Todo> list = new ArrayList<>();
                                list.add(todo);
                                return list;
                            })
                    .map((Function<List<Todo>, Resource<List<Todo>>>) todos -> {
                        if (todos != null && !todos.isEmpty()) {
                            if (todos.get(0).getId() == -1) {
                                return Resource.error("Something went wrong", null);
                            }
                        }
                        return Resource.success(todos);
                    })
                    .subscribeOn(Schedulers.io())
            );
            todos.addSource(source, new Observer<Resource<List<Todo>>>() {
                @Override
                public void onChanged(Resource<List<Todo>> listResource) {
                    todos.setValue(listResource);
                    todos.removeSource(source);
                }
            });

        }
        return todos;
    }


}
