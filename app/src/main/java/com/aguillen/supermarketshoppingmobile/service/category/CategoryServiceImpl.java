package com.aguillen.supermarketshoppingmobile.service.category;

import android.util.Log;

import com.aguillen.supermarketshoppingmobile.util.Environment;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryServiceImpl {

    public CategoryService getCategoryService() {

        String BASE_URL = "";
        try {
            BASE_URL = Environment.getHost();
        } catch (Exception ex) {
            Log.e("Connection Error", ex.getMessage());
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(CategoryService.class);
    }

}
