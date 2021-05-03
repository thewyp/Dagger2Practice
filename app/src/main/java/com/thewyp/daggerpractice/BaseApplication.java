package com.thewyp.daggerpractice;

import com.thewyp.daggerpractice.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    public static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.factory().init(this);
    }

}
