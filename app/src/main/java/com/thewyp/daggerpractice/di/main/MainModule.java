package com.thewyp.daggerpractice.di.main;

import com.thewyp.daggerpractice.network.comment.CommentApi;
import com.thewyp.daggerpractice.network.main.MainApi;
import com.thewyp.daggerpractice.network.photo.PhotoApi;
import com.thewyp.daggerpractice.network.todo.TodoApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static TodoApi provideTodoApi(Retrofit retrofit) {
        return retrofit.create(TodoApi.class);
    }

    @MainScope
    @Provides
    static PhotoApi providePhotoApi(Retrofit retrofit) {
        return retrofit.create(PhotoApi.class);
    }

    @MainScope
    @Provides
    static CommentApi provideCommentApi(Retrofit retrofit) {
        return retrofit.create(CommentApi.class);
    }
}


