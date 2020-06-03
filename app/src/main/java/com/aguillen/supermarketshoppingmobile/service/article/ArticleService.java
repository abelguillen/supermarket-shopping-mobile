package com.aguillen.supermarketshoppingmobile.service.article;

import com.aguillen.supermarketshoppingmobile.dto.ArticleDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleService {

    @GET("/article/list")
    Call<List<ArticleDTO>> getAll();

    @GET("/article/list/{category}")
    Call<List<ArticleDTO>> getByCategory(@Path("category") String category);

    @POST("/article/create")
    Call<ArticleDTO> create(@Body ArticleDTO articleDTO);

    @DELETE("/article/delete")
    Call<Boolean> delete(@Query("id") Integer id);

    @PUT("/article/update")
    Call<ArticleDTO> update(@Body ArticleDTO articleDTO);
}
