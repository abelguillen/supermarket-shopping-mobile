package com.aguillen.supermarketshoppingmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aguillen.supermarketshoppingmobile.R;
import com.aguillen.supermarketshoppingmobile.model.Article;

import java.util.List;

public class ArticlesListAdapter extends BaseAdapter {

    private Context context;
    private List<Article> articles;
    private LayoutInflater inflter;

    public ArticlesListAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.activity_row_article, null);

        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView description = (TextView) view.findViewById(R.id.tv_description);
        TextView category = (TextView) view.findViewById(R.id.tv_category);
        ImageView image = (ImageView) view.findViewById(R.id.iv_image) ;

        name.setText(articles.get(i).getName());
        description.setText(articles.get(i).getDescription());
        category.setText(articles.get(i).getCategory());
        image.setImageResource(R.drawable.noimage);

        return view;
    }
}
