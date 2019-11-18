package com.cmput3owo1.moodlet.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cmput3owo1.moodlet.R;
import com.cmput3owo1.moodlet.models.User;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter<User> {
    private ArrayList<User> userList;
    private Context context;
    private OnFollowClickListener listener;

    public interface OnFollowClickListener {
        void onFollowClick(User user);
    }

    public UserListAdapter(Context context, ArrayList<User> userList, OnFollowClickListener listener) {
        super(context, 0, userList);
        this.userList = userList;
        this.context = context;
        this.listener = listener;
    }

    static class ViewHolder {
        TextView usernameTextView;
        MaterialButton requestButton;
    }

    @Override
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_list_entry, null);
            holder = new ViewHolder();
            holder.usernameTextView = convertView.findViewById(R.id.search_username);
            holder.requestButton = convertView.findViewById(R.id.search_request_button);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final User user = getItem(position);
        Resources resources = context.getResources();

        holder.usernameTextView.setText(user.getUsername());
        if (user.isFollowing()) {
            holder.requestButton.setText(resources.getString(R.string.following));
            holder.requestButton.setEnabled(false);
        } else if (user.isRequested()) {
            holder.requestButton.setText(resources.getString(R.string.requested));
            holder.requestButton.setEnabled(false);
        } else {
            holder.requestButton.setText(resources.getString(R.string.follow));
            holder.requestButton.setEnabled(true);
        }

        holder.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFollowClick(user);
            }
        });

        return convertView;
    }
}
