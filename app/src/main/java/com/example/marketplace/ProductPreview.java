package com.example.marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.Glide;

public class ProductPreview extends AppCompatActivity {
    // Declaration
    ImageButton btnBack;
    Button btnAddToCart, btnBuyNow;
    ImageView imgProduct;
    TextView txtProductName, txtProductPrice, txtProductDescription, txtProductStock, txtProductCategory, txtProductCondition, txtProductFrom, txtProductWarranty;

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
        txtProductStock = findViewById(R.id.txtProductStock);
        txtProductCondition = findViewById(R.id.txtProductCondition);
        txtProductWarranty = findViewById(R.id.txtProductWarranty);
        txtProductFrom = findViewById(R.id.txtProductFrom);

        // Getting Intent Data
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String description = getIntent().getStringExtra("description");
        String category = getIntent().getStringExtra("category");
        String stock = getIntent().getStringExtra("stock");
        String condition = getIntent().getStringExtra("condition");
        String warranty = getIntent().getStringExtra("warranty");
        String from = getIntent().getStringExtra("from");

        // Passing Intent Data To Views
        Glide.with(this).load(imageUrl).placeholder(R.drawable.image_placeholder).into(imgProduct);

        txtProductName.setText(name);
        txtProductPrice.setText("$" + price);
        txtProductDescription.setText(description);
        txtProductCategory.setText("Category: " + category);
        txtProductStock.setText("Quantity: " + stock);
        txtProductCondition.setText("Condition: " + condition);
        txtProductWarranty.setText("Warranty Type: " + warranty);
        txtProductFrom.setText("Ships From: " + from);

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