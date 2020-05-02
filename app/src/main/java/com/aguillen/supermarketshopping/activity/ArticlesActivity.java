package com.aguillen.supermarketshopping.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.aguillen.supermarketshopping.R;
import com.aguillen.supermarketshopping.adapter.ArticlesListAdapter;
import com.aguillen.supermarketshopping.model.Article;
import com.aguillen.supermarketshopping.service.ArticleServiceImpl;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ArticlesActivity extends AppCompatActivity {

    ListView simpleList;

    private ArticleServiceImpl service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        simpleList = (ListView)findViewById(R.id.lv_articles_list);

        service = new ArticleServiceImpl();

        List<Article> articles = service.getAll();

        Log.i("articles: ",articles.toString());

        if(articles.isEmpty() || articles == null) {
            articles.add(new Article(1, "Lavandina", "Ayudin", "Limpieza", R.drawable.lavandina));
            articles.add(new Article(1, "Jabon de tocador", "Rexona", "Limpieza", R.drawable.jabon));
            articles.add(new Article(1, "Desodorante", "Dove", "Limpieza", R.drawable.desodorante));
        }

        ArticlesListAdapter adapter = new ArticlesListAdapter(getApplicationContext(), articles);
        simpleList.setAdapter(adapter);

    }
}
