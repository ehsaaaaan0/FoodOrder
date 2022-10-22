package com.app.foodorder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodorder.ModelCLasses.PlaceOrderModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class PastOrderAdapter extends RecyclerView.Adapter<PastOrderAdapter.myViewHolder> {


    ArrayList<PlaceOrderModel>list;
    String type;

    public PastOrderAdapter(ArrayList<PlaceOrderModel> list, String type) {
        this.list = list;
        this.type = type;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_order_rv, parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        PlaceOrderModel model = list.get(position);
        FirebaseDatabase.getInstance().getReference().child("All Dishes").child(model.getDishKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(holder.order_image.getContext()).load(snapshot.child("image1").getValue(String.class)).into(holder.order_image);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        if (Objects.equals(type, "user")){
            if (Objects.equals(model.getRating(), "0")){
                holder.ratting_text.setVisibility(View.VISIBLE);
                holder.give_rating.setVisibility(View.VISIBLE);
                holder.textView13.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.GONE);
                holder.totalrating.setVisibility(View.GONE);

                holder.give_rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        holder.textView13.setText(i +"");
                        holder.give_rating.setProgress(i);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        FirebaseDatabase.getInstance().getReference().child("Admin Rating").child("rating").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String c = snapshot.getValue(String.class);
                                    double d = Double.valueOf(c);
                                    String cc = holder.textView13.getText().toString();
                                    double dd = Double.valueOf(cc);
                                    double fin = (d + dd) / 5;
                                    double lfin = d + fin;
                                    String save = String.valueOf(lfin);
                                    FirebaseDatabase.getInstance().getReference().child("Admin Rating").child("rating").setValue(save);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getOrderBy()).child("Previous Orders").child(model.getKey()).child("rating").setValue(holder.textView13.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Complete Order").child(model.getKey()).child("rating").setValue(holder.textView13.getText().toString());


                       }
                });


            }else{
                holder.imageView.setVisibility(View.VISIBLE);
                holder.totalrating.setVisibility(View.VISIBLE);
                holder.totalrating.setText(model.getRating());
            }
        }else if (Objects.equals(type, "admin")){
            holder.imageView.setVisibility(View.VISIBLE);
            holder.totalrating.setVisibility(View.VISIBLE);
            holder.totalrating.setText(model.getRating());
            holder.reorder.setVisibility(View.GONE);

            holder.click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Details")
                            .setMessage("Order Status : "+model.getOrderStatus()+"\n"+
                            "Order By : " + model.getOrderBy() +"\n"
                            + "Order Name : " + model.getOrderName() +"\n"+
                            "Order Price : " + model.getOrderPrice()+"\n"+
                                    "Customer Address : " + model.getOrderAddress() + "\n\n")
                            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });


        }
        holder.dish_name.setText(model.getOrderName());
        holder.order_price.setText(model.getOrderPrice());
        holder.dish_secription.setText(model.getOrderQuantity());




        holder.reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Checkout.class);
                i.putExtra("name", model.getOrderName());
                i.putExtra("key", model.getDishKey());
                i.putExtra("finalPrice", model.getOrderPrice());
                i.putExtra("quantity", model.getOrderQuantity());
                i.putExtra("time", "25 Min");
                view.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView order_image,imageView;
        TextView dish_secription, dish_name,order_price,totalrating,textView13;
        AppCompatButton reorder;
        SeekBar give_rating;
        TextView ratting_text;
        CardView click;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            order_image = itemView.findViewById(R.id.order_image);
            dish_name = itemView.findViewById(R.id.dish_name_past);
            dish_secription = itemView.findViewById(R.id.dish_secription);
            order_price = itemView.findViewById(R.id.order_price);
            reorder = itemView.findViewById(R.id.reorder);
            ratting_text = itemView.findViewById(R.id.textView12);
            give_rating = itemView.findViewById(R.id.give_rating);
            imageView = itemView.findViewById(R.id.imageView);
            totalrating = itemView.findViewById(R.id.totalrating);
            textView13 = itemView.findViewById(R.id.textView13);
            click = itemView.findViewById(R.id.show_order_details);
        }
    }
}