package com.app.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.foodorder.ProfileActivities.Account_Information;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class Profile extends AppCompatActivity {
    ImageView logout_image;
    LinearLayout logout_layout,account_information,edit_address,order_history;
    TextView user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bgcolor));
        }
        edit_address = findViewById(R.id.edit_address);
        logout_image = findViewById(R.id.logout_image);
        logout_layout = findViewById(R.id.logout_layout);
        user_name = findViewById(R.id.user_name);
        order_history = findViewById(R.id.order_history);
        account_information = findViewById(R.id.account_information);


        account_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Account_Information.class));
            }
        });


        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                user_name.setText(snapshot.child("name").getValue(String.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        order_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, MainActivity.class);
                i.putExtra("past", "4");
                startActivity(i);
            }
        });

        logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, MainLoginScreen.class));
            }
        });
        logout_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, MainLoginScreen.class));
            }
        });

        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DialogPlus dialogPlus = DialogPlus.newDialog(Profile.this)
                        .setContentHolder(new ViewHolder(R.layout.activity_pop_up_address)).setExpanded(true, 1300)
                        .create();
                View v = dialogPlus.getHolderView();
                EditText new_address = v.findViewById(R.id.new_address);
                AppCompatButton save = v.findViewById(R.id.savesave);
                RadioButton address = v.findViewById(R.id.address_old);
                TextView edit_address = v.findViewById(R.id.textView15);

                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        address.setText(snapshot.child("address").getValue(String.class));
                                        new_address.setText(snapshot.child("address").getValue(String.class));
                                        edit_address.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                save.setText("Save");
                                                address.setVisibility(View.GONE);
                                                new_address.setVisibility(View.VISIBLE);
                                            }
                                        });
                                        save.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .child("address").setValue(new_address.getText().toString());
                                                dialogPlus.dismiss();
                                            }
                                        });


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                dialogPlus.show();



            }
        });
    }
}