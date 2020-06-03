package com.aguillen.supermarketshoppingmobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.aguillen.supermarketshoppingmobile.R;
import com.aguillen.supermarketshoppingmobile.activity.ArticleUpdateActivity;
import com.aguillen.supermarketshoppingmobile.dto.ArticleDTO;
import com.aguillen.supermarketshoppingmobile.model.Article;
import com.aguillen.supermarketshoppingmobile.service.article.ArticleServiceImpl;
import com.aguillen.supermarketshoppingmobile.util.Mapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesListAdapter extends BaseAdapter {

    private Context context;
    private List<Article> articles;
    private LayoutInflater inflter;
    private Activity activity;
    private ArticleServiceImpl service;

    public ArticlesListAdapter(Context context, List<Article> articles, Activity activity) {
        this.context = context;
        this.articles = articles;
        inflter = (LayoutInflater.from(context));
        this.activity = activity;
        this.service = new ArticleServiceImpl();
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int i) {
        return articles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return articles.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        view = inflter.inflate(R.layout.activity_row_article, null);

        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView description = (TextView) view.findViewById(R.id.tv_description);
        TextView category = (TextView) view.findViewById(R.id.tv_category);
        ImageView image = (ImageView) view.findViewById(R.id.iv_image);
        Button btDeleteArticle = (Button) view.findViewById(R.id.bt_delete_article);
        Button btUpdateArticle = (Button) view.findViewById(R.id.bt_update_article);

        btDeleteArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setTitle("Eliminar Articulo");
                builder.setMessage("¿Esta seguro que desea eliminar el articulo?");
                builder.setCancelable(false);
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteArticle(Mapper.buildArticleDTO(articles.get(i)), articles.get(i));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
                return;
            }
        });

        btUpdateArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArticleUpdateActivity.class);
                intent.putExtra("article", articles.get(i));
                context.startActivity(intent);
                activity.finish();
            }
        });

        name.setText(articles.get(i).getName());
        description.setText(articles.get(i).getDescription());
        category.setText(articles.get(i).getCategory());

        byte[] decodedString = Base64.decode(articles.get(i).getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        image.setImageBitmap(decodedByte);

        return view;
    }

    public void deleteArticle(ArticleDTO articleDTO, Article article) {

        Call<Boolean> call = service.getArticleService().delete(articleDTO.getId());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                articles.remove(article);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("Delete Article error: ", t.getMessage());
            }
        });
    }
}
