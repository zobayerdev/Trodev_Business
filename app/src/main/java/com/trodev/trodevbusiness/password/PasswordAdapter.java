package com.trodev.trodevbusiness.password;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trodev.trodevbusiness.R;

import java.util.ArrayList;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.MyViewHolder> {

    Context context;
    ArrayList<PasswordModel> list;

    public PasswordAdapter(Context context, ArrayList<PasswordModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PasswordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.password_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordAdapter.MyViewHolder holder, int position) {

        holder.web_name.setText("🌐 " + list.get(position).getWebsite() + " 🌐");
        holder.web_username.setText("username: " + list.get(position).getUsername());
        holder.web_pass.setText("password: " + list.get(position).getPassword());

        // Set an OnClickListener for the TextView to handle copying to clipboard
        holder.web_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the ClipboardManager instance
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

                // Create a ClipData object with the text to be copied
                ClipData clip = ClipData.newPlainText("copied_text", list.get(position).getPassword());

                // Set the ClipData to the clipboard
                clipboard.setPrimaryClip(clip);

                // Show a Toast message indicating that the text has been copied
                Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

// Load image from assets using Picasso
        Picasso.get()
                .load(R.drawable.trocloud) // Replace with the correct image file name
                .placeholder(R.drawable.add_icon) // Optional placeholder image
                .error(R.drawable.add_icon) // Optional error image
                .into(holder.imageIv);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView web_name, web_pass, web_username;
        ImageView imageIv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            web_name = itemView.findViewById(R.id.web_name_tv);
            web_pass = itemView.findViewById(R.id.web_pass_tv);
            web_username = itemView.findViewById(R.id.web_username_tv);
            imageIv = itemView.findViewById(R.id.imageIv);

        }
    }

}
