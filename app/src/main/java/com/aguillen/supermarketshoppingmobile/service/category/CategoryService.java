package com.aguillen.supermarketshoppingmobile.service.category;

import com.aguillen.supermarketshoppingmobile.dto.CategoryDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("/category/list")
    Call<List<CategoryDTO>> getAll();
}
