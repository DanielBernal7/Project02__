// In UserAdapter.java
package com.example.project02_.recycler;

import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_.R;  // Ensure proper imports
import com.example.project02_.database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;
    private List<User> filteredUsers;
    private final DeleteUserListener deleteUserListener;

    public UserAdapter(List<User> users, DeleteUserListener deleteUserListener) {
        this.users = new ArrayList<>(users);
        this.filteredUsers   = new ArrayList<>(users);
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
        User user = filteredUsers.get(position);
        holder.userNameTextView.setText(user.getUsername());
        holder.userPasswordTextView.setText(user.getPassword());

//        holder.deleteButton.setOnClickListener(v -> deleteUserListener.onDelete(user));

        holder.deleteButton.setOnClickListener(v -> {
//            try {
                deleteUserListener.onDelete(user);

                int index = users.indexOf(user);
                if(index != -1){
                    users.remove(index);
                }
                filteredUsers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, filteredUsers.size() - position);
//
//                users.remove(position);
//                notifyItemRemoved(position);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        });
    }

    @Override
    public int getItemCount() {
        if(filteredUsers == null){
            return 0;
        } else {
            return filteredUsers.size();
        }
    }

    public void updateUsers(List<User> newUsers) {
        if(newUsers != null){
            this.users = new ArrayList<>(newUsers);
            this.filteredUsers = new ArrayList<>(newUsers);
        } else {
            this.users = new ArrayList<>();
            this.filteredUsers = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<User> filteredList = new ArrayList<>();
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (User user : users) {
                    if (user.getUsername().toLowerCase().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }

                Log.d("Filter1", "Filtering for: " + filterPattern + " | Matched: " + filteredList.size());

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredUsers.clear();
                filteredUsers.addAll((List<User>) results.values);
                Log.d("Filter1", "Publishing results: " + filteredUsers.size());
                notifyDataSetChanged();
            }
        };
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
