package com.aguillen.supermarketshoppingmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.aguillen.supermarketshoppingmobile.R;
import com.aguillen.supermarketshoppingmobile.adapter.ArticlesListAdapter;
import com.aguillen.supermarketshoppingmobile.model.Article;
import com.aguillen.supermarketshoppingmobile.service.ArticleService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleCreateActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etDescription;
    private Spinner sCategory;
    private Button btBack;
    private Button btSave;
    private Button btExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_article);

        etName = (EditText) findViewById(R.id.et_name);
        etDescription = (EditText) findViewById(R.id.et_description);
        sCategory = (Spinner) findViewById(R.id.s_category);
        btBack = (Button) findViewById(R.id.bt_back);
        btSave = (Button) findViewById(R.id.bt_save);
        btExit = (Button) findViewById(R.id.bt_exit);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                String category = sCategory.getSelectedItem().toString();
                Article article = new Article(name, description, category, 1);
                saveArticles(getApplicationContext(), article);
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ArticleCreateActivity.this, ArticlesListActivity.class);
                finish();
                startActivity(i);
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });
    }

    private void saveArticles(Context context, Article article) {

        String BASE_URL = "http://192.168.100.158:8080";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArticleService service = retrofit.create(ArticleService.class);
        Call<Article> call = service.create(article);

        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                Intent i = new Intent(ArticleCreateActivity.this, ArticlesListActivity.class);
                finish();
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e("Connection error: ", t.getMessage());
            }
        });
    }

}
