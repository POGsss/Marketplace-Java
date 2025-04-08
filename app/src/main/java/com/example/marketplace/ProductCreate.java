package com.example.marketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ProductCreate extends AppCompatActivity {
    // Declaration
    ImageButton btnBack, btnImgUpload;
    EditText edtName, edtPrice, edtDescription, edtQuantity, edtFrom;
    Spinner spCategory, spCondition, spWarranty;
    Button btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_create);

        // Initialization
        btnBack = findViewById(R.id.btnBack);
        btnImgUpload = findViewById(R.id.btnImgUpload);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtFrom = findViewById(R.id.edtFrom);
        spCategory = findViewById(R.id.spCategory);
        spCondition = findViewById(R.id.spCondition);
        spWarranty = findViewById(R.id.spWarranty);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        // Spinner Array
        setupSpinnerWithHint(spCategory, R.array.product_category);
        setupSpinnerWithHint(spCondition, R.array.product_condition);
        setupSpinnerWithHint(spWarranty, R.array.product_warranty);

        // Event Listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnImgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    // Spinner Hint
    private void setupSpinnerWithHint(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this,
                R.layout.spinner_selected_item,
                getResources().getStringArray(arrayResId)
        ) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.parseColor("#E67E22"));
                } else {
                    tv.setTextColor(Color.parseColor("#000000"));
                }
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.parseColor("#E67E22"));
                } else {
                    tv.setTextColor(Color.parseColor("#000000"));
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }
}