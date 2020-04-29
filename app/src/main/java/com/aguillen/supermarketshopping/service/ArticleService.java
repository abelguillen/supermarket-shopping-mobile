package com.aguillen.supermarketshopping.service;

import com.aguillen.supermarketshopping.model.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArticleService {

    @GET("/article/list")
    Call<List<Article>> getAll();

}
