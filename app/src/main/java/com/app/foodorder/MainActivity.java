package com.app.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView view;
    CardView floattingdesign;
    LinearLayout clicked_view;
    TextView floatstatus;
    float xDown=0, yDown=0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.nav_view);
        floatstatus = findViewById(R.id.floatstatus);
        floattingdesign = findViewById(R.id.floattingdesign);
        clicked_view = findViewById(R.id.clicked_view);
        view.setItemIconTintList(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new HomeFragmant()).commit();
        Intent i = getIntent();
        String data = i.getStringExtra("past");
        if (Objects.equals(data, "4")){
            getSupportFragmentManager().beginTransaction().replace(R.id.layout, new Profilefragment()).commit();
        }
        view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==R.id.home){
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0,0).replace(R.id.layout, new HomeFragmant()).commit();
                }
                else if (item.getItemId()==R.id.chat){
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0,0).replace(R.id.layout, new ChatFragment()).commit();
                }
                else if (item.getItemId()==R.id.list){
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0,0).replace(R.id.layout, new ListFragmentr()).commit();
                }
                else if (item.getItemId()==R.id.profile){
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0,0).replace(R.id.layout, new Profilefragment()).commit();
                }

                return true;
            }
        });


        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("status").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String status_ = snapshot.getValue(String.class);
                        floatstatus.setText(status_);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        floattingdesign.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){

                    case MotionEvent.ACTION_DOWN:
                        xDown=motionEvent.getX();
                        yDown=motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float movedX, movedY;
                        movedX=motionEvent.getX();
                        movedY=motionEvent.getY();

                        float distenceX = movedX-xDown;
                        float distanceY = movedY-yDown;

                        floattingdesign.setX(floattingdesign.getX()+distenceX);
                        floattingdesign.setY(floattingdesign.getY()+distanceY);

                        xDown = movedX;
                        yDown = movedY;

                        break;


                }
                return true;
            }
        });


        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Active Orders").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            floattingdesign.setVisibility(View.VISIBLE);
                            clicked_view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(MainActivity.this, Preparing_food.class));
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}