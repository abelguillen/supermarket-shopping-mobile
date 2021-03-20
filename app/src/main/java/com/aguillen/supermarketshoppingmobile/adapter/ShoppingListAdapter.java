package com.aguillen.supermarketshoppingmobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aguillen.supermarketshoppingmobile.R;
import com.aguillen.supermarketshoppingmobile.model.Article;
import com.aguillen.supermarketshoppingmobile.model.Shopping;
import com.aguillen.supermarketshoppingmobile.service.article.ArticleServiceImpl;

import java.util.List;

public class ShoppingListAdapter extends BaseAdapter {

    private Context context;
    private List<Shopping> shoppingList;
    private LayoutInflater inflter;
    private Activity activity;
    private ArticleServiceImpl service;
    private Button btAddOneArticle;
    private TextView tvQuantity;

    public ShoppingListAdapter(Context context, List<Shopping> shoppingList, Activity activity) {
        this.context = context;
        this.shoppingList = shoppingList;
        inflter = (LayoutInflater.from(context));
        this.activity = activity;
        this.service = new ArticleServiceImpl();
    }

    @Override
    public int getCount() {
        return shoppingList.size();
    }

    @Override
    public Object getItem(int i) {
        return shoppingList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return shoppingList.get(i).getArticle().getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        view = inflter.inflate(R.layout.activity_row_shopping_list, null);

        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView description = (TextView) view.findViewById(R.id.tv_description);
        TextView category = (TextView) view.findViewById(R.id.tv_category);
        ImageView image = (ImageView) view.findViewById(R.id.iv_image);
        Button btAddOneArticle = (Button) view.findViewById(R.id.bt_add_one_article);
        Button btDeleteOneArticle = (Button) view.findViewById(R.id.bt_delete_one_article);
        TextView tvQuantity = (TextView) view.findViewById(R.id.tv_quantity);

        btAddOneArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingList.get(i).setCantidad(shoppingList.get(i).getCantidad() + 1);
                tvQuantity.setText(shoppingList.get(i).getCantidad().toString());
            }
        });

        btDeleteOneArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingList.get(i).setCantidad(shoppingList.get(i).getCantidad() - 1);
                tvQuantity.setText(shoppingList.get(i).getCantidad().toString());
            }
        });

        name.setText(shoppingList.get(i).getArticle().getName());
        description.setText(shoppingList.get(i).getArticle().getDescription());
        category.setText(shoppingList.get(i).getArticle().getCategory());

        byte[] decodedString = Base64.decode(shoppingList.get(i).getArticle().getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        image.setImageBitmap(decodedByte);
        tvQuantity.setText(shoppingList.get(i).getCantidad().toString());

        return view;
    }
}