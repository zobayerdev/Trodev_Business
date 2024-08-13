package com.trodev.trodevbusiness.website;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trodev.trodevbusiness.R;
import com.trodev.trodevbusiness.WebViewActivity;

import java.util.ArrayList;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.MyViewHolder> {

    Context context;
    ArrayList<LinkModel> list;

    public LinkAdapter(Context context, ArrayList<LinkModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LinkAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.link_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkAdapter.MyViewHolder holder, int position) {

         holder.web_name.setText(list.get(position).getWeb_name());
         holder.web_link.setText(list.get(position).getWeb_link());

        // Load image using Picasso
        Picasso.get()
                .load(list.get(position).getImage()) // Replace with your image URL
                .placeholder(R.drawable.add_icon) // Optional placeholder image
                .error(R.drawable.add_icon) // Optional error image
                .into(holder.imageIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("web_link",list.get(position).getWeb_link());
                context.startActivity(intent);
            }
        });


//        /*pdf downloader method*/
//        /*it's make by me*/
//        holder.questionDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent download = new Intent(Intent.ACTION_VIEW);
//                download.setData(Uri.parse(list.get(position).getPdfUrl()));
//                context.startActivity(download);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView web_name, web_link;
        ImageView imageIv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            web_name = itemView.findViewById(R.id.web_name_tv);
            web_link = itemView.findViewById(R.id.web_link_tv);
            imageIv = itemView.findViewById(R.id.imageIv);

        }
    }
}
