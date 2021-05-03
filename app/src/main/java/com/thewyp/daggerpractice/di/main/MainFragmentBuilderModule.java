package com.thewyp.daggerpractice.di.main;

import com.thewyp.daggerpractice.ui.main.comment.CommentFragment;
import com.thewyp.daggerpractice.ui.main.photo.PhotoFragment;
import com.thewyp.daggerpractice.ui.main.posts.PostsFragment;
import com.thewyp.daggerpractice.ui.main.profile.ProfileFragment;
import com.thewyp.daggerpractice.ui.main.todos.TodoFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributesProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributesPostsFragment();

    @ContributesAndroidInjector
    abstract TodoFragment contributesTodoFragment();

    @ContributesAndroidInjector
    abstract PhotoFragment contributesPhotoFragment();

    @ContributesAndroidInjector
    abstract CommentFragment contributesCommentFragment();
}
