package com.app.foodorder.aAdapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodorder.AdminDashboard;
import com.app.foodorder.AdminPanel.Pending_Dilivery;
import com.app.foodorder.ModelCLasses.PlaceOrderModel;
import com.app.foodorder.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderAdapter.myViewHolder> {

    ArrayList<PlaceOrderModel>list;
    int value;

    public ActiveOrderAdapter(){  }
    public ActiveOrderAdapter(ArrayList<PlaceOrderModel> list) {
        this.list = list;
    }
    public ActiveOrderAdapter(ArrayList<PlaceOrderModel> list, int value) {
        this.list = list;
        this.value = value;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_rv, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        PlaceOrderModel model = list.get(position);

        if (value==1){
            if (!Objects.equals(model.getOrderStatus(), "Our Rider is on Its Way")){
                holder.onitsway.setVisibility(View.GONE);
            }
            holder.orderid.setText(model.getKey());
            holder.dishNm.setText(model.getOrderName());
            holder.quantity.setText(model.getOrderQuantity());
            holder.address.setText(model.getOrderAddress());
            holder.price.setText(model.getOrderPrice());
            holder.status.setVisibility(View.GONE);
            holder.statust.setVisibility(View.GONE);
            holder.ready.setText("Deliver");
            holder.ready.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Item Marked As Delivered", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference().child("Users").child(model.getOrderBy()).child("Active Orders").removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Active Order").child(model.getKey()).removeValue();
                    String key =FirebaseDatabase.getInstance().getReference().child("Complete Order").push().getKey();
                    String status = "SuccessFully Dilivered";
                    PlaceOrderModel orderModel = new PlaceOrderModel(model.getOrderBy(), model.getOrderName(), model.getOrderQuantity(), model.getOrderPrice(), model.getOrderAddress(), model.getDishKey(),key, status);
                    orderModel.setRating("0");
                    FirebaseDatabase.getInstance().getReference().child("Complete Order").child(key).setValue(orderModel);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(model.getOrderBy()).child("Previous Orders").child(key).setValue(orderModel);
                    Intent i =new Intent(view.getContext(), AdminDashboard.class);
                    i.putExtra("value", 1);
                    view.getContext().startActivity(i);
                }
            });
        }else {

            holder.orderid.setText(model.getKey());
            holder.dishNm.setText(model.getOrderName());
            holder.quantity.setText(model.getOrderQuantity());
            holder.address.setText(model.getOrderAddress());
            holder.price.setText(model.getOrderPrice());
            holder.status.setText(model.getOrderStatus());


            holder.ready.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference().child("Users").child(model.getOrderBy()).child("status").setValue("Our Rider is on Its way");
                    FirebaseDatabase.getInstance().getReference().child("Users").child(model.getOrderBy()).child("Active Orders")
                            .child(model.getKey()).child("orderStatus").setValue("Our Rider is on Its Way");
                    FirebaseDatabase.getInstance().getReference().child("Active Order").child(model.getKey()).child("orderStatus").setValue("Our Rider is on Its Way");
                    Toast.makeText(view.getContext(), "Order is Ready to Deliver", Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(view.getContext(), AdminDashboard.class);
                    i.putExtra("value", 1);
                    view.getContext().startActivity(i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView orderid,dishNm, quantity, address, price,status,statust;
        AppCompatButton ready;
        ConstraintLayout onitsway;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            orderid  = itemView.findViewById(R.id.orderid);
            dishNm = itemView.findViewById(R.id.dishNm);
            quantity = itemView.findViewById(R.id.maxquanitty);
            address = itemView.findViewById(R.id.daddress);
            price  = itemView.findViewById(R.id.textView11);
            status = itemView.findViewById(R.id.status);
            ready = itemView.findViewById(R.id.ready);
            statust = itemView.findViewById(R.id.textView10);
            onitsway = itemView.findViewById(R.id.onitsway);
        }
    }
}
