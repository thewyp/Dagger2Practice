package com.thewyp.daggerpractice.network.todo;

import com.thewyp.daggerpractice.models.Comment;
import com.thewyp.daggerpractice.models.Todo;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TodoApi {

    @GET("users/{id}/todos")
    Flowable<List<Todo>> getTodosFromUser(
            @Path("id") int id
    );
}
