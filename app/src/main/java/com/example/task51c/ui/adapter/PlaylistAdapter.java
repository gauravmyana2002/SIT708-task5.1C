package com.example.task51c.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task51c.R;
import com.example.task51c.data.PlaylistItem;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private final List<PlaylistItem> items = new ArrayList<>();
    private final PlaylistClickListener listener;

    public PlaylistAdapter(PlaylistClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<PlaylistItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        PlaylistItem item = items.get(position);
        holder.urlView.setText(item.videoUrl);
        holder.videoIdView.setText(holder.itemView.getContext()
                .getString(R.string.video_id_label, item.videoId));
        holder.itemView.setOnClickListener(v -> listener.onPlaylistClicked(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        final TextView urlView;
        final TextView videoIdView;

        PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            urlView = itemView.findViewById(R.id.text_playlist_url);
            videoIdView = itemView.findViewById(R.id.text_playlist_video_id);
        }
    }
}
