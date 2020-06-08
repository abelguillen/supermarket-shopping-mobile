package com.aguillen.supermarketshoppingmobile.controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.aguillen.supermarketshoppingmobile.R;
import com.aguillen.supermarketshoppingmobile.adapter.ArticlesListAdapter;
import com.aguillen.supermarketshoppingmobile.adapter.ShoppingListAdapter;
import com.aguillen.supermarketshoppingmobile.dto.ArticleDTO;
import com.aguillen.supermarketshoppingmobile.model.Article;
import com.aguillen.supermarketshoppingmobile.model.Shopping;
import com.aguillen.supermarketshoppingmobile.service.article.ArticleServiceImpl;
import com.aguillen.supermarketshoppingmobile.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomFragment extends Fragment {

    private Context context;
    private Activity activity;
    private String category;
    private ArticleServiceImpl service;
    private ProgressBar pbArticlesList;
    ListView lvArticles;
    List<Article> articles;
    ArticlesListAdapter articlesListAdapter;
    ShoppingListAdapter shoppingListAdapter;
    private boolean isArticlesList;
    private List<Shopping> shoppingList;

    public CustomFragment(String category, Context context, Activity activity,
                          ProgressBar pbArticlesList, boolean isArticlesList) {
        this.category = category;
        this.context = context;
        this.activity = activity;
        this.pbArticlesList = pbArticlesList;
        this.isArticlesList = isArticlesList;
    }

    public CustomFragment(String category, Context context, Activity activity,
                          ProgressBar pbArticlesList, boolean isArticlesList,
                          List<Shopping> shoppingList) {
        this.category = category;
        this.context = context;
        this.activity = activity;
        this.pbArticlesList = pbArticlesList;
        this.isArticlesList = isArticlesList;
        this.shoppingList = shoppingList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_custom, container, false);

        pbArticlesList.setVisibility(View.VISIBLE);
        service = new ArticleServiceImpl();

        lvArticles = (ListView)view.findViewById(R.id.lv_articles_list);

        if(isArticlesList) {
            getArticlesByCategory(context, category);
        } else {
            pbArticlesList.setVisibility(View.INVISIBLE);
            shoppingListAdapter = new ShoppingListAdapter(context, shoppingList, activity);
            lvArticles.setAdapter(shoppingListAdapter);
        }

        return view;
    }

    private void getArticlesByCategory(Context context, String category) {
        articles = new ArrayList<Article>();
        Call<List<ArticleDTO>> call = service.getArticleService().getByCategory(category);

        call.enqueue(new Callback<List<ArticleDTO>>() {
            @Override
            public void onResponse(Call<List<ArticleDTO>> call, Response<List<ArticleDTO>> response) {
                pbArticlesList.setVisibility(View.INVISIBLE);
                for(ArticleDTO articleDTO : response.body()) {
                    articles.add(Mapper.buildArticleBO(articleDTO));
                }
                articlesListAdapter = new ArticlesListAdapter(context, articles, activity);
                lvArticles.setAdapter(articlesListAdapter);
            }

            @Override
            public void onFailure(Call<List<ArticleDTO>> call, Throwable t) {
                pbArticlesList.setVisibility(View.INVISIBLE);
                Log.e("Get Articles error: ", t.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
