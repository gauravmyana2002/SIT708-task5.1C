package com.example.task51c.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist_items")
public class PlaylistItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public String videoUrl;
    public String videoId;
    public long createdAt;

    public PlaylistItem(int userId, String videoUrl, String videoId, long createdAt) {
        this.userId = userId;
        this.videoUrl = videoUrl;
        this.videoId = videoId;
        this.createdAt = createdAt;
    }
}
