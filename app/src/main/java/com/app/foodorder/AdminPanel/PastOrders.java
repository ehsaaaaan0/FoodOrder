package com.app.foodorder.AdminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.foodorder.ModelCLasses.PlaceOrderModel;
import com.app.foodorder.PastOrderAdapter;
import com.app.foodorder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PastOrders extends AppCompatActivity {


    RecyclerView rc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);
        rc = findViewById(R.id.com_rc);


        ArrayList<PlaceOrderModel>list = new ArrayList();
        PastOrderAdapter adapter = new PastOrderAdapter(list, "admin");
        LinearLayoutManager liner = new LinearLayoutManager(this);
        rc.setLayoutManager(liner);
        FirebaseDatabase.getInstance().getReference().child("Complete Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        PlaceOrderModel model = snapshot1.getValue(PlaceOrderModel.class);
                        list.add(model);
                    }
                    rc.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}