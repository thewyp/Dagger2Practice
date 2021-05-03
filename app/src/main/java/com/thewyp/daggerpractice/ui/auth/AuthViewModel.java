package com.thewyp.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.thewyp.daggerpractice.SessionManager;
import com.thewyp.daggerpractice.models.User;
import com.thewyp.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";
    private SessionManager sessionManager;
    private AuthApi authApi;

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager)  {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
        Log.d(TAG, "AuthViewModel: viewmodel is working...");

    }

    public LiveData<AuthResource<User>> observeAuthState(){
        return sessionManager.getAuthUser();
    }

    public void authenticateWithId(int userId) {
        Log.d(TAG, "attemptLogin: attempting to login.");
        sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId) {

        return LiveDataReactiveStreams.fromPublisher(authApi.getUser(userId)

                // instead of calling onError, do this
                .onErrorReturn(new Function<Throwable, User>() {
                    @Override
                    public User apply(Throwable throwable) throws Exception {
                        User errorUser = new User();
                        errorUser.setId(-1);
                        return errorUser;
                    }
                })

                // wrap User object in AuthResource
                .map(new Function<User, AuthResource<User>>() {
                    @Override
                    public AuthResource<User> apply(User user) throws Exception {
                        if(user.getId() == -1){
                            return AuthResource.error("Could not authenticate", null);
                        }
                        return AuthResource.authenticated(user);
                    }
                })
                .subscribeOn(Schedulers.io()));
    }
}