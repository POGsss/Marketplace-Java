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
    TextView txtProductName, txtProductPrice, txtProductDescription, txtProductCount, txtProductCategory, txtProductCondition, txtProductFrom, txtProductWarranty;

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
        txtProductDescription = findViewById(R.id.txtProductDescription);
        txtProductCategory = findViewById(R.id.txtProductCategory);
        txtProductCount = findViewById(R.id.txtProductCount);
        txtProductCondition = findViewById(R.id.txtProductCondition);
        txtProductFrom = findViewById(R.id.txtProductFrom);
        txtProductWarranty = findViewById(R.id.txtProductWarranty);

        // Getting Intent Data
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String description = getIntent().getStringExtra("description");
        String category = getIntent().getStringExtra("category");
        String count = getIntent().getStringExtra("count");
        String condition = getIntent().getStringExtra("condition");
        String from = getIntent().getStringExtra("from");
        String warranty = getIntent().getStringExtra("warranty");

        // Passing Intent Data To Views
        txtProductName.setText(name);
        txtProductPrice.setText("$" + price);
        txtProductDescription.setText(description);
        txtProductCategory.setText("Category: " + category);
        txtProductCount.setText("Quantity: " + count);
        txtProductCondition.setText("Condition: " + condition);
        txtProductFrom.setText("Ships From: " + from);
        txtProductWarranty.setText("Warranty Type: " + warranty);

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