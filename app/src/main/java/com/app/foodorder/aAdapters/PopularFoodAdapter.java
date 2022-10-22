package com.app.foodorder.aAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodorder.ModelCLasses.AddFoodModel;
import com.app.foodorder.ProductPage;
import com.app.foodorder.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopularFoodAdapter extends RecyclerView.Adapter<PopularFoodAdapter.myViewHodler> {

    ArrayList<AddFoodModel> list;
    Context context;

    public PopularFoodAdapter(ArrayList<AddFoodModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public PopularFoodAdapter(ArrayList<AddFoodModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popularfood_rv, parent, false);
        return new myViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHodler holder, int position) {
        AddFoodModel model = list.get(position);
        Glide.with(context).load(model.getImage1()).apply(new RequestOptions().override(300, 300)).into(holder.dishimage);
        holder.dishName.setText(model.getDishName());
        holder.dishPrice.setText(model.getDishPrice() + " RS");
        holder.prepTime.setText(model.getTime() + " Min");
        holder.videde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ProductPage.class);
                i.putExtra("image1", model.getImage1());
                i.putExtra("image2", model.getImage2());
                i.putExtra("image2", model.getImage3());
                i.putExtra("name", model.getDishName());
                i.putExtra("description", model.getDishDescription());
                i.putExtra("price", model.getDishPrice());
                i.putExtra("time", model.getTime());
                i.putExtra("key", model.getKey());
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHodler extends RecyclerView.ViewHolder{
        ImageView dishimage;
        TextView dishPrice, dishName,prepTime;
        RelativeLayout videde;
        public myViewHodler(@NonNull View itemView) {
            super(itemView);
            dishimage = itemView.findViewById(R.id.dishimage);
            dishName = itemView.findViewById(R.id.dishName);
            dishPrice = itemView.findViewById(R.id.dishPrice);
            prepTime = itemView.findViewById(R.id.prepTime);
            videde = itemView.findViewById(R.id.videde);
        }
    }
}
