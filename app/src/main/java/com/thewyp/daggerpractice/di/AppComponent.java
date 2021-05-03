package com.thewyp.daggerpractice.di;

import android.app.Application;

import com.thewyp.daggerpractice.BaseApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuilderModule.class,
        AppModule.class,
})
public interface AppComponent extends AndroidInjector<BaseApplication> {


    @Component.Factory
    interface factory {
        AppComponent init(@BindsInstance Application application);
    }

}
