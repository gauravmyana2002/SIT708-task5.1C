package com.example.task51c.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task51c.R;
import com.example.task51c.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private final List<NewsItem> items = new ArrayList<>();
    private final StoryClickListener listener;

    public NewsAdapter(StoryClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<NewsItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem item = items.get(position);
        holder.imageView.setImageResource(item.getImageResId());
        holder.titleView.setText(item.getTitle());
        holder.descriptionView.setText(item.getDescription());
        holder.categoryView.setText(item.getCategory());
        holder.itemView.setOnClickListener(v -> listener.onStoryClicked(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView titleView;
        final TextView descriptionView;
        final TextView categoryView;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_news);
            titleView = itemView.findViewById(R.id.text_news_title);
            descriptionView = itemView.findViewById(R.id.text_news_description);
            categoryView = itemView.findViewById(R.id.text_news_category);
        }
    }
}
