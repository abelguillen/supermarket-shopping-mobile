package com.aguillen.supermarketshopping.service;

import android.util.Log;

import com.aguillen.supermarketshopping.model.Article;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleServiceImpl {

    private static final String BASE_URL = "http://192.168.100.158:8080";

    private static List<Article> articles = new ArrayList<Article>();

    public static List<Article> getAll() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArticleService service = retrofit.create(ArticleService.class);
        Call<List<Article>> call = service.getAll();

        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                for(Article article : response.body()) {
                    articles.add(article);
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.e("Connection error: ", t.getMessage());
            }
        });

        return articles;
    }
}
