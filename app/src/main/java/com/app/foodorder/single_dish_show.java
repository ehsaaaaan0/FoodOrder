package com.app.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.foodorder.ModelCLasses.AddFoodModel;
import com.app.foodorder.aAdapters.ALlListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class single_dish_show extends AppCompatActivity {
    RecyclerView show_single;
    EditText search_single;
    ArrayList<AddFoodModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_dish_show);
        show_single = findViewById(R.id.show_single);
        search_single = findViewById(R.id.search_single);


        search_single.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {


                    searchh(editable.toString());
//                    seachA(editable.toString());
                } else {
                    searchh("");
                }
            }
        });

        Intent i =getIntent();
        String dish = i.getStringExtra("dish");

        if (Objects.equals(dish, "pizza")){


            list=new ArrayList<>();
            ALlListAdapter adapter = new ALlListAdapter(list);
            LinearLayoutManager liner = new LinearLayoutManager(this);
            show_single.setLayoutManager(liner);
            FirebaseDatabase.getInstance().getReference().child("pizza").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            AddFoodModel model = snapshot1.getValue(AddFoodModel.class);
                            list.add(model);
                        }
                        show_single.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





        }else if (Objects.equals(dish, "burger")){

            list=new ArrayList<>();
            ALlListAdapter adapter = new ALlListAdapter(list);
            LinearLayoutManager liner = new LinearLayoutManager(this);
            show_single.setLayoutManager(liner);
            FirebaseDatabase.getInstance().getReference().child("burger").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            AddFoodModel model = snapshot1.getValue(AddFoodModel.class);
                            list.add(model);
                        }
                        show_single.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }else if (Objects.equals(dish, "chicken")){


            list=new ArrayList<>();
            ALlListAdapter adapter = new ALlListAdapter(list);
            LinearLayoutManager liner = new LinearLayoutManager(this);
            show_single.setLayoutManager(liner);
            FirebaseDatabase.getInstance().getReference().child("chicken").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            AddFoodModel model = snapshot1.getValue(AddFoodModel.class);
                            list.add(model);
                        }
                        show_single.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }else if (Objects.equals(dish, "fries")){
            list=new ArrayList<>();
            ALlListAdapter adapter = new ALlListAdapter(list);
            LinearLayoutManager liner = new LinearLayoutManager(this);
            show_single.setLayoutManager(liner);
            FirebaseDatabase.getInstance().getReference().child("fries").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            AddFoodModel model = snapshot1.getValue(AddFoodModel.class);
                            list.add(model);
                        }
                        show_single.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else if (Objects.equals(dish, "ice")){


            list=new ArrayList<>();
            ALlListAdapter adapter = new ALlListAdapter(list);
            LinearLayoutManager liner = new LinearLayoutManager(this);
            show_single.setLayoutManager(liner);
            FirebaseDatabase.getInstance().getReference().child("icecream").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            AddFoodModel model = snapshot1.getValue(AddFoodModel.class);
                            list.add(model);
                        }
                        show_single.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }else if (Objects.equals(dish, "drink")){


            list=new ArrayList<>();
            ALlListAdapter adapter = new ALlListAdapter(list);
            LinearLayoutManager liner = new LinearLayoutManager(this);
            show_single.setLayoutManager(liner);
            FirebaseDatabase.getInstance().getReference().child("drink").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            AddFoodModel model = snapshot1.getValue(AddFoodModel.class);
                            list.add(model);
                        }
                        show_single.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void searchh(String toString) {

        FirebaseDatabase.getInstance().getReference().child("All Dishes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren())
                {
                    list.clear();
                    for (DataSnapshot dss : dataSnapshot.getChildren())
                    {

                        AddFoodModel register = dss.getValue(AddFoodModel.class);
                        if (register.getDishName().toLowerCase().contains(toString.toLowerCase())){
                            list.add(register);
                        }


                    }
                    ALlListAdapter adapter = new ALlListAdapter(list);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(single_dish_show.this);
                    show_single.setLayoutManager(linearLayoutManager);
                    show_single.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}