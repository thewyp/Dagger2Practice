package com.thewyp.daggerpractice.network.photo;

import com.thewyp.daggerpractice.models.Photo;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoApi {
    @GET("photos")
    Flowable<List<Photo>> getPhotos(
            @Query("albumId") int albumId
    );
}
