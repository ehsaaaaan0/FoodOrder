package com.app.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.foodorder.AdminPanel.ActiveOrders;
import com.app.foodorder.AdminPanel.Add_food;
import com.app.foodorder.AdminPanel.Complaint_And_Suggestion;
import com.app.foodorder.AdminPanel.PastOrders;
import com.app.foodorder.AdminPanel.Pending_Dilivery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminDashboard extends AppCompatActivity {
    LinearLayout add_new,active_orders,previous_orders,complaints_suggestion,pendingDilivery;
    TextView complete_orders,activeOrders,percent;
    ImageView logout;
    LinearLayout active_order_open,complete_orders_open;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Complaint Notification", "Complaint Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = AdminDashboard.this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bordercolor));
        }
            add_new = findViewById(R.id.add_new_dish);
            active_orders = findViewById(R.id.active_orders);
            active_order_open = findViewById(R.id.active_order_open);
            logout = findViewById(R.id.logout_image_admin);
            previous_orders = findViewById(R.id.previous_orders);
            complaints_suggestion = findViewById(R.id.complaints_suggestion);
            pendingDilivery = findViewById(R.id.pendingDilivery);
            complete_orders = findViewById(R.id.complete_orders);
            activeOrders = findViewById(R.id.activeOrders);
            complete_orders_open = findViewById(R.id.complete_orders_open);


        Intent intent =getIntent();

        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("Complete Order").exists()){
                        complete_orders.setText(snapshot.child("Complete Order").getChildrenCount() +"");
                    }
                    if (snapshot.child("Active Order").exists()){
                        activeOrders.setText(snapshot.child("Active Order").getChildrenCount() +"");
                        if (intent.hasExtra("value")) {
                        }else{
                        Intent intent = new Intent(AdminDashboard.this, ActiveOrders.class);
                        intent.setAction(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);


                            PendingIntent pendingIntent = PendingIntent.getActivity(AdminDashboard.this, 0,
                                    intent, PendingIntent.FLAG_MUTABLE);

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(AdminDashboard.this, "Complaint Notification");
                            builder.setContentTitle("Active Order");
                            builder.setContentText("You Have An Active Order Please Complete it ASAP");
                            builder.setSmallIcon(R.drawable.burger);
                            builder.setAutoCancel(true);
                            builder.setContentIntent(pendingIntent);

                            NotificationManagerCompat compat = NotificationManagerCompat.from(AdminDashboard.this);
                            compat.notify(10, builder.build());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            previous_orders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AdminDashboard.this, PastOrders.class));
                }
            });

            complaints_suggestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AdminDashboard.this, Complaint_And_Suggestion.class));
                }
            });
            pendingDilivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AdminDashboard.this, Pending_Dilivery.class));
                }
            });
            active_orders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AdminDashboard.this, ActiveOrders.class));
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(AdminDashboard.this, MainLoginScreen.class));
                }
            });
            add_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AdminDashboard.this, Add_food.class));
                }
            });
            active_order_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AdminDashboard.this, ActiveOrders.class));
                }
            });
            complete_orders_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AdminDashboard.this, PastOrders.class));
                }
            });
        }

}