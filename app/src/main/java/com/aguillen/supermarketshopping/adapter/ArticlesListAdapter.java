package com.aguillen.supermarketshopping.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aguillen.supermarketshopping.model.Article;

import java.util.List;

import com.aguillen.supermarketshopping.R;

public class ArticlesListAdapter extends BaseAdapter {

    protected Activity activity;
    protected List<Article> item;

    public ArticlesListAdapter(Activity activity, List<Article> item) {
        this.activity = activity;
        this.item = item;
    }

    public void clear() {
        item.clear();
    }

    @Override
    public int getCount() {
        return item.size();
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
        return null;
    }
}
