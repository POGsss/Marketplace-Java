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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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
        Query query = FirebaseDatabase.getInstance().getReference().child("productDatabase").child("productItem").orderByChild("uid").equalTo(currentUid);

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

        dLogout.setCancelable(false);
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

        dDelete.setCancelable(false);
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
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserProfile.this, "Account Deactivated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserProfile.this, SignInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
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