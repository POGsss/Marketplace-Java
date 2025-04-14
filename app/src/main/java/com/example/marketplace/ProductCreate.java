package com.example.marketplace;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductCreate extends AppCompatActivity {
    // Declaration
    ImageButton btnBack, btnImgUpload;
    EditText edtName, edtPrice, edtDescription, edtQuantity, edtFrom;
    Spinner spCategory, spCondition, spWarranty;
    Button btnAddProduct;
    private Uri imageUri;

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

        // Image Picker Launcher
        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        btnImgUpload.setImageURI(imageUri);
                    }
                }
        );

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
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                imagePickerLauncher.launch(intent);
            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    String fileName = UUID.randomUUID().toString();
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference("productImages/productItem/" + fileName);

                    storageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            saveProductToDatabase(imageUrl);
                        });
                    }).addOnFailureListener(e -> {
                        Toast.makeText(ProductCreate.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    Toast.makeText(ProductCreate.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Save Product To Database
    private void saveProductToDatabase(String imageUrl) {
        if (
                edtName.getText().toString().trim().isEmpty() ||
                edtPrice.getText().toString().trim().isEmpty() ||
                edtDescription.getText().toString().trim().isEmpty() ||
                edtQuantity.getText().toString().trim().isEmpty() ||
                edtFrom.getText().toString().trim().isEmpty() ||
                spCategory.getSelectedItem().toString().equals("Select Category") ||
                spCondition.getSelectedItem().toString().equals("Select Condition") ||
                spWarranty.getSelectedItem().toString().equals("Select Warranty") ||
                imageUrl == null || imageUrl.isEmpty()
        ) {
            Toast.makeText(this, "Please fill in all fields and upload an image", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("productName", edtName.getText().toString());
        map.put("productPrice", edtPrice.getText().toString());
        map.put("productDescription", edtDescription.getText().toString());
        map.put("productQuantity", edtQuantity.getText().toString());
        map.put("productFrom", edtFrom.getText().toString());
        map.put("productCategory", spCategory.getSelectedItem().toString());
        map.put("productCondition", spCondition.getSelectedItem().toString());
        map.put("productWarranty", spWarranty.getSelectedItem().toString());
        map.put("productImg", imageUrl);
        map.put("productSearch", edtName.getText().toString().toLowerCase());

        FirebaseDatabase.getInstance().getReference().child("productDatabase").child("productItem").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ProductCreate.this, "Product uploaded successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductCreate.this, "Failed to upload product", Toast.LENGTH_SHORT).show();
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