package com.app.foodorder.AdminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.foodorder.ModelCLasses.PlaceOrderModel;
import com.app.foodorder.R;
import com.app.foodorder.aAdapters.ActiveOrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActiveOrders extends AppCompatActivity {
    RecyclerView rv  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_orders);
        rv = findViewById(R.id.recyclerViewactive);

        ArrayList<PlaceOrderModel>list = new ArrayList<>();
        ActiveOrderAdapter adapter = new ActiveOrderAdapter(list);
        LinearLayoutManager liner = new LinearLayoutManager(this);
        rv.setLayoutManager(liner);
        FirebaseDatabase.getInstance().getReference().child("Active Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        PlaceOrderModel model = snapshot1.getValue(PlaceOrderModel.class);
                        list.add(model);
                    }
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}