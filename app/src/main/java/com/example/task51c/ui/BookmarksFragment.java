package com.example.task51c.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task51c.R;
import com.example.task51c.data.AppDatabase;
import com.example.task51c.data.BookmarkItem;
import com.example.task51c.model.NewsItem;
import com.example.task51c.model.SportsRepository;
import com.example.task51c.ui.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookmarksFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_bookmarks);
        TextView emptyView = view.findViewById(R.id.text_empty_bookmarks);

        List<BookmarkItem> bookmarks = AppDatabase.getInstance(requireContext()).bookmarkDao().getAll();
        List<NewsItem> savedStories = new ArrayList<>();
        for (BookmarkItem bookmark : bookmarks) {
            savedStories.add(SportsRepository.fromBookmark(bookmark.storyId, bookmark.title,
                    bookmark.description, bookmark.category, bookmark.imageResId));
        }

        NewsAdapter adapter = new NewsAdapter(item -> navigator().openSportsDetail(item));
        adapter.submitList(savedStories);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        emptyView.setVisibility(savedStories.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(savedStories.isEmpty() ? View.GONE : View.VISIBLE);

        view.findViewById(R.id.button_back_from_bookmarks)
                .setOnClickListener(v -> requireActivity().onBackPressed());
    }
}
