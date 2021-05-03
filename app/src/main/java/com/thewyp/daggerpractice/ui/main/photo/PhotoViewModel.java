package com.thewyp.daggerpractice.ui.main.photo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.thewyp.daggerpractice.models.Photo;
import com.thewyp.daggerpractice.network.photo.PhotoApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PhotoViewModel extends ViewModel {

    private static final String TAG = "PhotoViewModel";

    final PagedList.Config config;

    final LiveData<PagedList<Photo>> pagedListLiveData;

    private final PhotoApi photoApi;

    public LiveData<PagedList<Photo>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    private Disposable disposable;

    @Inject
    public PhotoViewModel(PhotoApi photoApi) {
        this.photoApi = photoApi;

        config = new PagedList.Config.Builder()
                .setPrefetchDistance(10)
                .build();

        DataSource.Factory<Integer, Photo> dataSourceFactory = new DataSource.Factory<Integer, Photo>() {
            @NonNull
            @Override
            public DataSource<Integer, Photo> create() {
                return createDataSource();
            }
        };

        pagedListLiveData = new LivePagedListBuilder<>(dataSourceFactory, config).build();
    }

    private PageKeyedDataSource<Integer, Photo> createDataSource() {
        Log.d(TAG, "createDataSource: " + Thread.currentThread().getId());
        return new PageKeyedDataSource<Integer, Photo>() {
            @Override
            public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Photo> callback) {
                Log.d(TAG, "loadInitial: ");
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
                disposable = photoApi.getPhotos(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(photos -> callback.onResult(photos, 1, 2), Throwable::printStackTrace);
            }

            @Override
            public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {

            }

            @Override
            public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {
                Log.d(TAG, "loadAfter: " + params.key);
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
                disposable = photoApi.getPhotos(params.key)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(photos -> callback.onResult(photos, params.key + 1), Throwable::printStackTrace);
            }
        };
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared: ");
        if (disposable != null && !disposable.isDisposed()) {
            Log.d(TAG, "onCleared: will dispose");
            disposable.dispose();
        }
    }
}
