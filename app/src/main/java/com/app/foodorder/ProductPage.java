package com.app.foodorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class ProductPage extends AppCompatActivity {
    ImageView goback, image1,image2,image3;
    TextView ddishName,pprice, pptime ,description__;
    TextView total;
    ImageView minus, plus;
    AppCompatButton addtocart;
    int price_=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        goback = findViewById(R.id.goback);
        image1 = findViewById(R.id.image1_);
        image2 = findViewById(R.id.image2_);
        image3 = findViewById(R.id.image3_);
        ddishName = findViewById(R.id.ddishName);
        pprice = findViewById(R.id.pprice);
        pptime = findViewById(R.id.pptime);
        description__ = findViewById(R.id.description__);
        minus = findViewById(R.id.minus);
        total = findViewById(R.id.total);
        plus = findViewById(R.id.plus);
        addtocart = findViewById(R.id.addtocart);

        Intent i = getIntent();
        String image1_ = i.getStringExtra("image1");
        String image2_ = i.getStringExtra("image2");
        String image3_ = i.getStringExtra("image3");
        String dishName = i.getStringExtra("name");
        String deshDescription = i.getStringExtra("description");
        String price = i.getStringExtra("price");
        String time = i.getStringExtra("time");
        String key = i.getStringExtra("key");
        price_=Integer.parseInt(price);

        int fstprice = Integer.parseInt(price);
        Glide.with(this).load(image1_).into(image1);
        Picasso.get().load(image2_).into(image2);
        Picasso.get().load(image3_).into(image3);
        ddishName.setText(dishName);
        pprice.setText(price +" RS");
        pptime.setText(time +" min");
        description__.setText(deshDescription);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!total.getText().toString().equals("1")) {
                    int q = Integer.parseInt(total.getText().toString());
                    int qq = q-1;
                    total.setText(qq + "");
                    int rs = Integer.parseInt(price)*qq;
                    pprice.setText(rs +" Rs");
                    price_ = rs;
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int q = Integer.parseInt(total.getText().toString());
                int qq = q+1;
                total.setText(qq +"");
                int rs = Integer.parseInt(price) * qq;
                pprice.setText(rs +" RS");
                price_=rs;
            }
        });
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductPage.this, Cart.class );
                intent.putExtra("key", key);
                intent.putExtra("total", total.getText().toString());
                intent.putExtra("name", ddishName.getText().toString());
                intent.putExtra("price", price_);
                intent.putExtra("desc", description__.getText().toString());
                intent.putExtra("image1", image1_);
                intent.putExtra("image2", image2_);
                intent.putExtra("image3", image3_);
                intent.putExtra("fstprice", fstprice);
                intent.putExtra("time", pptime.getText().toString());
                startActivity(intent);
            }
        });







        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}