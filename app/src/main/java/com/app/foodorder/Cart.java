package com.app.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Cart extends AppCompatActivity {
    TextView price_show, p_name,address, sub_total,total_price;
    AppCompatButton plus, minus, cart_button;
    TextView total_q;
    ImageView imageView_profuct;
    CardView cardView_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bgcolor));
        }
        price_show = findViewById(R.id.textView4);
        plus = findViewById(R.id.plus_q);
        minus = findViewById(R.id.minus_q);
        total_q = findViewById(R.id.total_q);
        p_name = findViewById(R.id.textView3);
        cart_button = findViewById(R.id.cart_button);
        total_price = findViewById(R.id.total_price);
        sub_total = findViewById(R.id.sub_total);
        imageView_profuct = findViewById(R.id.imageView_profuct);

        Intent i = getIntent();
        String image1 = i.getStringExtra("image1");
        String image2 = i.getStringExtra("image1");
        String image3 = i.getStringExtra("image1");
        String time = i.getStringExtra("time");
        String desc = i.getStringExtra("desc");
        int price = i.getIntExtra("price",0);
        int fstprice = i.getIntExtra("fstprice", 0);
        String name = i.getStringExtra("name");
        String total = i.getStringExtra("total");
        String key = i.getStringExtra("key");
        Picasso.get().load(image1).into(imageView_profuct);
        price_show.setText(price +" RS");
        total_q.setText(total);
        p_name.setText(name);
        sub_total.setText(price + " RS");
        total_price.setText((price + 40)+ " RS");


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int q = Integer.parseInt(total_q.getText().toString());
                if (q!=1) {
                    int qq = q - 1;
                    total_q.setText(qq + "");
                    int pricee = fstprice * qq;
                    price_show.setText(pricee + " RS");
                    sub_total.setText(pricee + " RS");
                    total_price.setText((pricee +40)+" RS");
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int q = Integer.parseInt(total_q.getText().toString());
                int qq = q+1;
                total_q.setText(qq+"");
                int pricee = fstprice *qq;
                price_show.setText(pricee +" RS");
                sub_total.setText(pricee + " RS");
                total_price.setText((pricee +40)+" RS");
            }
        });
        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Cart.this, Checkout.class);
                i.putExtra("name", p_name.getText().toString());
                i.putExtra("quantity", total_q.getText().toString());
                i.putExtra("finalPrice", total_price.getText().toString());
                i.putExtra("image", image1);
                i.putExtra("key", key);
                i.putExtra("time", time);
                startActivity(i);
            }
        });

    }
}