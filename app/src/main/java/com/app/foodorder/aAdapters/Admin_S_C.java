package com.app.foodorder.aAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodorder.ModelCLasses.RecomendationNComplaint;
import com.app.foodorder.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Admin_S_C extends RecyclerView.Adapter<Admin_S_C.myViewHolder> {

    ArrayList<RecomendationNComplaint>list;
    String type;

    public Admin_S_C(ArrayList<RecomendationNComplaint> list, String type) {
        this.list = list;
        this.type = type;
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_com_sug, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        RecomendationNComplaint model = list.get(position);
        holder.userName.setText(model.getName());
        holder.userEmail.setText(model.getName());
        holder.userMessage.setText(model.getMessage());
        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(type, "com")) {
                    FirebaseDatabase.getInstance().getReference().child("recommendations and Complaints").child("complaints").child(model.getKey()).removeValue();
                }else if (Objects.equals(type, "rec")){
                    FirebaseDatabase.getInstance().getReference().child("recommendations and Complaints").child("recommendation").child(model.getKey()).removeValue();
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView userName, userEmail, userMessage;
        AppCompatButton complete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_email);
            userEmail = itemView.findViewById(R.id.user_name);
            userMessage = itemView.findViewById(R.id.message);
            complete = itemView.findViewById(R.id.solve);
        }
    }
}
