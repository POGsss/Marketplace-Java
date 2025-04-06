package com.example.marketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    ImageButton btnCart, btnProfile;
    MainAdapter mainAdapter;
    RecyclerView rvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization
        fabAdd = findViewById(R.id.fabAdd);
        btnCart = findViewById(R.id.btnCart);
        btnProfile = findViewById(R.id.btnProfile);
        searchItem = findViewById(R.id.searchItem);
        rvMain = findViewById(R.id.rvMain);

        // Button Event Listener
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductCreate.class);
                startActivity(intent);
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserCart.class);
                startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserProfile.class);
                startActivity(intent);
            }
        });

        // Search Event Listener
        searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchTerm = query.toLowerCase();

                productSearch(searchTerm);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                String searchTerm = query.toLowerCase();

                productSearch(searchTerm);
                return false;
            }
        });

        // Recycler View
        Query query = FirebaseDatabase.getInstance().getReference().child("productDatabase").child("productItem").orderByChild("productName");

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(query, MainModel.class)
                        .build();

        rvMain.setLayoutManager(new GridLayoutManager(this, 2));
        mainAdapter = new MainAdapter(options);
        rvMain.setAdapter(mainAdapter);
    }
    
    // Search Functionality
    private void productSearch(String searchTerm) {
        Query query = FirebaseDatabase.getInstance().getReference().child("productDatabase").child("productItem").orderByChild("productSearch").startAt(searchTerm).endAt(searchTerm + "~");

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(query, MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        rvMain.setAdapter(mainAdapter);
        mainAdapter.startListening();
    }

    // Start Listening Adapter
    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    // Stop Listening Adapter
    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}