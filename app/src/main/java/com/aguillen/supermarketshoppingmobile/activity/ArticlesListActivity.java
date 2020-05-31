package com.aguillen.supermarketshoppingmobile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.aguillen.supermarketshoppingmobile.R;
import com.aguillen.supermarketshoppingmobile.adapter.ArticlesListAdapter;
import com.aguillen.supermarketshoppingmobile.dto.ArticleDTO;
import com.aguillen.supermarketshoppingmobile.model.Article;
import com.aguillen.supermarketshoppingmobile.service.ArticleServiceImpl;
import com.aguillen.supermarketshoppingmobile.util.Mapper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesListActivity extends AppCompatActivity {

    ListView lvArticles;
    List<Article> articles;
    ArticlesListAdapter adapter;
    Button btBack;
    Button btExit;
    Button btNewArticle;
    ProgressBar pbArticlesList;
    private ArticleServiceImpl service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        service = new ArticleServiceImpl();
        getArticles(getApplicationContext());

        lvArticles = (ListView)findViewById(R.id.lv_articles_list);
        btBack = (Button) findViewById(R.id.bt_back);
        btExit = (Button) findViewById(R.id.bt_exit);
        btNewArticle = (Button) findViewById(R.id.bt_new_article);
        pbArticlesList = (ProgressBar) findViewById(R.id.pb_articles_list);

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ArticlesListActivity.this, MenuActivity.class);
                finish();
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        btNewArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ArticlesListActivity.this, ArticleCreateActivity.class);
                finish();
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void getArticles(Context context) {
        articles = new ArrayList<Article>();
        Call<List<ArticleDTO>> call = service.getArticleService().getAll();

        call.enqueue(new Callback<List<ArticleDTO>>() {
            @Override
            public void onResponse(Call<List<ArticleDTO>> call, Response<List<ArticleDTO>> response) {
                pbArticlesList.setVisibility(View.INVISIBLE);
                for(ArticleDTO articleDTO : response.body()) {
                    articles.add(Mapper.buildBO(articleDTO));
                }
                adapter = new ArticlesListAdapter(context, articles, ArticlesListActivity.this);
                lvArticles.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ArticleDTO>> call, Throwable t) {
                pbArticlesList.setVisibility(View.INVISIBLE);
                Log.e("Get Articles error: ", t.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(ArticlesListActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setTitle("Error de conexion");
                builder.setMessage("Ha ocurrido un error al intentar obtener informacion de la base de datos.");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
                return;
            }

        });

    }
}
