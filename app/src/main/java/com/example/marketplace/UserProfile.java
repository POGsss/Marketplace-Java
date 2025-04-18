package com.example.marketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserProfile extends AppCompatActivity {
    // Declaration
    ImageButton btnBack, btnSignOut;
    TextView txtName, txtEmail;
    Dialog dLogout, dDelete;
    Button btnCancelLogout, btnYesLogout, btnCancelDelete, btnYesDelete, btnDelete;
    AddedAdapter addedAdapter;
    RecyclerView rvAddedProduct, rvPurchasedProduct;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialization
        btnBack = findViewById(R.id.btnBack);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnDelete = findViewById(R.id.btnDelete);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        rvAddedProduct = findViewById(R.id.rvAddedProduct);
        rvPurchasedProduct = findViewById(R.id.rvPurchasedProduct);

        // Firebase User
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String name = user.getDisplayName();
        String email = user.getEmail();
        String currentUid = user.getUid();

        // Updating UI
        txtName.setText(name);
        txtEmail.setText(email);

        // Recycler View
        Query query = FirebaseDatabase.getInstance().getReference().child("productDatabase").orderByChild("uid").equalTo(currentUid);

        FirebaseRecyclerOptions<AddedModel> options =
                new FirebaseRecyclerOptions.Builder<AddedModel>()
                        .setQuery(query, AddedModel.class)
                        .build();

        addedAdapter = new AddedAdapter(options);

        rvAddedProduct.setItemAnimator(null);
        rvAddedProduct.setLayoutManager(new GridLayoutManager(this, 3));
        rvAddedProduct.setAdapter(addedAdapter);

        // Event Listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLogout.show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dDelete.show();
            }
        });

        // Logout Dialog
        dLogout = new Dialog(UserProfile.this);

        dLogout.setCancelable(true);
        dLogout.setContentView(R.layout.dialog_logout);
        dLogout.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dLogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnCancelLogout = dLogout.findViewById(R.id.btnCancel);
        btnYesLogout = dLogout.findViewById(R.id.btnYes);

        btnCancelLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLogout.dismiss();
            }
        });
        btnYesLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(UserProfile.this, "Logout Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UserProfile.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Delete Dialog
        dDelete = new Dialog(UserProfile.this);

        dDelete.setCancelable(true);
        dDelete.setContentView(R.layout.dialog_delete);
        dDelete.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dDelete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnCancelDelete = dDelete.findViewById(R.id.btnCancel);
        btnYesDelete = dDelete.findViewById(R.id.btnYes);

        btnCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dDelete.dismiss();
            }
        });
        btnYesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Query Products Of Current User
                Query query = FirebaseDatabase.getInstance().getReference().child("productDatabase").orderByChild("uid").equalTo(currentUid);

                // Delete Each Products Of Current User
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                            // Getting Image Url
                            String imageUrl = productSnapshot.child("productImg").getValue(String.class);

                            // Delete Image From Firebase Storage
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                                photoRef.delete();
                            }

                            // Delete Product From Database
                            productSnapshot.getRef().removeValue();
                        }

                        // Delete Users In Users Database
                        FirebaseDatabase.getInstance()
                                .getReference("users")
                                .child(currentUid)
                                .child("userProducts")
                                .removeValue();

                        // Delete The Current User Account
                        user.delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserProfile.this, "Account and all data deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserProfile.this, SignInActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(UserProfile.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UserProfile.this, "Error deleting products", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Start Listening Adapter
    @Override
    protected void onStart() {
        super.onStart();
        if (addedAdapter != null) {
            addedAdapter.startListening();
        }
    }
}