package com.app.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.foodorder.ModelCLasses.PlaceOrderModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class Checkout extends AppCompatActivity {
    TextView view_address,dish_name,item_quantity,total_price_;
    EditText edit_address;
    AppCompatButton confirm_order;
    RadioGroup select_method;
    String me = "null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bgcolor));
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Order Status", "Order Status", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        view_address = findViewById(R.id.view_address);
        edit_address = findViewById(R.id.edit_address);
        dish_name = findViewById(R.id.dish_name);
        select_method = findViewById(R.id.select_method);
        item_quantity = findViewById(R.id.item_quantity);
        total_price_ = findViewById(R.id.total_price_);
        confirm_order = findViewById(R.id.confirm_order);
        ProgressDialog dialog = new ProgressDialog(Checkout.this);
        dialog.setMessage("Placing your Order...!");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String key_ = i.getStringExtra("key");
        String image = i.getStringExtra("image");
        String price = i.getStringExtra("finalPrice");
        String quantity = i.getStringExtra("quantity");
        String time = i.getStringExtra("time");
        dish_name.setText(name);
        total_price_.setText(price);
        item_quantity.setText(quantity);



        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                view_address.setText(snapshot.child("address").getValue(String.class));
                                edit_address.setText(snapshot.child("address").getValue(String.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
        view_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_address.setVisibility(View.VISIBLE);
                view_address.setVisibility(View.GONE);
            }
        });



        select_method.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.code:
                        me = "COD";
                        break;

                    case R.id.cp:
                        me = "Online Play";
                        break;
                    default:
                        me  = "null";
                        break;
                }
            }
        });

        confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (me=="null"){
                    Toast.makeText(Checkout.this, "Please Select Payment Method", Toast.LENGTH_SHORT).show();
                }else{
                dialog.show();
                String orderBy = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String orderName = dish_name.getText().toString();
                String orderQuantity = item_quantity.getText().toString();
                String orderPrice = total_price_.getText().toString();
                String orderAddress = edit_address.getText().toString();
                String prepairTime = time;
                String dishKey = key_;
                String orderStatus ="We are Preparing Your Food";
                String key = FirebaseDatabase.getInstance().getReference().child("key").push().getKey();
                PlaceOrderModel model = new PlaceOrderModel(orderBy, orderName, orderQuantity, orderPrice, orderAddress, prepairTime, dishKey ,key, orderStatus, "0");
                FirebaseDatabase.getInstance().getReference().child("Active Order").child(key).setValue(model);
                FirebaseDatabase.getInstance().getReference().child("Users").child(orderBy).child("status").setValue(orderStatus);
                FirebaseDatabase.getInstance().getReference().child("Users").child(orderBy).child("Active Orders").child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dialog.dismiss();
                        Toast.makeText(Checkout.this, "Order Placed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Checkout.this, Preparing_food.class);
                        intent.setAction(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        PendingIntent pendingIntent = PendingIntent.getActivity(Checkout.this, 0,
                                intent, PendingIntent.FLAG_MUTABLE);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(Checkout.this, "Order Status");
                        builder.setContentText("Order Status");
                        builder.setContentText("your Order is Placed Successfully");
                        builder.setSmallIcon(R.drawable.burger);
                        builder.setAutoCancel(true);
                        builder.setContentIntent(pendingIntent);

                        NotificationManagerCompat manager = NotificationManagerCompat.from(Checkout.this);
                        manager.notify(5, builder.build());

                        startActivity(new Intent(Checkout.this, MainActivity.class));
                    }
                });

            }
                }
        });
    }
}