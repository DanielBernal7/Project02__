package com.example.project02_.recycler;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project02_.R;
import com.example.project02_.database.AppRepository;
import com.example.project02_.database.embedded.ProductAndQuantity;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class ProductAndQuantityAdapter extends RecyclerView.Adapter<ProductAndQuantityAdapter.ViewHolder> {

    private List<ProductAndQuantity> items = new ArrayList<>();
    private Context context;
    private AppRepository repository;

    public ProductAndQuantityAdapter(Context context, AppRepository repository){
        this.context = context;
        this.repository = repository;
    }

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

        holder.deleteButton.setOnClickListener(v -> {
            int productId = pq.product.getId();
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("userId", 1);

            repository.deleteCartItem(userId, productId);
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemChanged(position, items.size());
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView quantity;
        ImageView deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            quantity = itemView.findViewById(R.id.quantity);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
