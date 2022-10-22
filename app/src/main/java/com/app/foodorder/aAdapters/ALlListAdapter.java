package com.app.foodorder.aAdapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodorder.ModelCLasses.AddFoodModel;
import com.app.foodorder.ProductPage;
import com.app.foodorder.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ALlListAdapter extends RecyclerView.Adapter<ALlListAdapter.myViewHolder> {

    ArrayList<AddFoodModel>list;

    public ALlListAdapter(ArrayList<AddFoodModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rv, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        AddFoodModel model = list.get(position);
        Glide.with(holder.details.getContext()).load(model.getImage1()).placeholder(R.drawable.upload_image).into(holder.image);
        holder.name.setText(model.getDishName());
        holder.details.setText(model.getDishDescription());

        holder.chlock.setOnClickListener(new View.OnClickListener() {
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

    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,details;
        RatingBar ratting;
        ConstraintLayout chlock;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            details = itemView.findViewById(R.id.details);
            ratting = itemView.findViewById(R.id.ratting);
            chlock = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
