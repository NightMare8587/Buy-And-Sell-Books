package com.consumers.librarymanagementsystem.Home;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.consumers.librarymanagementsystem.Home.Frags.BuyBooksFrag;
import com.consumers.librarymanagementsystem.Home.Frags.MyAccountFrag;
import com.consumers.librarymanagementsystem.Home.Frags.SearchBooksFrag;

class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BuyBooksFrag buyBooksFrag = new BuyBooksFrag();
                return buyBooksFrag;
            case 2:
                MyAccountFrag myAccountFrag = new MyAccountFrag();
                return myAccountFrag;
            case 1:
                SearchBooksFrag searchBooksFrag = new SearchBooksFrag();
                return searchBooksFrag;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}