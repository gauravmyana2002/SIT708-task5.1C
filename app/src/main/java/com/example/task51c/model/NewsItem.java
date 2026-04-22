package com.example.task51c.model;

import java.io.Serializable;

public class NewsItem implements Serializable {
    private final int id;
    private final String title;
    private final String description;
    private final String category;
    private final int imageResId;
    private final boolean featured;
    private final String subtitle;

    public NewsItem(int id, String title, String description, String category, int imageResId,
                    boolean featured, String subtitle) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageResId = imageResId;
        this.featured = featured;
        this.subtitle = subtitle;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isFeatured() {
        return featured;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
