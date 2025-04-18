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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    EditText edtName, edtPrice, edtDescription, edtStock, edtFrom;
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
        edtStock = findViewById(R.id.edtStock);
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
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
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
                        Toast.makeText(ProductCreate.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    Toast.makeText(ProductCreate.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Save Product To Database
    private void saveProductToDatabase(String imageUrl) {
        // Check If All Fields Are Filled
        if (
                imageUrl == null || imageUrl.isEmpty() ||
                edtName.getText().toString().trim().isEmpty() ||
                edtPrice.getText().toString().trim().isEmpty() ||
                edtDescription.getText().toString().trim().isEmpty() ||
                spCategory.getSelectedItem().toString().equals("Select Category") ||
                edtStock.getText().toString().trim().isEmpty() ||
                spCondition.getSelectedItem().toString().equals("Select Condition") ||
                spWarranty.getSelectedItem().toString().equals("Select Warranty") ||
                edtFrom.getText().toString().trim().isEmpty()
        ) {
            Toast.makeText(this, "Please fill in all fields and upload an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get User ID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;
        String uid = user.getUid();

        // Generate Product ID
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("productDatabase").child("productItem");
        String productId = productRef.push().getKey();

        // Create A Map For Product
        Map<String, Object> map = new HashMap<>();
        map.put("productSearch", edtName.getText().toString().toLowerCase());
        map.put("uid", uid);
        map.put("productImg", imageUrl);
        map.put("productName", edtName.getText().toString());
        map.put("productPrice", edtPrice.getText().toString());
        map.put("productDescription", edtDescription.getText().toString());
        map.put("productCategory", spCategory.getSelectedItem().toString());
        map.put("productStock", Integer.parseInt(edtStock.getText().toString()));
        map.put("productCondition", spCondition.getSelectedItem().toString());
        map.put("productWarranty", spWarranty.getSelectedItem().toString());
        map.put("productFrom", edtFrom.getText().toString());

        // Check If Product ID Is Null
        if (productId == null) {
            Toast.makeText(this, "Failed to generate product ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Upload Product To Database
        FirebaseDatabase.getInstance().getReference().child("productDatabase")
                .child(productId)
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Update User Products
                        FirebaseDatabase.getInstance().getReference("userDatabase")
                                .child(uid)
                                .child("userProducts")
                                .child(productId)
                                .setValue(true);

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
                ProductCreate.this,
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