package com.app.foodorder.AdminPanel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.app.foodorder.R;
import com.google.android.material.tabs.TabLayout;

public class Complaint_And_Suggestion extends AppCompatActivity {
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_and_suggestion);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_r_c, new Recomendation_admin()).commit();
        tabLayout = findViewById(R.id.tab_layout_com_suggestion);
        tabLayout.addTab(tabLayout.newTab().setText("Recommendation"));
        tabLayout.addTab(tabLayout.newTab().setText("Complaint"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    getSupportFragmentManager().beginTransaction().replace(R.id.view_r_c, new Recomendation_admin()).commit();
                }else if (tab.getPosition()==1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.view_r_c, new COmplaint_Admin()).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}