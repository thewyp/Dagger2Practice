package com.thewyp.daggerpractice.di;

import com.thewyp.daggerpractice.di.auth.AuthModule;
import com.thewyp.daggerpractice.di.auth.AuthScope;
import com.thewyp.daggerpractice.di.auth.AuthViewModelsModule;
import com.thewyp.daggerpractice.di.main.MainModule;
import com.thewyp.daggerpractice.di.main.MainFragmentBuilderModule;
import com.thewyp.daggerpractice.di.main.MainScope;
import com.thewyp.daggerpractice.di.main.MainViewModelsModule;
import com.thewyp.daggerpractice.ui.auth.AuthActivity;
import com.thewyp.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @AuthScope
    @ContributesAndroidInjector(modules = {
            AuthViewModelsModule.class,
            AuthModule.class
    })
    abstract AuthActivity contributesAuthActivity();

    @MainScope
    @ContributesAndroidInjector(modules = {
            MainFragmentBuilderModule.class,
            MainViewModelsModule.class,
            MainModule.class
    })
    abstract MainActivity contributesMainActivity();

    @ContributesAndroidInjector
    abstract OkHttpGlideModule contributesOkHttpGlideModule();

}
