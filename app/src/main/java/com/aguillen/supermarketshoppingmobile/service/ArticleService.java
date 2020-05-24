package com.aguillen.supermarketshoppingmobile.service;

import com.aguillen.supermarketshoppingmobile.model.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ArticleService {

    @GET("/article/list")
    Call<List<Article>> getAll();

    @POST("/article/create")
    Call<Article> create(@Body Article article);

    @DELETE("/article/delete")
    Call<Boolean> delete(@Query("id") Integer id);

    @PUT("/article/update")
    Call<Article> update(@Body Article article);
}
