// In UserAdapter.java
package com.example.project02_.recycler;

import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_.R;  // Ensure proper imports
import com.example.project02_.database.entities.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;
    private final DeleteUserListener deleteUserListener;

    public UserAdapter(List<User> users, DeleteUserListener deleteUserListener) {
        this.users = users;
        this.deleteUserListener = deleteUserListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.userNameTextView.setText(user.getUsername());
        holder.userPasswordTextView.setText(user.getPassword());

//        holder.deleteButton.setOnClickListener(v -> deleteUserListener.onDelete(user));

        holder.deleteButton.setOnClickListener(v -> {
            try {
                deleteUserListener.onDelete(user);

                users.remove(position);
                notifyItemRemoved(position);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateUsers(List<User> newUsers) {
        this.users = newUsers;
        notifyDataSetChanged();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        final TextView userNameTextView;
        final TextView userPasswordTextView;
        final Button deleteButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            userPasswordTextView = itemView.findViewById(R.id.userPasswordTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public interface DeleteUserListener {
        void onDelete(User user);
    }
}
