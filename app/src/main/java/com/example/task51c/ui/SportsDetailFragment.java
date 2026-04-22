package com.example.task51c.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SportsDetailFragment extends BaseFragment {
    private static final String ARG_ITEM = "arg_item";

    private NewsItem item;
    private AppDatabase database;
    private Button bookmarkButton;

    public static SportsDetailFragment newInstance(NewsItem item) {
        SportsDetailFragment fragment = new SportsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ITEM, item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sports_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        item = (NewsItem) requireArguments().getSerializable(ARG_ITEM);
        database = AppDatabase.getInstance(requireContext());
        bookmarkButton = view.findViewById(R.id.button_toggle_bookmark);

        ImageView imageView = view.findViewById(R.id.image_story);
        TextView titleView = view.findViewById(R.id.text_story_title);
        TextView categoryView = view.findViewById(R.id.text_story_category);
        TextView descriptionView = view.findViewById(R.id.text_story_description);
        RecyclerView relatedRecycler = view.findViewById(R.id.recycler_related);

        imageView.setImageResource(item.getImageResId());
        titleView.setText(item.getTitle());
        categoryView.setText(item.getCategory());
        descriptionView.setText(item.getDescription());

        NewsAdapter relatedAdapter = new NewsAdapter(story -> navigator().openSportsDetail(story));
        relatedAdapter.submitList(SportsRepository.getRelatedStories(item));
        relatedRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        relatedRecycler.setAdapter(relatedAdapter);
        relatedRecycler.setNestedScrollingEnabled(false);

        refreshBookmarkState();

        bookmarkButton.setOnClickListener(v -> {
            if (database.bookmarkDao().isBookmarked(item.getId()) > 0) {
                database.bookmarkDao().deleteById(item.getId());
                Toast.makeText(requireContext(), R.string.bookmark_removed, Toast.LENGTH_SHORT).show();
            } else {
                database.bookmarkDao().insert(new BookmarkItem(item.getId(), item.getTitle(),
                        item.getDescription(), item.getCategory(), item.getImageResId(),
                        System.currentTimeMillis()));
                Toast.makeText(requireContext(), R.string.bookmark_saved, Toast.LENGTH_SHORT).show();
            }
            refreshBookmarkState();
        });

        view.findViewById(R.id.button_back_sports).setOnClickListener(v -> requireActivity().onBackPressed());
        view.findViewById(R.id.button_open_bookmarks_from_detail)
                .setOnClickListener(v -> navigator().openBookmarks());
    }

    private void refreshBookmarkState() {
        boolean isBookmarked = database.bookmarkDao().isBookmarked(item.getId()) > 0;
        bookmarkButton.setText(isBookmarked
                ? R.string.remove_bookmark
                : R.string.add_bookmark);
    }
}
