package com.aguillen.supermarketshoppingmobile.controller;

import android.app.Activity;
import android.content.Context;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aguillen.supermarketshoppingmobile.model.Shopping;

import java.util.ArrayList;
import java.util.List;

public class PagerController extends FragmentPagerAdapter {

    private int numTabs;
    private Context context;
    private Activity activity;
    private ArrayList<String> nameTabsList;
    private ProgressBar pbArticlesList;
    private boolean isArticlesList;
    private List<Shopping> shoppingList;

    public PagerController(
            FragmentManager fm, int numTabs, ArrayList<String> nameTabsList, Context context,
            Activity activity, ProgressBar pbArticlesList, boolean isArticlesList) {
        super(fm);
        this.numTabs = numTabs;
        this.nameTabsList = nameTabsList;
        this.context = context;
        this.activity = activity;
        this.pbArticlesList = pbArticlesList;
        this.isArticlesList = isArticlesList;
    }

    public PagerController(
            FragmentManager fm, int numTabs, ArrayList<String> nameTabsList, Context context,
            Activity activity, ProgressBar pbArticlesList, boolean isArticlesList,
            List<Shopping> shoppingList) {
        super(fm);
        this.numTabs = numTabs;
        this.nameTabsList = nameTabsList;
        this.context = context;
        this.activity = activity;
        this.pbArticlesList = pbArticlesList;
        this.isArticlesList = isArticlesList;
        this.shoppingList = shoppingList;
    }

    @Override
    public Fragment getItem(int position) {
        if(isArticlesList) {
            return new CustomFragment(nameTabsList.get(position), context, activity,
                    pbArticlesList, isArticlesList);
        } else {
            List<Shopping> shoppingListFiltered = filterByCategory(shoppingList, nameTabsList.get(position));
            return new CustomFragment(nameTabsList.get(position), context, activity,
                    pbArticlesList, isArticlesList, shoppingListFiltered);
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    public List<Shopping> getShoppingList() {
        return shoppingList;
    }

    private List<Shopping> filterByCategory(List<Shopping> shoppingList, String category) {
        List<Shopping> shoppingListFiltered = new ArrayList<>();
        for(Shopping shopping : shoppingList) {
            if(shopping.getArticle().getCategory().equals(category)) {
                shoppingListFiltered.add(shopping);
            }
        }
        return shoppingListFiltered;
    }
}
