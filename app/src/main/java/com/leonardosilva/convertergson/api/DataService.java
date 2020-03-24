package com.leonardosilva.convertergson.api;

import com.leonardosilva.convertergson.model.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("posts")
    Call<List<Posts>> getPosts();
}
