package com.omnitech.covidproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.omnitech.covidproject.Model.Article;
import com.omnitech.covidproject.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<Article>listData;
    Context context;
    public NewsAdapter(List<Article> listData, Context context) {
        this.listData = listData;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lyt_news,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i)
    {


        Article article = listData.get(i);

        holder.txtTitle.setText(article.getTitle());
        holder.txtDesc.setText(article.getDescription());
        holder.txtLink.setText(article.getUrl());
        holder.txtDate.setText(article.getPublishedAt());

        Glide.with(context).load(article.getUrlToImage())
                .error(R.drawable.broken_image)
                .into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(article.getUrl()!=null)
                {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(article.getUrl()));
                    context.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView txtTitle;
        TextView txtDesc;
        TextView txtLink;
        TextView txtDate;
        ImageView imageView;


        public ViewHolder(View itemView)
        {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtTitle);
            txtDesc=itemView.findViewById(R.id.txtDescription);
            txtLink=itemView.findViewById(R.id.txtLink);
            txtDate=itemView.findViewById(R.id.txtDate);
            imageView=itemView.findViewById(R.id.imgNews);
        }
    }
}
