package com.example.project02_.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project02_.R;
import com.example.project02_.database.embedded.ProductAndQuantity;
import java.util.ArrayList;
import java.util.List;

public class ProductAndQuantityAdapter extends RecyclerView.Adapter<ProductAndQuantityAdapter.ViewHolder> {

    private List<ProductAndQuantity> items = new ArrayList<>();

    public void setItems(List<ProductAndQuantity> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_and_quantity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductAndQuantity pq = items.get(position);
        holder.productName.setText(pq.product.getName());
        holder.productPrice.setText(String.format("$%.2f", pq.product.getPrice()));
        holder.quantity.setText(String.format("Qty: %d", pq.quantity));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView quantity;

        ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
