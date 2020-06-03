package com.aguillen.supermarketshoppingmobile.controller;

import android.app.Activity;
import android.content.Context;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerController extends FragmentPagerAdapter {

    private int numTabs;
    private Context context;
    private Activity activity;
    private ArrayList<String> nameTabsList;
    private ProgressBar pbArticlesList;

    public PagerController(
            FragmentManager fm, int numTabs, ArrayList<String> nameTabsList,
            Context context, Activity activity, ProgressBar pbArticlesList) {
        super(fm);
        this.numTabs = numTabs;
        this.nameTabsList = nameTabsList;
        this.context = context;
        this.activity = activity;
        this.pbArticlesList = pbArticlesList;
    }

    @Override
    public Fragment getItem(int position) {
        return new CustomFragment(nameTabsList.get(position), context, activity, pbArticlesList);
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
