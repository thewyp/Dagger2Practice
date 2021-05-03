package com.thewyp.daggerpractice.network.comment;

import com.thewyp.daggerpractice.models.Comment;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommentApi {
    @GET("comments")
    Flowable<List<Comment>> getPostsFromUser(
            @Query("userId") int userId,
            @Query("postId") int postId
    );

}
