package com.example.task51c.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarks")
public class BookmarkItem {
    @PrimaryKey
    public int storyId;

    public String title;
    public String description;
    public String category;
    public int imageResId;
    public long savedAt;

    public BookmarkItem(int storyId, String title, String description, String category, int imageResId,
                        long savedAt) {
        this.storyId = storyId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageResId = imageResId;
        this.savedAt = savedAt;
    }
}
