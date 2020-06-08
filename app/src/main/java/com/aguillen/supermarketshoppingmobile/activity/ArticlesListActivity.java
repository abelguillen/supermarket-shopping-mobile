package com.aguillen.supermarketshoppingmobile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.aguillen.supermarketshoppingmobile.R;
import com.aguillen.supermarketshoppingmobile.adapter.ArticlesListAdapter;
import com.aguillen.supermarketshoppingmobile.controller.PagerController;
import com.aguillen.supermarketshoppingmobile.dto.ArticleDTO;
import com.aguillen.supermarketshoppingmobile.dto.CategoryDTO;
import com.aguillen.supermarketshoppingmobile.model.Article;
import com.aguillen.supermarketshoppingmobile.model.Category;
import com.aguillen.supermarketshoppingmobile.service.article.ArticleServiceImpl;
import com.aguillen.supermarketshoppingmobile.service.category.CategoryServiceImpl;
import com.aguillen.supermarketshoppingmobile.util.Mapper;
import com.google.android.material.tabs.TabLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesListActivity extends AppCompatActivity {

    List<Article> articles;
    Button btBack;
    Button btExit;
    Button btNewArticle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayout.Tab customTab;
    private PagerController pagerAdapter;
    ProgressBar pbArticlesList;
    private CategoryServiceImpl service;
    private ArrayList<String> nameTabsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        nameTabsList = new ArrayList<String>();

        service = new CategoryServiceImpl();

        getCategorys();

        btBack = (Button) findViewById(R.id.bt_back);
        btExit = (Button) findViewById(R.id.bt_exit);
        btNewArticle = (Button) findViewById(R.id.bt_new_article);
        pbArticlesList = (ProgressBar) findViewById(R.id.pb_articles_list);

        tabLayout = (TabLayout) findViewById(R.id.tl_tab);
        viewPager = (ViewPager) findViewById(R.id.vp_pager);

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

    private void getCategorys() {
        Call<List<CategoryDTO>> call = service.getCategoryService().getAll();

        call.enqueue(new Callback<List<CategoryDTO>>() {
            @Override
            public void onResponse(Call<List<CategoryDTO>> call, Response<List<CategoryDTO>> response) {

                pbArticlesList.setVisibility(View.INVISIBLE);

                for(int i=0;i<response.body().size();i++) {
                    nameTabsList.add(response.body().get(i).getName());
                }

                for(int i=0;i<nameTabsList.size();i++) {
                    tabLayout.addTab(tabLayout.newTab().setText(nameTabsList.get(i)));
                }

                pagerAdapter = new PagerController(getSupportFragmentManager(), tabLayout.getTabCount(),
                        nameTabsList, getApplicationContext(), ArticlesListActivity.this,
                        pbArticlesList, true);
                viewPager.setAdapter(pagerAdapter);

                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                        pagerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            }

            @Override
            public void onFailure(Call<List<CategoryDTO>> call, Throwable t) {
                pbArticlesList.setVisibility(View.INVISIBLE);
                Log.e("Get Categorys error: ", t.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(ArticlesListActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setTitle("Error de conexion");
                builder.setMessage("Ha ocurrido un error al intentar obtener informacion de la base de datos.");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(ArticlesListActivity.this, MenuActivity.class);
                        finish();
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
            }
        });
    }
}
