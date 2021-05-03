package com.thewyp.daggerpractice.di.main;

import androidx.lifecycle.ViewModel;

import com.thewyp.daggerpractice.di.ViewModelKey;
import com.thewyp.daggerpractice.ui.main.comment.CommentViewModel;
import com.thewyp.daggerpractice.ui.main.photo.PhotoViewModel;
import com.thewyp.daggerpractice.ui.main.posts.PostsViewModel;
import com.thewyp.daggerpractice.ui.main.profile.ProfileViewModel;
import com.thewyp.daggerpractice.ui.main.todos.TodoViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindAuthViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TodoViewModel.class)
    public abstract ViewModel bindTodoViewModel(TodoViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PhotoViewModel.class)
    public abstract ViewModel bindPhotoViewModel(PhotoViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CommentViewModel.class)
    public abstract ViewModel bindCommentViewModel(CommentViewModel viewModel);
}
