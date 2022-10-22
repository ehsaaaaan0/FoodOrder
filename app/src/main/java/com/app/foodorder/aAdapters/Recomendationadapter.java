package com.app.foodorder.aAdapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.foodorder.Complaint;
import com.app.foodorder.LoginTabFragment;
import com.app.foodorder.Recomendation;
import com.app.foodorder.SignupTabFragment;

public class Recomendationadapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;



    public Recomendationadapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }
    @Override
    public int getCount() {
        return totalTabs;
    }

    @NonNull
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Recomendation recomendation = new Recomendation();
                return recomendation;
            case 1:
                Complaint complaint = new Complaint();
                return complaint;
            default:
                return null;
        }
    }
}
