package com.example.marketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {
    // Declaration
    FloatingActionButton fabAdd;
    SearchView searchItem;
    ImageButton btnCart, btnUser;
    MainAdapter mainAdapter;
    RecyclerView rvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization
        fabAdd = findViewById(R.id.fabAdd);
        searchItem = findViewById(R.id.searchItem);
        btnCart = findViewById(R.id.btnCart);
        btnUser = findViewById(R.id.btnUser);
        rvOutput = findViewById(R.id.rvOutput);

        // Recycler View
        Query query = FirebaseDatabase.getInstance().getReference().child("productDatabase").child("productItem").orderByChild("productName");

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(query, MainModel.class)
                        .build();

        rvOutput.setLayoutManager(new GridLayoutManager(this, 2));
        mainAdapter = new MainAdapter(options);
        rvOutput.setAdapter(mainAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }
}