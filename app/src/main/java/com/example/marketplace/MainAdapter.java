package com.example.marketplace;

import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        // Adding Data To Each Item
        holder.txtProductName.setText(model.getProductName());
        holder.txtProductPrice.setText("$" + model.getProductPrice());
        Glide.with(holder.imgProduct.getContext())
                .load(model.getProductImg())
                .placeholder(R.drawable.image_placeholder)
                .into(holder.imgProduct);

        // Event Listener
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductPreview.class);

                intent.putExtra("imageUrl", model.getProductImg());
                intent.putExtra("name", model.getProductName());
                intent.putExtra("price", model.getProductPrice());
                intent.putExtra("description", model.getProductDescription());
                intent.putExtra("category", model.getProductCategory());
                intent.putExtra("count", String.valueOf(model.getProductCount()));
                intent.putExtra("condition", model.getProductCondition());
                intent.putExtra("from", model.getProductFrom());
                intent.putExtra("warranty", model.getProductWarranty());

                view.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        // Declaration
        ImageView imgProduct;
        TextView txtProductName, txtProductPrice;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
        }
    }
}
