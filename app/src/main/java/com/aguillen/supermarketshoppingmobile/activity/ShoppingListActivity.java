package com.aguillen.supermarketshoppingmobile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.aguillen.supermarketshoppingmobile.R;
import com.aguillen.supermarketshoppingmobile.controller.PagerController;
import com.aguillen.supermarketshoppingmobile.dto.ArticleDTO;
import com.aguillen.supermarketshoppingmobile.dto.CategoryDTO;
import com.aguillen.supermarketshoppingmobile.export.TemplatePDF;
import com.aguillen.supermarketshoppingmobile.model.Article;
import com.aguillen.supermarketshoppingmobile.model.Shopping;
import com.aguillen.supermarketshoppingmobile.service.article.ArticleServiceImpl;
import com.aguillen.supermarketshoppingmobile.service.category.CategoryServiceImpl;
import com.aguillen.supermarketshoppingmobile.util.Mapper;
import com.aguillen.supermarketshoppingmobile.validate.ValidateShopping;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingListActivity extends AppCompatActivity {

    private List<Article> articles;
    private Button btBack;
    private Button btExit;
    private Button btExport;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayout.Tab customTab;
    private PagerController pagerAdapter;
    private ProgressBar pbArticlesList;
    private CategoryServiceImpl categoryService;
    private ArticleServiceImpl articleService;
    private ArrayList<String> nameTabsList;
    private List<Shopping> shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        nameTabsList = new ArrayList<String>();
        shoppingList = new ArrayList<Shopping>();

        categoryService = new CategoryServiceImpl();
        articleService = new ArticleServiceImpl();

        getCategorys();

        btBack = (Button) findViewById(R.id.bt_back);
        btExit = (Button) findViewById(R.id.bt_exit);
        btExport = (Button) findViewById(R.id.bt_export);
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
                Intent i = new Intent(ShoppingListActivity.this, MenuActivity.class);
                finish();
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        btExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!ValidateShopping.isShoppingEmpty(pagerAdapter.getShoppingList())) {
                        TemplatePDF.exportPDF(pagerAdapter.getShoppingList());
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Exportar");
                        builder.setMessage("La lista de compras se ha exportado correctamente");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(ShoppingListActivity.this, MenuActivity.class);
                                finish();
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Exportar");
                        builder.setMessage("No se han agregado articulos a la lista de compras");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getCategorys() {
        Call<List<CategoryDTO>> call = categoryService.getCategoryService().getAll();

        call.enqueue(new Callback<List<CategoryDTO>>() {
            @Override
            public void onResponse(Call<List<CategoryDTO>> call, Response<List<CategoryDTO>> response) {

                for(int i=0;i<response.body().size();i++) {
                    nameTabsList.add(response.body().get(i).getName());
                }

                for(int i=0;i<nameTabsList.size();i++) {
                    tabLayout.addTab(tabLayout.newTab().setText(nameTabsList.get(i)));
                }

                getArticles();
            }

            @Override
            public void onFailure(Call<List<CategoryDTO>> call, Throwable t) {
                pbArticlesList.setVisibility(View.INVISIBLE);
                Log.e("Get Categorys error: ", t.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setTitle("Error de conexion");
                builder.setMessage("Ha ocurrido un error al intentar obtener informacion de la base de datos.");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(ShoppingListActivity.this, MenuActivity.class);
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

    private void getArticles() {
        Call<List<ArticleDTO>> call = articleService.getArticleService().getAll();
        call.enqueue(new Callback<List<ArticleDTO>>() {
            @Override
            public void onResponse(Call<List<ArticleDTO>> call, Response<List<ArticleDTO>> response) {

                pbArticlesList.setVisibility(View.INVISIBLE);

                List<Article> articlesList = new ArrayList<>();
                for(ArticleDTO articleDTO : response.body()) {
                    articlesList.add(Mapper.buildArticleBO(articleDTO));
                }
                shoppingList = Mapper.buildShoppingList(articlesList);

                pagerAdapter = new PagerController(getSupportFragmentManager(), tabLayout.getTabCount(),
                        nameTabsList, getApplicationContext(), ShoppingListActivity.this,
                        pbArticlesList, false, shoppingList);
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
            public void onFailure(Call<List<ArticleDTO>> call, Throwable t) {
                pbArticlesList.setVisibility(View.INVISIBLE);
                Log.e("Get Articles error: ", t.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setTitle("Error de conexion");
                builder.setMessage("Ha ocurrido un error al intentar obtener informacion de la base de datos.");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(ShoppingListActivity.this, MenuActivity.class);
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
