package com.aguillen.supermarketshopping.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.aguillen.supermarketshopping.R;
import com.aguillen.supermarketshopping.model.Article;
import com.aguillen.supermarketshopping.service.ArticleServiceImpl;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ArticlesActivity extends AppCompatActivity {

    ListView simpleList;
    String articlesList[] = {"Carne", "Galletitas", "Jabon", "Harina", "Detergente", "Frutas"};

    private ArticleServiceImpl service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        simpleList = (ListView)findViewById(R.id.lv_articles_list);

        service = new ArticleServiceImpl();

        List<Article> articles = service.getAll();

        Log.i("articles: ",articles.toString());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_row_article, R.id.tv_text, articlesList);
        simpleList.setAdapter(arrayAdapter);
    }
}
