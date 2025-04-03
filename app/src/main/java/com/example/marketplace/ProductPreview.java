package com.example.marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ProductPreview extends AppCompatActivity {
    // Declaration
    ImageButton btnBack;
    Button btnAddToCart, btnBuyNow;
    ImageView imgProduct;
    TextView txtProductName, txtProductPrice, txtProductDesc, txtProductCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_preview);

        // Initialization
        btnBack = findViewById(R.id.btnBack);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        imgProduct = findViewById(R.id.imgProduct);
        txtProductName = findViewById(R.id.txtProductName);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        txtProductDesc = findViewById(R.id.txtProductDesc);
        txtProductCount = findViewById(R.id.txtProductCount);

        // Getting Intent Data
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String description = getIntent().getStringExtra("description");
        String count = getIntent().getStringExtra("count");

        // Passing Intent Data To Views
        txtProductName.setText(name);
        txtProductPrice.setText("$" + price);
        txtProductDesc.setText(description);
        txtProductCount.setText("Quantity: " + count);

        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .into(imgProduct);

        // Event Listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProductPreview.this, "Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProductPreview.this, "Proceed To Checkout", Toast.LENGTH_SHORT).show();
            }
        });
    }
}