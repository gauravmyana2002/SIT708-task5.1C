package com.example.task51c.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookmarkItem item);

    @Query("DELETE FROM bookmarks WHERE storyId = :storyId")
    void deleteById(int storyId);

    @Query("SELECT COUNT(*) FROM bookmarks WHERE storyId = :storyId")
    int isBookmarked(int storyId);

    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    List<BookmarkItem> getAll();
}
