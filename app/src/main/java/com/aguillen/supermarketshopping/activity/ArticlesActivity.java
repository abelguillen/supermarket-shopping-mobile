package com.aguillen.supermarketshopping.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.aguillen.supermarketshopping.R;
import com.aguillen.supermarketshopping.adapter.ArticlesListAdapter;
import com.aguillen.supermarketshopping.model.Article;
import com.aguillen.supermarketshopping.service.ArticleService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticlesActivity extends AppCompatActivity {

    ListView lvArticles;
    List<Article> articles;
    ArticlesListAdapter adapter;
    Button btBack;
    Button btExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        getArticlesPetition(getApplicationContext());

        lvArticles = (ListView)findViewById(R.id.lv_articles_list);
        btBack = (Button) findViewById(R.id.bt_back);
        btExit = (Button) findViewById(R.id.bt_exit);

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ArticlesActivity.this, MenuActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    private void getArticlesPetition(Context context) {
        String BASE_URL = "http://192.168.100.158:8080";

        articles = new ArrayList<Article>();

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
                adapter = new ArticlesListAdapter(context, articles);
                lvArticles.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.e("Connection error: ", t.getMessage());

                // Prueba local
                if(articles.isEmpty() || articles == null) {
                    articles.add(new Article(1, "Lavandina", "Ayudin", "Limpieza", R.drawable.lavandina));
                    articles.add(new Article(1, "Jabon de tocador", "Rexona", "Limpieza", R.drawable.jabon));
                    articles.add(new Article(1, "Desodorante", "Dove", "Limpieza", R.drawable.desodorante));
                    articles.add(new Article(1, "Lavandina", "Ayudin", "Limpieza", R.drawable.lavandina));
                    articles.add(new Article(1, "Jabon de tocador", "Rexona", "Limpieza", R.drawable.jabon));
                    articles.add(new Article(1, "Desodorante", "Dove", "Limpieza", R.drawable.desodorante));
                    articles.add(new Article(1, "Lavandina", "Ayudin", "Limpieza", R.drawable.lavandina));
                    articles.add(new Article(1, "Jabon de tocador", "Rexona", "Limpieza", R.drawable.jabon));
                    articles.add(new Article(1, "Desodorante", "Dove", "Limpieza", R.drawable.desodorante));
                    articles.add(new Article(1, "Lavandina", "Ayudin", "Limpieza", R.drawable.lavandina));
                    articles.add(new Article(1, "Jabon de tocador", "Rexona", "Limpieza", R.drawable.jabon));
                    articles.add(new Article(1, "Desodorante", "Dove", "Limpieza", R.drawable.desodorante));
                }
                adapter = new ArticlesListAdapter(context, articles);
                lvArticles.setAdapter(adapter);
            }

        });

    }
}
