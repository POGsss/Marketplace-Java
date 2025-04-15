package com.example.marketplace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AddedAdapter extends FirebaseRecyclerAdapter<AddedModel, AddedAdapter.myViewHolder> {

    public AddedAdapter(@NonNull FirebaseRecyclerOptions<AddedModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AddedAdapter.myViewHolder holder, int position, @NonNull AddedModel model) {
        holder.txtProductName.setText(model.getProductName());
        Glide.with(holder.imgProduct.getContext()).load(model.getProductImg()).into(holder.imgProduct);
    }

    @NonNull
    @Override
    public AddedAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.added_item, parent, false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        // Declaration
        ImageView imgProduct;
        TextView txtProductName;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialization
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
        }
    }
}
