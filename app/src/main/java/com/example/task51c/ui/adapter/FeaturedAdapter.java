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

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {
    private final List<NewsItem> items = new ArrayList<>();
    private final StoryClickListener listener;

    public FeaturedAdapter(StoryClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<NewsItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_featured_match, parent, false);
        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        NewsItem item = items.get(position);
        holder.imageView.setImageResource(item.getImageResId());
        holder.titleView.setText(item.getTitle());
        holder.subtitleView.setText(item.getCategory());
        holder.itemView.setOnClickListener(v -> listener.onStoryClicked(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class FeaturedViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView titleView;
        final TextView subtitleView;

        FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_featured);
            titleView = itemView.findViewById(R.id.text_featured_title);
            subtitleView = itemView.findViewById(R.id.text_featured_subtitle);
        }
    }
}
